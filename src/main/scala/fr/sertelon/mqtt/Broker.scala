package fr.sertelon.mqtt

import model._
import akka.actor._
import com.sun.security.ntlm.Client

class Broker extends Actor {
  
  val encoder = context.system.actorOf(Props[Encoder], "encoder")
  
  var connectedClients = List[String]()
  
  def receive = {
    case (s: ActorRef, m: MqttConnect) =>
      println("received connect from " + m.payload.clientId)
      connectedClients = m.payload.clientId :: connectedClients
      encoder ! (s, MqttConnAck(MqttHeader(ConnAckMessageType, false, QoS_0, false, 2), ConnectionAccepted))
    case (s: ActorRef, m: MqttPublish) =>
      println(s"Publishing ${m.payload} on ${m.variableHeader.topic}")
  }
}