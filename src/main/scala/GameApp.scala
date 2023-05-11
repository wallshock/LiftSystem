import Traits.GuiObserver
import javafx.application.{Application, Platform}
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.GridPane
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class GameApp extends Application,GuiObserver {
  var simulator: Simulator = null
  var floors = 0
  var elevators = 0
  var rectangleGrid = new GridPane()
  val grid = new GridPane()

  override def start(primaryStage: Stage): Unit = {
    grid.setPadding(new Insets(10, 10, 10, 10))
    grid.setVgap(10)
    grid.setHgap(10)

    val xLabel = new Label("Podaj ilość wind:")
    val xField = new TextField()
    grid.add(xLabel, 0, 0)
    grid.add(xField, 1, 0)

    val yLabel = new Label("Podaj ilość pięter:")
    val yField = new TextField()
    grid.add(yLabel, 0, 1)
    grid.add(yField, 1, 1)

    val submitButton = new Button("Create Building")
    GridPane.setColumnSpan(submitButton, 2)
    grid.add(submitButton, 0, 2)

    submitButton.setOnAction { _ =>
      elevators = xField.getText.toInt
      floors = yField.getText.toInt
      // create grid based on x and y values
      rectangleGrid = new GridPane()
      simulator = new Simulator(floors,elevators,this)
      for {
        i <- 0 until elevators
        j <- 0 until floors
      } {
        val rectangle = new Rectangle(35, 70)
        rectangle.setFill(Color.WHITE)
        rectangle.setStroke(Color.BLACK)
        rectangleGrid.add(rectangle, i, j)
      }

      val scene = new Scene(rectangleGrid, 55 * elevators, 90 * floors)
      primaryStage.setScene(scene)

      Thread(simulator).start()
    }

    primaryStage.setTitle("Elevator App")
    primaryStage.setScene(new Scene(grid, 300, 150))
    primaryStage.show()
  }

  def updateGuiBefore(): Unit = {
    try {
      Platform.runLater(this.updateGuiViewsBefore)
      Thread.sleep(1)
    } catch {
      case ex: InterruptedException =>
        System.out.println(ex.getMessage)
    }
  }

  def updateGuiAfter(): Unit = {
    try {
      Platform.runLater(this.updateGuiViewsAfter)
      Thread.sleep(800)
    } catch {
      case ex: InterruptedException =>
        System.out.println(ex.getMessage)
    }
  }

  def updateGridAfter(grid: GridPane, floor: Int, id: Int): Unit = {
    val rectangle = new Rectangle(35, 70)
    rectangle.setFill(Color.BLACK)
    rectangle.setStroke(Color.BLACK)
    grid.add(rectangle, (elevators - 1) - id,(floors - 1) - floor)
  }

  def updateGridBefore(grid: GridPane, floor: Int, id: Int): Unit = {
    val rectangle = new Rectangle(35, 70)
    rectangle.setFill(Color.WHITE)
    rectangle.setStroke(Color.BLACK)
    grid.add(rectangle,(elevators - 1) - id, (floors - 1) - floor)
  }

  val updateGuiViewsAfter: Runnable = new Runnable() {
    def run(): Unit = {
      simulator.system.elevators.foreach(elevator => updateGridAfter(rectangleGrid, elevator.currentFloor, elevator.id))
    }
  }

  val updateGuiViewsBefore: Runnable = new Runnable() {
    def run(): Unit = {
      simulator.system.elevators.foreach(elevator => updateGridBefore(rectangleGrid, elevator.currentFloor, elevator.id))
    }
  }
}

object GameApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GameApp], args: _*)
  }
}