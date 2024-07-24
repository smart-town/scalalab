package st.catseffects

import cats.effect.{IO, IOApp}
import scala.concurrent.duration._


object B_Concept extends IOApp.Simple {
  val run: IO[Unit] = {
//    lazy val loop: IO[Unit] = IO.println("Hello Cancelation") >> loop
//    loop.timeout(2.seconds) >>
    IO.println(
      """
        |Fibers are the fundamental abstraction in Cats Effect. The terminology is intentionally reminiscent of `Threads` since fibers
        |are literally lightweight threads.(often referred to as "green threads" or "coroutines")
        |Much like threads, they represent a sequence
        |""".stripMargin) >>
      IO.println("Every application has a 'main fiber', this is very similar to the notion of a 'main thread' in that it is the point at which control flow start  within the process.") >>
      IO.println("When one fiber starts another fiber, we generally say that the first fiber is the 'parent' of the second one. This relationship is not directly hierarchical in that the parent can terminate before the child without causing any inconsistencies.")
  }
}