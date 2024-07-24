package st.scala3


extension (msg: String) {
  def hello(): Unit = {
    println(s"Hello!$msg")
  }
}

trait Ord[A]:
  def greaterThan(a1: A, a2: A): Boolean

def max[A](a1: A, a2: A)(using ord: Ord[A]): A = {
  if ord.greaterThan(a1, a2) then a1 else a2
}

def maxElement[A](as: List[A])(using ord: Ord[A]): A = {
  //    given Ord[A] = ord
  as.reduceLeft(max(_, _))
}


def maxElement2[A: Ord](as: List[A]): A = {
  println(
    """
      |上下文绑定时一种简写语法，用于表达“依赖于类型参数的上下文参数”模式
      |
      |方法或类的类型参数为 A, 有类似 :Ord 的绑定，表示有`Ord[A]`的上下文参数。
      |在后台，编译器将此语法转换为`maxElement`中的语法
      |""".stripMargin)
  as.reduceLeft(max(_, _))
}

case class User(name: String)

class UserOrd[A <: User] extends Ord[A] {
  override def greaterThan(a1: A, a2: A): Boolean = {
    a1.name.length < a2.name.length
  }
}

object TypeSystem01 {
  println(
    """
      |scala 是静态类型的。由于类型推断有时候可以无需指定类型。
      |
      |此外 scala3 中有联合类型，如： def isTruthy(a Boolean | Int | String): Boolean = ???
      |
      |
      |## 泛型
      |泛型类或者`traits`把在方括号中的`[...]`类型作为**参数**进行调用。Scala 约定使用单个字母来命名这些类型参数，需要时该类型可以在类中用于方法实例参数或返回类型
      |""".stripMargin)

  def genericType(): Unit = {
    class GenericMy[A]:
      private var ele: List[A] = Nil
      def push(x: A): Unit = {ele = ele.prepended(x)}
  }

  println(
    """
      |上下文绑定
      |
      |""".stripMargin
  )

  def main(args: Array[String]): Unit = {
    val users = List(
      User("smalltown"),
      User("luhanghang"),
      User("cherry"),
      User("hanyingchao"),
    )

//    given
    val userOrd = UserOrd[User]()
    given Ord[User] = userOrd


    val maxUser2 = maxElement(users)
    println(maxUser2)

    val maxUser = maxElement2[User](users)
    println(maxUser)
  }








}
