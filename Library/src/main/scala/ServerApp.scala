import java.io.File

import Actors.LibraryServerActor
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object ServerApp extends App {
  val configFile = getClass.getClassLoader.getResource("server.conf").getFile
  val config = ConfigFactory.parseFile(new File(configFile))
  val system = ActorSystem("LibraryServer", config)
  val libraryServer = system.actorOf(Props(new LibraryServerActor()), "remoteServer")
}
