package fr.sertelon.mqtt

import akka.actor.{ Actor, ActorRef, Props, ActorSystem }
import akka.io.{ IO, Tcp }
import akka.util.ByteString
import java.net.InetSocketAddress

object Main extends App {
  val system = ActorSystem("Akka-MQTT-Broker")
    system.actorOf(Props[Server])
}

class Server extends Actor {
  
  import Tcp._
  import context.system
  
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1883))
  
  def receive = {
    case b @ Bound(localAddress) =>
      println("hello");
    case CommandFailed(_ : Bind) =>
      context stop self
    case c @ Connected(remote, local) =>
      val handler = context.actorOf(Props[SimplisticHandler])
      val connection = sender()
      connection ! Register(handler)
  }
  
}
    
class SimplisticHandler extends Actor {
  import Tcp._
  
  val decoder = context.actorOf(Props[Decoder])
  
  def receive = {
    case Received(data) => decoder ! data
    case PeerClosed => context stop self
  }
}