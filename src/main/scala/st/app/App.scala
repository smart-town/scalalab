package st.app
import javax.swing.JPanel
import scala.swing.*


class App extends Frame {
  title = "HelloApp"

  contents = new FlowPanel {
    contents += new Label("Launch")
    contents += new Button("Click") {
      reactions += {
        case event.ButtonClicked(_) =>
          println(s"All ... ${this.showing}" )
      }
    }
  }

//  pack()

  private def start(): Unit = {
    println(s"starting... ${this.showing}")
//    this.
    this.centerOnScreen()
    this.open()
  }


}

object App {
  def main(args: Array[String]): Unit = {
    new App().start()
  }
}
