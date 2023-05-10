import scala.collection.mutable.ArrayBuffer
import Panels.ElevatorPanel
import Traits.{Building, GuiObserver}
import javafx.scene.layout.GridPane

import java.lang.Runnable
import scala.util.Random
class Simulator(val floors:Int, val elevators:Int,observer:GuiObserver) extends Runnable {
  
  val system: ElevatorSystemImpl = ElevatorSystemImpl(floors)
  val AVSysytemHq: AVSystemHeadquaters = AVSystemHeadquaters(floors, system)
  def randomFloorPanelPickup(building:Building):Unit={
    //choose random floor and up or down direction to go
    val randomFloor = Random.nextInt(building.floors+1)
    val updown = Random.nextInt(2)
    if (updown == 1) building.floorPanels(randomFloor).goUpButtonPressed()
    else building.floorPanels(randomFloor).goDownButtonPressed()
  }

  def randomElevatorPanelTarget(building:Building): Unit = {
    //choose random elevator and in it choose random floor destination
    val randomFloor = Random.nextInt(building.floors+1)
    val randomElev = Random.nextInt(building.elevatorSystem.elevators.length)
    val elev = building.elevatorSystem.elevators(randomElev)
    elev.panel.chooseFloorButtonPressed(elev.id,randomFloor)

  }
  def init():Unit = {
    for (i <- 0 until elevators) {
      system.addElevator(ElevatorImpl(i, 0, 0, 0, ElevatorPanel(system)))
    }
  }
  
  def run(): Unit = {
    init()
    while(true) {
      val randomInt = Random.nextInt(10)
      if (randomInt == 1) randomFloorPanelPickup(AVSysytemHq)
      else if (randomInt == 2) randomElevatorPanelTarget(AVSysytemHq)
      observer.updateGuiBefore()
      AVSysytemHq.elevatorSystem.step()
      observer.updateGuiAfter()
    }

  }
}
