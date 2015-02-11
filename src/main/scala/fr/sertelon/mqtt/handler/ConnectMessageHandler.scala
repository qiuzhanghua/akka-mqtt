package fr.sertelon.mqtt.handler

import akka.actor._

import fr.sertelon.mqtt.model._

object ConnectMessageHandler {
  case class ConnectMessage(msg: ConnectMqttMessage, connection: ActorRef)
}

class ConnectMessageHandler extends Actor {
  import ConnectMessageHandler._
  
  def receive = {
    case ConnectMessage(MqttConnect(header, connectHeader, payload), conn) =>
    case ConnectMessage(MqttConnAck(header, returnCode), conn) =>
    case ConnectMessage(MqttDisconnect(header), conn) =>
  }
  
}