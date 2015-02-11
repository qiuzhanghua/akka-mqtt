package fr.sertelon.mqtt.handler

import akka.actor._
import fr.sertelon.mqtt.model._

object PingMessageHandler {
  case class PingMessage(msg: PingMqttMessage, connection: ActorRef)
}

class PingMessageHandler extends Actor {
  import PingMessageHandler._
  
  def receive = {
    case PingMessage(MqttPingReq(header), conn) =>
    case PingMessage(MqttPingResp(header), conn) =>
  }
  
}