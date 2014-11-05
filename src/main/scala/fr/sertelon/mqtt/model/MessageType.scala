package fr.sertelon.mqtt.model

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