import Traits.GuiObserver
import javafx.application.{Application, Platform}
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.control.{Label, TextField}
import javafx.scene.layout.{BorderPane, GridPane, VBox}
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import javafx.stage.WindowEvent
import javafx.scene.control.ChoiceBox
import scalafx.scene.control.Button
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.layout.HBox
class GameApp extends Application,GuiObserver {
  var simulator: Simulator = null
  var floors = 0
  var elevators = 0
  var rectangleGrid = new GridPane()
  val grid = new GridPane()

  import javafx.event.EventHandler
  import javafx.event.EventHandler


  def exithandle(stage:Stage):Unit={
    stage.setOnCloseRequest(new EventHandler[WindowEvent]() {
      override def handle(event: WindowEvent): Unit = {
        Platform.exit()
        System.exit(0)
      }
    })
  }

  def floorBox(): HBox = {
    val label = new Label("Panel Przywołania <Piętro : Góra | Dół> ")
    val floorNumber = ObservableBuffer(0 until floors: _*)
    val floorBox = new ChoiceBox[Int](floorNumber)

    val elevatorDirection = ObservableBuffer("Góra","Dół")
    val elevBox = new ChoiceBox[String](elevatorDirection)

    val okButton = new Button("OK")
    okButton.onAction = _ => {
      val floor = floorBox.getValue
      val direction = elevBox.getValue
      if(direction == "Góra"){
        simulator.AVSysytemHq.floorPanels(floor).goUpButtonPressed()
      }else{
        simulator.AVSysytemHq.floorPanels(floor).goDownButtonPressed()
      }

    }
    val hbox = new HBox()
    hbox.children.addAll(label, floorBox,elevBox,okButton)
    hbox
  }

  def simulateButton():Button={
    val toggleButton = new Button("Włącz/Wyłącz Symulacje")
    toggleButton.onAction = _ => {
      simulator.toggleSimulation()
    }
    toggleButton
  }
  def elevatorBox():HBox={
    val label = new Label("Panel Wewnętrzny <Winda : Cel> ")
    val elevatorNumbers = ObservableBuffer(0 until elevators: _*)
    val elevBox = new ChoiceBox[Int](elevatorNumbers)
    val floorNumber = ObservableBuffer(0 until floors: _*)
    val floorBox = new ChoiceBox[Int](floorNumber)
    val okButton = new Button("OK")
    okButton.onAction = _ => {
      val elev = simulator.system.elevators(elevBox.getValue)
      val floor = floorBox.getValue
      elev.panel.chooseFloorButtonPressed(elev.id, floor)
    }
    val hbox = new HBox()
    hbox.children.addAll(label,elevBox, floorBox, okButton)
    hbox
  }

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
    exithandle(primaryStage)

    submitButton.setOnAction { _ =>
      elevators = xField.getText.toInt
      floors = yField.getText.toInt
      // create grid based on x and y values
      rectangleGrid = new GridPane()
      simulator = new Simulator(floors, elevators, this)
      for {
        i <- 0 until elevators
        j <- 0 until floors
      } {
        val rectangle = new Rectangle(35, 70)
        rectangle.setFill(Color.WHITE)
        rectangle.setStroke(Color.BLACK)
        rectangleGrid.add(rectangle, i, j)
      }

      val elevatorHBox = elevatorBox()
      val floorHBox = floorBox()
      val button = simulateButton()

      elevatorHBox.setPadding(new Insets(10, 10, 10, 10))
      floorHBox.setPadding(new Insets(10, 10, 10, 10))
      button.setPadding(new Insets(10, 10, 10, 10))
      val rightVBox = new VBox(elevatorHBox, floorHBox,button)
      val borderPane = new BorderPane()
      borderPane.setLeft(rectangleGrid)
      borderPane.setRight(rightVBox)

      val scene = new Scene(borderPane, 45 * elevators + 300, 75 * floors)
      primaryStage.setScene(scene)
      primaryStage.show()

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