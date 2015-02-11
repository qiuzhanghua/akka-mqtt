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

// Connection related messages
sealed trait ConnectMqttMessage extends MqttMessage

case class MqttConnect(header: MqttHeader, variableHeaders: ConnectHeader, payload: ConnectPayload) extends ConnectMqttMessage
case class ConnectHeader(protocolName: String, protocolVersion: Int, connectFlags: ConnectFlags, keepAlive: Int)
case class ConnectFlags(hasUsername: Boolean, hasPassword: Boolean, hasWillRetain: Boolean, willQoS: QoS, hasWill: Boolean, cleanSession: Boolean)
case class ConnectPayload(clientId: String, willTopic: Option[Topic], willMessage: Option[String], username: Option[String], password: Option[String])

case class MqttConnAck(header: MqttHeader, variableHeader: ConnectReturnCode) extends ConnectMqttMessage
sealed trait ConnectReturnCode
case object ConnectionAccepted extends ConnectReturnCode
case object ConnectionRefusedProtocolVersion extends ConnectReturnCode
case object ConnectionRefusedIdentifierRejected extends ConnectReturnCode
case object ConnectionRefusedServerUnavailable extends ConnectReturnCode
case object ConnectionRefusedBadCredentials extends ConnectReturnCode
case object ConnectionRefusedNotAuthorized extends ConnectReturnCode

case class MqttDisconnect(header: MqttHeader) extends ConnectMqttMessage

// Publishing related messages
sealed trait PublishMqttMessage extends MqttMessage

case class MqttPublish(header: MqttHeader, variableHeader: PublishHeader, payload: Option[String]) extends PublishMqttMessage
case class PublishHeader(topic: Topic, messageId: MessageId) 

case class MqttPubAck(header: MqttHeader, variableHeader: MessageIdHeader) extends PublishMqttMessage

case class MqttPubRec(header: MqttHeader, variableHeader: MessageIdHeader) extends PublishMqttMessage

case class MqttPubRel(header: MqttHeader, variableHeader: MessageIdHeader) extends PublishMqttMessage

case class MqttPubComp(header: MqttHeader, variableHeader: MessageIdHeader) extends PublishMqttMessage

// Subscription related messages
sealed trait SubscribeMqttMessage extends MqttMessage

case class MqttSubscribe(header: MqttHeader, variableHeader: MessageIdHeader, payload: List[Subscription]) extends SubscribeMqttMessage
case class Subscription(topic: Topic, qos: QoS)

case class MqttSubAck(header: MqttHeader, variableHeader: MessageIdHeader, payload: List[QoS]) extends SubscribeMqttMessage

case class MqttUnsubscribe(header: MqttHeader, variableHeader: MessageIdHeader, topics: List[Topic]) extends SubscribeMqttMessage

case class MqttUnsubAck(header: MqttHeader, variableHeader: MessageIdHeader) extends SubscribeMqttMessage

// Ping related messages
sealed trait PingMqttMessage extends MqttMessage

case class MqttPingReq(header: MqttHeader) extends PingMqttMessage

case class MqttPingResp(header: MqttHeader) extends PingMqttMessage

