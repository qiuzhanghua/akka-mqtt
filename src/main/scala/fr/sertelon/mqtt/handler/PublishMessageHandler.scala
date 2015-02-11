package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

class PublishMessageHandler extends Actor {
  
  def receive = {
    case (MqttPublish(header, publishHeader, payload), conn) =>
    case (MqttPubAck(header, msgId), conn) =>
    case (MqttPubRec(header, msgId), conn) =>
    case (MqttPubRel(header, msgId), conn) =>
    case (MqttPubComp(header, msgId), conn) =>
  }
  
}