package Actors

import Actors.LibraryServerActor.{BookAvailable, BookUnavailable}
import akka.actor.{Actor, Props, _}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

final case class Book(title: String, price: Double)


class FindBookWorker(implicit databases: List[String]) extends Actor {
  implicit val timeout = Timeout(5.seconds)


  import DatabaseSearchActor.SearchDatabase

  val logger = Logging.getLogger(context.system, this)

  override def preStart(): Unit = {
    databases.foreach(database => context.actorOf(Props(new DatabaseSearchActor(database)), database))
  }

  override def receive: Receive = {
    case LibraryServerActor.FindBook(title: String) =>
      val client = sender()
      val results = Future.sequence(context.children.map(w => (w ? SearchDatabase(title)).mapTo[Try[List[Book]]]))
      results.onComplete({
        case Success(books) =>
          val queriedDatabases = books.count(_.isSuccess)
          books.flatten[Book]({
            case Success(dbQuery) => dbQuery
            case Failure(_) => List.empty[Book]
          }) match {
            case h :: _ => client ! BookAvailable(title, h.price)
            case Nil => client ! BookUnavailable(title, queriedDatabases == databases.size)
          }
        case Failure(t) =>
          logger.warning("Communication timeout problems: " + t)
          client ! BookUnavailable(title, queriedAll = false)
      })
  }
}
