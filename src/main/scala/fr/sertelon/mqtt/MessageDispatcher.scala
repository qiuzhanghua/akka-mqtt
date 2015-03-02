package fr.sertelon.mqtt

import akka.actor._
import akka.io._
import akka.util._
import handler._
import model._
import model.MqttMessage._

class MessageDispatcher extends Actor {
  import Tcp._
  import MqttCodec._

  val connectHandler = context.actorOf(Props[ConnectMessageHandler])
  val pingHandler = context.actorOf(Props[PingMessageHandler])
  val publishHandler = context.actorOf(Props[PublishMessageHandler])
  val subscribeHandler = context.actorOf(Props[SubscribeMessageHandler])

  def receive = {
    case Received(data) => {
      val mqttMsg = decodeMqttMessage(data)

      mqttMsg match {
        case con : ConnectMqttMessage => connectHandler ! (con, sender)
        case pub : PublishMqttMessage => publishHandler ! (pub, sender)
        case pin : PingMqttMessage => pingHandler ! (pin, sender)
        case sub : SubscribeMqttMessage => subscribeHandler ! (sub, sender)
      }
    }
    case PeerClosed => {
      // TODO clean registries
      context stop self
    }
  }

}