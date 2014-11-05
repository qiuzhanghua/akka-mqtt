package fr.sertelon.mqtt.model

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