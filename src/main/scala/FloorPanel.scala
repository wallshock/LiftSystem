import Traits.{ElevatorSystem, Panel}

class FloorPanel(val elevatorSystem: ElevatorSystem) extends Panel{
  def goUpButtonPressed(floor: Int): Unit = {
    elevatorSystem.pickup(floor,1)
  }

  def goDownButtonPressed(floor: Int): Unit = {
    elevatorSystem.pickup(floor,0)
  }
}
