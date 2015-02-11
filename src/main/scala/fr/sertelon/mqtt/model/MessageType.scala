package fr.sertelon.mqtt.model

sealed trait MessageType
case object ConnectMessageType extends MessageType
case object ConnAckMessageType extends MessageType
case object PublishMessageType extends MessageType
case object PubAckMessageType extends MessageType
case object PubRecMessageType extends MessageType
case object PubRelMessageType extends MessageType
case object PubCompMessageType extends MessageType
case object SubscribeMessageType extends MessageType
case object SubAckMessageType extends MessageType
case object UnsubscribeMessageType extends MessageType
case object UnsubAckMessageType extends MessageType
case object PingReqMessageType extends MessageType
case object PingRespMessageType extends MessageType
case object DisconnectMessageType extends MessageType
case object UnknownMessageType extends MessageType

object MessageType {
  implicit def get(number: Int): MessageType = {
    number match {
      case 1 => ConnectMessageType
      case 2 => ConnAckMessageType
      case 3 => PublishMessageType
      case 4 => PubAckMessageType
      case 5 => PubRecMessageType
      case 6 => PubRelMessageType
      case 7 => PubCompMessageType
      case 8 => SubscribeMessageType
      case 9 => SubAckMessageType
      case 10 => UnsubscribeMessageType
      case 11 => UnsubAckMessageType
      case 12 => PingReqMessageType
      case 13 => PingRespMessageType
      case 14 => DisconnectMessageType
      case _ => UnknownMessageType
    }
  }
}