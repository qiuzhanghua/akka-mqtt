package fr.sertelon.mqtt

import akka.actor._
import akka.io._
import akka.util.ByteString
import java.net.InetSocketAddress

object Main extends App {
  val system = ActorSystem("Akka-MQTT-Broker")
  system.actorOf(Props[Server], "server")
}

class Server extends Actor {
  
  import Tcp._
  import context.system
  
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1883))
  
  def receive = {
    case b @ Bound(localAddress) =>
      println("MQTT broker listening on localhost:1883");
    case CommandFailed(_ : Bind) =>
      context stop self
    case c @ Connected(remote, local) =>
      val handler = context.actorOf(Props[MessageDispatcher])
      val connection = sender
      connection ! Register(handler)
  }
  
}
    
