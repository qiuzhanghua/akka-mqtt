package fr.sertelon.mqtt

// Transcription of the MQTT spec model
// Select relevant types (Char, Short, Int, etc.) beware of signed/unsigned
object MqttMessage {
	type Topic = String
	type MessageId = Char
}

import MqttMessage._

trait MessageType

sealed trait QoS
case class QoS_0 extends QoS
case class QoS_1 extends QoS
case class QoS_2 extends QoS

sealed trait ConnectReturnCode
case class ConnectionAccepted extends ConnectReturnCode
case class ConnectionRefusedProtocolVersion extends ConnectReturnCode
case class ConnectionRefusedIdentifierRejected extends ConnectReturnCode
case class ConnectionRefusedServerUnavailable extends ConnectReturnCode
case class ConnectionRefusedBadCredentials extends ConnectReturnCode
case class ConnectionRefusedNotAuthorized extends ConnectReturnCode

class MqttHeader(messageType: MessageType, duplicate: Boolean, qos: QoS, retain: Boolean, length: Int)

class MqttConnect(header: MqttHeader, variableHeaders: ConnectHeader, payload: ConnectPayload)
class ConnectHeader(protocolName: String, protocolVersion: Int, connectFlags: ConnectFlags, keepAlive: Int)
class ConnectFlags(hasUsername: Boolean, hasPassword: Boolean, hasWillRetain: Boolean, hasWillQoS: Boolean, hasWill: Boolean, hasCleanSession: Boolean)
class ConnectPayload(clientId: String, willTopic: Topic, willMessage: String, username: String, password: String)

class MqttConnAck(header: MqttHeader, variableHeader: ConnAckHeader)
class ConnAckHeader(returnCode: ConnectReturnCode)

class MqttPublish(header: MqttHeader, variableHeader: PublishHeader)
class PublishHeader(topic: Topic, messageId: MessageId, payload: String)

class MqttPubAck(header: MqttHeader, variableHeader: PubAckHeader)
class PubAckHeader(messageId: MessageId)

class MqttPubRec(header: MqttHeader, variableHeader: PubRecHeader)
class PubRecHeader(messageId: MessageId)

class MqttPubRel(header: MqttHeader, variableHeader: PubRelHeader)
class PubRelHeader(messageId: MessageId)

class MqttPubComp(header: MqttHeader, variableHeader: PubCompHeader)
class PubCompHeader(messageId: MessageId)

class MqttSubscribe(header: MqttHeader, variableHeader: SubscribeHeader, payload: List[Subscription])
class SubscribeHeader(messageId: MessageId)
class Subscription(topic: Topic, qos: QoS)

class MqttSubAck(header: MqttHeader, variableHeader: SubAckHeader, payload: List[QoS])
class SubAckHeader(messageId: MessageId)

class MqttUnsubscribe(header: MqttHeader, variableHeader: UnsubscribeHeader, topics: List[Topic])
class UnsubscribeHeader(messageId: MessageId)

class MqttUnsubAck(header: MqttHeader, variableHeader: UnsubAckHeader)
class UnsubAckHeader(messageId: MessageId)

class MqttPingReq(header: MqttHeader)

class MqttPingResp(header: MqttHeader)

class MqttDisconnect(header: MqttHeader)