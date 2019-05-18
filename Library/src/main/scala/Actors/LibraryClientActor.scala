package Actors

import java.nio.file.NoSuchFileException

import Actors.LibraryServerActor._
import akka.actor.Status.Failure
import akka.actor.{Actor, ActorSelection}
import akka.event.Logging

class LibraryClientActor extends Actor {
  val server: ActorSelection = context.actorSelection("akka.tcp://LibraryServer@127.0.0.1:2552/user/remoteServer")
  val logger = Logging.getLogger(context.system, this)

  override def receive: Receive = {
    case FindBook(title) =>
      server ! FindBook(title)
    case OrderBook(title) =>
      server ! OrderBook(title)
    case StreamBook(title) =>
      server ! StreamBook(title)
    case response: ServerResponse =>
      logger.info(response.toString)
    case Failure(e: NoSuchFileException) =>
      logger.warning(e.getFile + " is not available")
    case unhandled =>
      logger.warning("Library client can not handle this message: " + unhandled)
  }
}
