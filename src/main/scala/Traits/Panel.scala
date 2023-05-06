package Traits

trait Panel{
  
  def elevatorSystem:ElevatorSystem
  def elevator: Elevator
  def floorButtonPressed(floor:Int):Unit
}
