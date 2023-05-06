import Traits.{Elevator, ElevatorSystem}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ElevatorSystemImpl extends ElevatorSystem {
  var elevators: ArrayBuffer[Elevator] = ArrayBuffer()
  var goDownRequest: ArrayBuffer[Int] =  ArrayBuffer()
  var goUpRequest: ArrayBuffer[Int] =  ArrayBuffer()

  var destinationRequests = collection.mutable.Map.empty[Int, List[Int]] // elevatorId -> destinationFloors
  private var emergencyStopped = false

  override def pickup(floor: Int, direction: Int): Unit = {

  }

  override def update(elevatorId: Int, currentFloor: Int, destinationFloor: Int): Unit = {

  }

  override def addElevator(elevator: Elevator): Unit = {
    elevators += elevator
    println("Added elevator")
  }

  override def step(): Unit = {

  }

  override def status: Array[(Int, Int, Int)] =
    elevators.map(x => x.getStatus).toArray
}
