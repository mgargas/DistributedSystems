package Actors

import java.nio.file.Paths

import Actors.LibraryServerActor.{StreamBook, StreamedMessage, StreamingFinished}
import akka.actor.Actor
import akka.stream.{ActorMaterializer, IOResult, ThrottleMode}
import akka.util.ByteString

import scala.concurrent.Future
import scala.concurrent.duration._


class StreamBookActor extends Actor {
  override def receive: Receive = {
    case StreamBook(title) =>
      import akka.stream.scaladsl._
      val client = sender()
      val file = Paths.get("src/main/resources/" + title)
      implicit val materializer: ActorMaterializer = ActorMaterializer.create(context)

      val source: Source[ByteString, Future[IOResult]] = FileIO.fromPath(file)

      val flow =
        Flow[ByteString]
          .via(Framing.delimiter(ByteString(System.lineSeparator()), 10000))
          .throttle(1, 1.second, 1, ThrottleMode.shaping)
          .map(bs => StreamedMessage(bs.utf8String))

      source.via(flow).runWith(Sink.actorRef(client, StreamingFinished))
  }
}
