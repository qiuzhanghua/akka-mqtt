package fr.sertelon.mqtt.handler

import akka.actor._

import fr.sertelon.mqtt.model._

class ConnectMessageHandler extends Actor {
  
  def receive = {
    case (MqttConnect(header, connectHeader, payload), conn) =>
    case (MqttConnAck(header, returnCode), conn) =>
    case (MqttDisconnect(header), conn) =>
  }
  
}