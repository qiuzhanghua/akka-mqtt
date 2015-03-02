package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

class SubscribeMessageHandler extends Actor {
  
  def receive = {
    case (MqttSubscribe(msgId, subscriptions), conn) =>
    case (MqttSubAck(msgId, qos), conn) =>
    case (MqttUnsubscribe(msgId, topics), conn) =>
    case (MqttUnsubAck(msgId), conn) =>
  }
  
}