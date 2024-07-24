package st

trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}

//def combineAll[A <: Monoid[A]](list: List[A]): A = ???


object App {
  def main(args: Array[String]): Unit = {
    println("starting...")

    val array1 = Array(1,2,3,4)
    val array2 = array1.view.map { item => {
      println(s"double $item")
      item * 2
    }}
    println("declared array2")
    array2.foreach{item => {
      println(item)
    }}

//    val parArray2 = ParArray.fromArray(array1)
  }
}
