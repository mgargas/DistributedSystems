package Actors

import akka.actor.Actor

class DatabaseSearchActor(database: String) extends Actor {

  import DatabaseSearchActor._

  override def receive: Receive = {
    case SearchDatabase(title) =>
      sender() ! searchBook(title, database)
  }
}

object DatabaseSearchActor {

  private def searchBook(title: String, database: String) = {
    import scala.io.Source
    val books: Iterator[String] = Source.fromResource(database).getLines
    books
      .map(_.split(":").toList)
      .collect({
        case _ :: bookTitle :: price :: _ if bookTitle.equals(title) =>
          Book(bookTitle, price.toDouble)
      })
      .toList
  }

  case class SearchDatabase(title: String)

}
