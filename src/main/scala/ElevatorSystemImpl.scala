import Traits.{Elevator, ElevatorSystem}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

class ElevatorSystemImpl(val floors:Int) extends ElevatorSystem {
  var goDownRequest:Array[Boolean] = Array()
  var goUpRequest:Array[Boolean] = Array()
  var elevators: ArrayBuffer[Elevator] = ArrayBuffer()

  floors match {
    case x if x >= 0 =>
      goDownRequest = new Array[Boolean](floors+1)
      goUpRequest = new Array[Boolean](floors+1)
    case _ =>
      throw new IllegalArgumentException("Value must be greater than or equal to 0.")
  }





  var destinationRequests = collection.mutable.Map.empty[Int, List[Int]] // elevatorId -> destinationFloors
  private var emergencyStopped = false

  override def pickup(floor: Int, direction: Int): Unit = {
      direction match {
        case x if x == 1 =>
          goUpRequest(floor)=true
        case x if x == -1 =>
          goDownRequest(floor)=true
        case _ =>
          throw new IllegalArgumentException("Direction must be either up or down. ( 1 or -1)")
      }
  }

  override def update(elevatorId: Int, currentFloor: Int, destinationFloor: Int): Unit = {
    breakable {
      for (elevator <- elevators) {
        elevator match {
          case ElevatorImpl(`elevatorId`,_,_,_,_) =>
            elevator.setFloor(currentFloor)
            elevator.setDestination(destinationFloor)
            break
          case _ =>
        }
      }
    }
  }

  override def addElevator(elevator: Elevator): Unit = {
    elevators += elevator
    println("Added elevator")
  }

  override def step(): Unit = {
    //dla wszystkich wind po kolei zaktualizuj ich currentfloor direction i destination
    //rusz o 1 do przodu kazda ktora ma inny currentfloor od destination
  }

  override def status: Array[(Int, Int, Int)] =
    elevators.map(x => x.getStatus).toArray
}
