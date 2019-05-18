import java.io.File

import Actors.{LibraryClientActor, LibraryServerActor}
import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import com.typesafe.config.ConfigFactory

object ClientApp extends App {
  val configFile = getClass.getClassLoader.getResource("client.conf").getFile
  val config = ConfigFactory.parseFile(new File(configFile))
  val system = ActorSystem("LibraryClient", config)
  val libraryClient = system.actorOf(Props(new LibraryClientActor()))
  val logger = Logging.getLogger(system, this)
  while (true) {
    val line = scala.io.StdIn.readLine.split(" ").toList
    line match {
      case "find" :: title :: _ => libraryClient ! LibraryServerActor.FindBook(title)
      case "order" :: title :: _ => libraryClient ! LibraryServerActor.OrderBook(title)
      case "stream" :: title :: _ => libraryClient ! LibraryServerActor.StreamBook(title)
      case unhandled => logger.warning("Client application can not handle this input: " + unhandled)
    }
  }
}
