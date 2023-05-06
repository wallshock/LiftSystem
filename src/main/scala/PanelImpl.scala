import Traits.{Elevator, ElevatorSystem,Panel}

import scala.collection.mutable.ArrayBuffer

class PanelImpl(val elevatorSystem: ElevatorSystem,val elevator: Elevator) extends Panel {
  override def floorButtonPressed(floor: Int): Unit = {}
}