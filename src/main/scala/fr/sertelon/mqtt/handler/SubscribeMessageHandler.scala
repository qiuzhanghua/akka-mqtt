package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

object SubscribeMessageHandler {
  case class SubscribeMessage(msg: SubscribeMqttMessage, connection: ActorRef)
}

class SubscribeMessageHandler extends Actor {
  import SubscribeMessageHandler._
  
  def receive = {
    case SubscribeMessage(MqttSubscribe(header, msgId, subscriptions), conn) =>
    case SubscribeMessage(MqttSubAck(header, msgId, qos), conn) =>
    case SubscribeMessage(MqttUnsubscribe(header, msgId, topics), conn) =>
    case SubscribeMessage(MqttUnsubAck(header, msgId), conn) =>
  }
  
}