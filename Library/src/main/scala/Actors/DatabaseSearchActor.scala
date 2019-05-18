package Actors

import akka.actor.Actor

import scala.io.Source
import scala.util.Try

class DatabaseSearchActor(database: String) extends Actor {

  import DatabaseSearchActor._

  override def receive: Receive = {
    case SearchDatabase(title) =>
      sender() ! searchBook(title, database)
  }
}

object DatabaseSearchActor {

  private def searchBook(title: String, database: String): Try[List[Book]] = {
    val dbRequest: Try[List[String]] = Try(Source.fromFile("src/main/resources/" + database).getLines.toList)
    dbRequest map {
      books =>
        books
          .map(_.split(":").toList)
          .collect({
            case _ :: bookTitle :: price :: _ if bookTitle.equals(title) =>
              Book(bookTitle, price.toDouble)
          })
    }
  }

  final case class SearchDatabase(title: String)

}
