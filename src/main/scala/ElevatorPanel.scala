import Traits.{Elevator, ElevatorSystem,Panel}

import scala.collection.mutable.ArrayBuffer

class ElevatorPanel(val elevatorSystem: ElevatorSystem) extends Panel {
  def chooseFloorButtonPressed(floor: Int): Unit = {}
}