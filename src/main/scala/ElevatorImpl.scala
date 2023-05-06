import Traits.{Elevator, Panel}

import scala.collection.mutable.ArrayBuffer

object ElevatorImpl{
  def unapply(elevator: ElevatorImpl): Option[(Int, Int, Int, Int, collection.mutable.ArrayBuffer[Int])] = {
    Some((elevator.id, elevator.currentFloor, elevator.direction, elevator.currentDestination, elevator.targets))
  }
}
class ElevatorImpl(val id: Int, var currentFloor: Int, var direction: Int, var currentDestination:Int, var targets:ArrayBuffer[Int], val panel:Panel) extends Elevator {
  override def getStatus: (Int, Int, Int) = {
    (id,currentFloor,currentDestination)
  }
  
  def setFloor(floor:Int):Unit= currentFloor = floor
  def setDestination(destination:Int):Unit= currentDestination = destination

}