package fr.sertelon.mqtt

import akka.actor.Actor
import akka.util.ByteString
import akka.util.ByteIterator

class Decoder extends Actor {

  def receive = {
    case bs : ByteString =>
      println(bs.length)
    	 val it = bs.iterator
    	 
    	 // Fixed header
    	 val firstByte = it.getByte
    	 val messageType = MessageType.get((firstByte & 0xF0) >>> 4)
		 val dup = firstByte & 0x8 >>> 3
		 val qos = firstByte & 0x6 >>> 1
		 val retain = firstByte & 0x1
	     val length = getRemaininLength(it)
	     
	     val header = MqttHeader(messageType, dup, QoS.get(qos), retain, length)
	     println(header)
	     
	     val variableHeader = getVariableHeader(messageType, it)
	     println(variableHeader)
  }
  
  def getVariableHeader(msgType: MessageType, it: ByteIterator) = {
    msgType match {
      case ConnectMessageType() =>
        
        val protocolName = getUTF8String(it)
        val versionNumber: Int = it.getByte
        
        // Connect flags
        val flagByte = it.getByte
        val hasUsername = (flagByte & 0x80) >>> 7
        val hasPassword = (flagByte & 0x40) >>> 6
        val hasWillRetain = (flagByte & 0x20) >>> 5
        val willQoS = (flagByte & 0x18) >>> 3
        val hasWill = (flagByte & 0x04) >>> 2
        val cleanSession = (flagByte & 0x02) >>> 1
        
        val flags = ConnectFlags(hasUsername, hasPassword, hasWillRetain, QoS.get(willQoS), hasWill, cleanSession)
        val keepAlive = (it.getByte >>> 8) + it.getByte
        
        
        val clientId = getUTF8String(it)
        val willTopic = if(hasWill) Some(getUTF8String(it)) else None
        val willMessage = if(hasWill) Some(getUTF8String(it)) else None
        val username = if(hasUsername) Some(getUTF8String(it)) else None
        val password = if(hasPassword) Some(getUTF8String(it)) else None
        
        (ConnectHeader(protocolName, versionNumber, flags, keepAlive), 
        ConnectPayload(clientId, willTopic, willMessage, username, password))
        
    }
  }
  
  def getUTF8String(it: ByteIterator): String = {
    val strLength = (it.getByte >>> 8) + it.getByte
    val bytes = new Array[Byte](strLength)
    it getBytes bytes
    ByteString(bytes).utf8String
  }
  
  implicit def intToBoolean(i: Int) = if(i == 1) true else false
  
  def getRemaininLength(it: ByteIterator) = {
    var multiplier = 1
    var value = 0
    
    var currentByte = 0
    
    do {
      currentByte = it.getByte
        
      value = value + (currentByte & 127) * multiplier
      multiplier = multiplier * 128
    } while ((currentByte & 128) != 0)
      
    value
  }
  
}