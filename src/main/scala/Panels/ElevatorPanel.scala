package Panels

import Traits.{Elevator, ElevatorSystem, Panel}

import scala.collection.mutable.ArrayBuffer

class ElevatorPanel(val elevatorSystem: ElevatorSystem) extends Panel {
  def chooseFloorButtonPressed(id:Int,floor: Int): Unit = {
    elevatorSystem.addElevatorDestination(id,floor)
  }
}