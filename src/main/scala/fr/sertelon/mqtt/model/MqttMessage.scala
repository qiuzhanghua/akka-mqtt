package fr.sertelon.mqtt.model

import MqttMessage._
import scala.Char.char2int

// Transcription of the MQTT spec model
// Select relevant types (Char, Short, Int, etc.) beware of signed/unsigned
object MqttMessage {
	type Topic = String
	type MessageId = Char
}
trait MqttMessage
case class MqttHeader(messageType: MessageType, duplicate: Boolean, qos: QoS, retain: Boolean, length: Int)

// Publishing related messages
sealed trait PublishMqttMessage extends MqttMessage

case class MqttPublish(publishHeader: PublishHeader, payload: Option[Array[Byte]]) extends PublishMqttMessage
case class PublishHeader(topic: Topic, messageId: MessageId) 

case class MqttPubAck(messageId: MessageId) extends PublishMqttMessage

case class MqttPubRec(messageId: MessageId) extends PublishMqttMessage

case class MqttPubRel(messageId: MessageId) extends PublishMqttMessage

case class MqttPubComp(messageId: MessageId) extends PublishMqttMessage

// Subscription related messages
sealed trait SubscribeMqttMessage extends MqttMessage

case class MqttSubscribe(messageId: MessageId, subscriptions: List[Subscription]) extends SubscribeMqttMessage
case class Subscription(topic: Topic, qos: QoS)

case class MqttSubAck(messageId: MessageId, qos: List[QoS]) extends SubscribeMqttMessage

case class MqttUnsubscribe(messageId: MessageId, topics: List[Topic]) extends SubscribeMqttMessage

case class MqttUnsubAck(messageId: MessageId) extends SubscribeMqttMessage

// Ping related messages
sealed trait PingMqttMessage extends MqttMessage

case class MqttPingReq() extends PingMqttMessage

case class MqttPingResp() extends PingMqttMessage

