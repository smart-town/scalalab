package st.akkastream

import akka.Done
import akka.actor.{ActorSystem, Cancellable}
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object A_StreamBasic {


  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem()

    println("Hello2")
    val strSource = Source.tick(5.seconds, 6.seconds, "Message")
    var count = 0
    var cancelable: Option[Cancellable] = None
    val sink: Sink[String, Future[Done]] = Sink.foreach[String](f => {
      println(s"$count dealt $f")
      count += 1
      if (count > 10) {
        cancelable.foreach(cancel => cancel.cancel())
      }
    })
    val result = strSource.toMat(sink)(Keep.both).run()
    cancelable = Some(result._1)
    println(result)

  }
}
