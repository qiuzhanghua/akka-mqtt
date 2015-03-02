package fr.sertelon.mqtt

import akka.actor._
import akka.io._
import akka.util._
import handler._
import model._
import model.MqttMessage._

object MqttCodec {
  
  // Encoders
  def encodeMqttMessage(msg: MqttMessage) : ByteString = {
		import akka.util.ByteStringBuilder
		  
    val header1 = 0
    
    
    
    ByteString()  
  }
  
  
  
  // Decoders
  def decodeMqttMessage(bs: ByteString): MqttMessage = {
    val it = bs.iterator
    val firstByte = it.getByte

    val messageType = (firstByte & 0xF0) >>> 4
    val duplicate = (firstByte & 0x8) >>> 3
    val qos = (firstByte & 0x6) >>> 1
    val retain = firstByte & 0x1
    val remainingLength = getRemaininLength(it)

    val header = MqttHeader(messageType, duplicate, qos, retain, remainingLength)

    header.messageType match {
      case ConnectMessageType => MqttConnect.fromByteIterator(remainingLength, it)
      case ConnAckMessageType => null
      case PublishMessageType => decodePublishMessage(remainingLength, it)
      case PubAckMessageType => null
      case PubRecMessageType => null
      case PubRelMessageType => null
      case PubCompMessageType => null
      case SubscribeMessageType => null
      case SubAckMessageType => null
      case UnsubscribeMessageType => null
      case UnsubAckMessageType => null
      case PingReqMessageType => null
      case PingRespMessageType => null
      case DisconnectMessageType => null
      case UnknownMessageType => null

    }
  }

  private def decodeConnectMessage(it: ByteIterator) = {
    
  }

  private def decodePublishMessage(remainingLength: Int, it: ByteIterator) = {
    val (topicLength, topic) = getUTF8String(it)
    val msgId = ((it.getByte >>> 8) + it.getByte).toChar

    val payloadLength = remainingLength - topicLength - 2
    
    val payload = Some(it.take(payloadLength).toByteString.toArray)

    MqttPublish(PublishHeader(topic, msgId), payload)
  }

  // Utils

  def getUTF8String(it: ByteIterator): (Int, String) = {
    val strLength = (it.getByte >>> 8) + it.getByte
    val bytes = new Array[Byte](strLength)
    it getBytes bytes
    (strLength + 2, ByteString(bytes).utf8String)
  }

  implicit def intToBoolean(i: Int) = if (i == 1) true else false

  private def getRemaininLength(it: ByteIterator) = {
    // Fully var because it's how it's presented in spec
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