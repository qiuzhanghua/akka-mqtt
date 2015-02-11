package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

class SubscribeMessageHandler extends Actor {
  
  def receive = {
    case (MqttSubscribe(header, msgId, subscriptions), conn) =>
    case (MqttSubAck(header, msgId, qos), conn) =>
    case (MqttUnsubscribe(header, msgId, topics), conn) =>
    case (MqttUnsubAck(header, msgId), conn) =>
  }
  
}