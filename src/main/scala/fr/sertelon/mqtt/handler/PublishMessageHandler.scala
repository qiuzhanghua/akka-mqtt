package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

object PublishMessageHandler {
  case class PublishMessage(msg: PublishMqttMessage, connection: ActorRef)
}

class PublishMessageHandler extends Actor {
  import PublishMessageHandler._
  
  def receive = {
    case PublishMessage(MqttPublish(header, publishHeader, payload), conn) =>
    case PublishMessage(MqttPubAck(header, msgId), conn) =>
    case PublishMessage(MqttPubRec(header, msgId), conn) =>
    case PublishMessage(MqttPubRel(header, msgId), conn) =>
    case PublishMessage(MqttPubComp(header, msgId), conn) =>
  }
  
}