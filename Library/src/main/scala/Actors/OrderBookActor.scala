package Actors

import java.io.FileWriter

import Actors.LibraryServerActor.{OrderBook, OrderConfirmation}
import akka.actor.Actor

class OrderBookActor(implicit ordersFile: String) extends Actor {

  import OrderBookActor.addOrder

  override def receive: Receive = {
    case OrderBook(book) =>
      val server = sender()
      addOrder(book, ordersFile)
      server ! OrderConfirmation(book)
  }
}

object OrderBookActor {
  private def addOrder(book: String, ordersFile: String): Unit = {
    val fw = new FileWriter(ordersFile, true)
    fw.write(book + "\n")
    fw.close()
  }
}
