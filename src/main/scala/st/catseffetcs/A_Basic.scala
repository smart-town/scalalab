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

  def forLoop(): Unit = {
    val ints = Seq(1,2,3,4)
    for i <- ints do println(i)

    println("code i <- ints is referred to as a generator")
    for i <- ints do
      val x = i * 2
      println(x)

    println("multiple generators")
    for
      i <- 1 to 2
      j <- 'a' to 'b'
      k <- 1 to 10 by 5
    do
      println(s"i=$i j=$j k=$k")

    println("Guard...")
    for
      i <- ints
      if i % 2 == 0
      if i > 2
    do
      println(i)

    println("\n Using for with Maps. You can also use for loop with a Map")
    val states = Map(
      "AK" -> "Alak",
      "AL" -> "Alab",
      "AR" -> "Ariza",
    )
    for (abbrev, fullName) <- states
    do
      println(s"$abbrev, $fullName")


    println(
      """
        |In the previous for loop examples, those loops were all used for side effects(use println)
        |It's important to know that you can also create for expressions that return values.
        |You create a for expression by adding the yield keyword and an expression to return,
        |""".stripMargin)
    val list2 =
      for i <- 8 to 11 yield i * 2

    println(list2)

    println(
      """
        |while the intent of here is to demonstrate for expressions, it can help to know
        |that for expression shown is equivalent to this map method call:
        |val list = (8 to 11).map(i => i * 2)
        |
        |for expression can be used any time you need to traverse all the elements in a collection and apply
        |an algorithm to those elements to create a new list.
        |
        |""".stripMargin)

    println(
      """
        |当 for 包含一个生成器时，会使用 map 方法
        |""".stripMargin)
    val l1 = List(4,5,6)
    val l1_1 = for x <- l1 yield x * 2
    val l1_2 = l1.map(x => x * 2)
    println(l1_1)
    println(l1_2)

    println(
      """
        |包含多个生成器时，第一个生成器直到倒数第一个生成器都是 flatMap 操作，最后一个使用`map`操作。
        |如果最后一个是过滤器，那么会先使用`withFilter`再使用`map`
        |
        |""".stripMargin)
    val l2 = List(1,2,3)
    val l2_1 = for
        x <- l2
        y <- l1
      yield x + y
    val l2_2 = l2.flatMap(
      x => l1.map(y => x + y)
    )
    println(l2_1)
    println(l2_2)
  }

  def run: IO[Unit] = {
    forLoop()
    basic()
  }
}
