import Panels.ElevatorPanel
import Traits.{Elevator, Panel}

import scala.collection.mutable.ArrayBuffer

object ElevatorImpl{
  def unapply(elevator: ElevatorImpl): Option[(Int, Int, Int, Int)] = {
    Some((elevator.id, elevator.currentFloor, elevator.direction, elevator.currentDestination))
  }
}
class ElevatorImpl(val id: Int, var currentFloor: Int, var direction: Int, var currentDestination:Int, val panel:ElevatorPanel) extends Elevator {
  override def getStatus: (Int, Int, Int) = {
    (id,currentFloor,currentDestination)
  }
  
  def chooseFloor(floor:Int):Unit = {
    panel.chooseFloorButtonPressed(id, floor)
  }
  
  def setFloor(floor:Int):Unit= currentFloor = floor
  def setDestination(destination:Int):Unit= currentDestination = destination

}