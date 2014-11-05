package fr.sertelon.mqtt.model

import MqttMessage._
import scala.Char.char2int

// Transcription of the MQTT spec model
// Select relevant types (Char, Short, Int, etc.) beware of signed/unsigned
object MqttMessage {
	type Topic = String
	type MessageId = Char
}
sealed trait MqttMessage
case class MqttHeader(messageType: MessageType, duplicate: Boolean, qos: QoS, retain: Boolean, length: Int)
case class MessageIdHeader(messageId: MessageId)

case class MqttConnect(header: MqttHeader, variableHeaders: ConnectHeader, payload: ConnectPayload) extends MqttMessage
case class ConnectHeader(protocolName: String, protocolVersion: Int, connectFlags: ConnectFlags, keepAlive: Int)
case class ConnectFlags(hasUsername: Boolean, hasPassword: Boolean, hasWillRetain: Boolean, willQoS: QoS, hasWill: Boolean, cleanSession: Boolean)
case class ConnectPayload(clientId: String, willTopic: Option[Topic], willMessage: Option[String], username: Option[String], password: Option[String])

case class MqttConnAck(header: MqttHeader, variableHeader: ConnectReturnCode) extends MqttMessage
sealed trait ConnectReturnCode
case object ConnectionAccepted extends ConnectReturnCode
case object ConnectionRefusedProtocolVersion extends ConnectReturnCode
case object ConnectionRefusedIdentifierRejected extends ConnectReturnCode
case object ConnectionRefusedServerUnavailable extends ConnectReturnCode
case object ConnectionRefusedBadCredentials extends ConnectReturnCode
case object ConnectionRefusedNotAuthorized extends ConnectReturnCode

case class MqttPublish(header: MqttHeader, variableHeader: PublishHeader, payload: Option[String]) extends MqttMessage
case class PublishHeader(topic: Topic, messageId: MessageId) 

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