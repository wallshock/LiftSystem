package Panels

import Traits.{ElevatorSystem, Panel}

class FloorPanel(val floor:Int,val elevatorSystem: ElevatorSystem) extends Panel{
  def goUpButtonPressed(): Unit = {
    elevatorSystem.pickup(floor,1)
  }

  def goDownButtonPressed(): Unit = {
    elevatorSystem.pickup(floor,-1)
  }
}
