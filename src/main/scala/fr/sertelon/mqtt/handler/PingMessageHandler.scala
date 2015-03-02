package fr.sertelon.mqtt.handler

import akka.actor._
import fr.sertelon.mqtt.model._

class PingMessageHandler extends Actor {
  
  def receive = {
    case (MqttPingReq(), conn) =>
    case (MqttPingResp(), conn) =>
  }
  
}