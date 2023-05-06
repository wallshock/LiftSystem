import Traits.Elevator

class ElevatorImpl(val id: Int, var currentFloor: Int, var direction: Int, var target:Int) extends Elevator {
  override def getStatus: (Int, Int, Int) = {
    (id,currentFloor,target)
  }

}