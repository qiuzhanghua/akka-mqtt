package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

class PublishMessageHandler extends Actor {
  
  def receive = {
    case (MqttPublish(publishHeader, payload), conn) =>
    case (MqttPubAck(msgId), conn) =>
    case (MqttPubRec(msgId), conn) =>
    case (MqttPubRel(msgId), conn) =>
    case (MqttPubComp(msgId), conn) =>
  }
  
}