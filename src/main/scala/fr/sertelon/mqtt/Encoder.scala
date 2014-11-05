package fr.sertelon.mqtt

import akka.actor._
import akka.io.Tcp._
import fr.sertelon.mqtt.model.MqttConnAck
import akka.util.ByteStringBuilder
import akka.util.ByteString

class Encoder extends Actor {
  def receive = {
    case (s: ActorRef, m : MqttConnAck) =>
      s ! Write(encodeMqttConAck(m))
  }
  
  def encodeMqttConAck(m : MqttConnAck) = {
    val builder = ByteString.newBuilder
    
    builder.putByte(0x20) // MessageType
    builder.putByte(0x02) // Remaining Length
    builder.putByte(0x00) // Reserved
    builder.putByte(0x00) // Connection Accepted
    
    builder.result()
  }
}