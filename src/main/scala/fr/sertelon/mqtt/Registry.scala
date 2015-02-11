package fr.sertelon.mqtt

import akka.actor._
import fr.sertelon.mqtt.model._

object Registry {
  case class RegisterClient(clientId: String, connection: ActorRef)
  case class UnregisterClient(clientId: String)
}

class Registry extends Actor {
  import Registry._
  
  
  def receive = {
    case RegisterClient(clientId, connection) =>
    case UnregisterClient(clientId) =>
  }
}