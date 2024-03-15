package st.catseffetcs

import cats.effect.{IO, IOApp}

object A_Basic extends IOApp.Simple {
  def basic(): IO[Unit] = {
    println(
      """
        |Cats Effect 中的 IO 类型是函数式编程中用于描述带有副作用的计算的一种类型。
        |""".stripMargin)
    val helloIO: IO[Unit] = IO {
      println("Hello Cats Effect")
    }
    helloIO
  }

  def run: IO[Unit] = {
    basic()
  }
}
