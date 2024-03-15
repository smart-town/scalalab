package st

trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}

//def combineAll[A <: Monoid[A]](list: List[A]): A = ???


object App {
  def main(args: Array[String]): Unit = {
    println("starting...")
  }
}
