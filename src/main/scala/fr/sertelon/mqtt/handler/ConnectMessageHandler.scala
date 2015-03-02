package fr.sertelon.mqtt.handler

import akka.actor._

import fr.sertelon.mqtt.model._

class ConnectMessageHandler extends Actor {
  
  def receive = {
    case (MqttConnect(connectHeader, payload), conn) =>
    case (MqttConnAck(returnCode), conn) =>
    case (MqttDisconnect(), conn) =>
  }
  
}