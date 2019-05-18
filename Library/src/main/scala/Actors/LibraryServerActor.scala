package Actors

import akka.actor.{Actor, Props}
import akka.event.Logging

class LibraryServerActor extends Actor {
  private implicit val databases: List[String] = List("libraryDatabase2")
  private implicit val ordersFile: String = "src/main/resources/orders"
  val logger = Logging.getLogger(context.system, this)

  import LibraryServerActor._

  override def receive: Receive = opened

  def opened: Receive = {
    case FindBook(title) =>
      context.actorOf(Props(new FindBookWorker)).forward(FindBook(title))
    case OrderBook(title) =>
      context.actorOf(Props(new OrderBookActor)).forward(OrderBook(title))
    case StreamBook(title) =>
      context.actorOf(Props(new StreamBookActor)).forward(StreamBook(title))
    case unhandled => logger.warning("Library server can not handle this message: " + unhandled)

  }
}

object LibraryServerActor {


  trait ServerRequest

  trait ServerResponse

  final case class FindBook(title: String) extends ServerRequest

  final case class OrderBook(title: String) extends ServerRequest

  final case class StreamBook(title: String) extends ServerRequest

  final case class BookAvailable(title: String, price: Double) extends ServerResponse

  final case class OrderConfirmation(title: String) extends ServerResponse

  final case class BookUnavailable(title: String) extends ServerResponse

  final case class StreamedMessage(line: String) extends ServerResponse

  final case object StreamingFinished extends ServerResponse

}
