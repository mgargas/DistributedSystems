package Actors

import Actors.LibraryServerActor.{BookAvailable, BookUnavailable}
import akka.actor.{Actor, Props, _}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

final case class Book(title: String, price: Double)


class FindBookWorker(implicit databases: List[String]) extends Actor {
  implicit val timeout = Timeout(5.minutes)

  import DatabaseSearchActor.SearchDatabase

  override def preStart(): Unit = {
    databases.foreach(database => context.actorOf(Props(new DatabaseSearchActor(database)), database))
  }

  override def receive: Receive = {
    case LibraryServerActor.FindBook(title: String) =>
      println(title)
      val server = sender()
      val results = Future.sequence(context.children.map(w => (w ? SearchDatabase(title)).mapTo[List[Book]]))
      results.onComplete({
        case Success(books) =>
          books.flatten match {
            case h :: _ => server ! BookAvailable(title, h.price)
            case Nil => server ! BookUnavailable(title)
          }
        case Failure(t) =>
          t.printStackTrace()
          server ! BookUnavailable(title)
      })
  }
}
