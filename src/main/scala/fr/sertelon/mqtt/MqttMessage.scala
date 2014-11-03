package fr.sertelon.mqtt

// Transcription of the MQTT spec model
// Select relevant types (Char, Short, Int, etc.) beware of signed/unsigned
object MqttMessage {
	type Topic = String
	type MessageId = Char
}

import MqttMessage._

sealed trait MessageType
case class ConnectMessageType extends MessageType
case class ConnAckMessageType extends MessageType
case class PublishMessageType extends MessageType
case class PubAckMessageType extends MessageType
case class PubRecMessageType extends MessageType
case class PubRelMessageType extends MessageType
case class PubCompMessageType extends MessageType
case class SubscribeMessageType extends MessageType
case class SubAckMessageType extends MessageType
case class UnsubscribeMessageType extends MessageType
case class UnsubAckMessageType extends MessageType
case class PingReqMessageType extends MessageType
case class PingRespMessageType extends MessageType
case class DisconnectMessageType extends MessageType
case class UnknownMessageType extends MessageType

object MessageType {
  def get(number: Int): MessageType = {
    number match {
      case 1 => ConnectMessageType()
      case 2 => ConnAckMessageType()
      case 3 => PublishMessageType()
      case 4 => PubAckMessageType()
      case 5 => PubRecMessageType()
      case 6 => PubRelMessageType()
      case 7 => PubCompMessageType()
      case 8 => SubscribeMessageType()
      case 9 => SubAckMessageType()
      case 10 => UnsubscribeMessageType()
      case 11 => UnsubAckMessageType()
      case 12 => PingReqMessageType()
      case 13 => PingRespMessageType()
      case 14 => DisconnectMessageType()
      case _ => UnknownMessageType()
    }
  }
}

object QoS {
  def get(number: Int): QoS = {
    number match {
      case 0 => QoS_0()
      case 1 => QoS_1()
      case 2 => QoS_2()
      case _ => UnknownQoS()
    }
  }
}
sealed trait QoS
case class QoS_0 extends QoS
case class QoS_1 extends QoS
case class QoS_2 extends QoS
case class UnknownQoS extends QoS

sealed trait ConnectReturnCode
case class ConnectionAccepted extends ConnectReturnCode
case class ConnectionRefusedProtocolVersion extends ConnectReturnCode
case class ConnectionRefusedIdentifierRejected extends ConnectReturnCode
case class ConnectionRefusedServerUnavailable extends ConnectReturnCode
case class ConnectionRefusedBadCredentials extends ConnectReturnCode
case class ConnectionRefusedNotAuthorized extends ConnectReturnCode

case class MqttHeader(messageType: MessageType, duplicate: Boolean, qos: QoS, retain: Boolean, length: Int)

sealed trait VariableHeader
case class MessageIdHeader(messageId: MessageId) extends VariableHeader

sealed trait Payload


case class MqttConnect(header: MqttHeader, variableHeaders: ConnectHeader, payload: ConnectPayload)
case class ConnectHeader(protocolName: String, protocolVersion: Int, connectFlags: ConnectFlags, keepAlive: Int) extends VariableHeader
case class ConnectFlags(hasUsername: Boolean, hasPassword: Boolean, hasWillRetain: Boolean, willQoS: QoS, hasWill: Boolean, cleanSession: Boolean)
case class ConnectPayload(clientId: String, willTopic: Option[Topic], willMessage: Option[String], username: Option[String], password: Option[String]) extends Payload

case class MqttConnAck(header: MqttHeader, variableHeader: ConnAckHeader)
case class ConnAckHeader(returnCode: ConnectReturnCode) extends VariableHeader

case class MqttPublish(header: MqttHeader, variableHeader: PublishHeader)
case class PublishHeader(topic: Topic, messageId: MessageId, payload: String) extends VariableHeader

case class MqttPubAck(header: MqttHeader, variableHeader: MessageIdHeader)

case class MqttPubRec(header: MqttHeader, variableHeader: MessageIdHeader)

case class MqttPubRel(header: MqttHeader, variableHeader: MessageIdHeader)

case class MqttPubComp(header: MqttHeader, variableHeader: MessageIdHeader)

case class MqttSubscribe(header: MqttHeader, variableHeader: MessageIdHeader, payload: List[Subscription])
case class Subscription(topic: Topic, qos: QoS)

case class MqttSubAck(header: MqttHeader, variableHeader: MessageIdHeader, payload: List[QoS])

case class MqttUnsubscribe(header: MqttHeader, variableHeader: MessageIdHeader, topics: List[Topic])

case class MqttUnsubAck(header: MqttHeader, variableHeader: MessageIdHeader)

case class MqttPingReq(header: MqttHeader)

case class MqttPingResp(header: MqttHeader)

case class MqttDisconnect(header: MqttHeader)