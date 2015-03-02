package fr.sertelon.mqtt.model

import MqttMessage._
import akka.util._
import fr.sertelon.mqtt.MqttCodec._

sealed trait ConnectMqttMessage extends MqttMessage

object MqttConnect {
  def toByteString: ByteString	= {
    null
  }
  
  def fromByteIterator(remainingLength: Long, it: ByteIterator): MqttConnect = {
    val protocolName = getUTF8String(it)._2
    val versionNumber: Int = it.getByte

    // Connect flags
    val flagByte = it.getByte
    val cleanSession = (flagByte & 0x02) >>> 1
    val hasWill = (flagByte & 0x04) >>> 2
    val willQoS = (flagByte & 0x18) >>> 3
    val hasWillRetain = (flagByte & 0x20) >>> 5
    val hasPassword = (flagByte & 0x40) >>> 6
    val hasUsername = (flagByte & 0x80) >>> 7

    val flags = ConnectFlags(hasUsername, hasPassword, hasWillRetain, willQoS, hasWill, cleanSession)
    val keepAlive = (it.getByte >>> 8) + it.getByte

    val clientId = getUTF8String(it)._2
    val willTopic = if (hasWill) Some(getUTF8String(it)._2) else None
    val willMessage = if (hasWill) Some(getUTF8String(it)._2) else None
    val username = if (hasUsername) Some(getUTF8String(it)._2) else None
    val password = if (hasPassword) Some(getUTF8String(it)._2) else None

    MqttConnect(ConnectHeader(protocolName, versionNumber, flags, keepAlive), ConnectPayload(clientId, willTopic, willMessage, username, password))
  }
}
case class MqttConnect(connectHeader: ConnectHeader, payload: ConnectPayload) extends ConnectMqttMessage


case class ConnectHeader(protocolName: String, protocolVersion: Int, connectFlags: ConnectFlags, keepAlive: Int)
case class ConnectFlags(hasUsername: Boolean, hasPassword: Boolean, hasWillRetain: Boolean, willQoS: QoS, hasWill: Boolean, cleanSession: Boolean)
case class ConnectPayload(clientId: String, willTopic: Option[Topic], willMessage: Option[String], username: Option[String], password: Option[String])

case class MqttConnAck(returnCode: ConnectReturnCode) extends ConnectMqttMessage
sealed trait ConnectReturnCode
case object ConnectionAccepted extends ConnectReturnCode
case object ConnectionRefusedProtocolVersion extends ConnectReturnCode
case object ConnectionRefusedIdentifierRejected extends ConnectReturnCode
case object ConnectionRefusedServerUnavailable extends ConnectReturnCode
case object ConnectionRefusedBadCredentials extends ConnectReturnCode
case object ConnectionRefusedNotAuthorized extends ConnectReturnCode

case class MqttDisconnect() extends ConnectMqttMessage