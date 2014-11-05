package fr.sertelon.mqtt.model

object QoS {
  def get(number: Int): QoS = {
    number match {
      case 0 => QoS_0
      case 1 => QoS_1
      case 2 => QoS_2
      case _ => UnknownQoS
    }
  }
}
sealed trait QoS
case object QoS_0 extends QoS
case object QoS_1 extends QoS
case object QoS_2 extends QoS
case object UnknownQoS extends QoS