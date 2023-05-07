import Traits.{Elevator, ElevatorSystem}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

class ElevatorSystemImpl(val floors:Int) extends ElevatorSystem {
  var elevators: ArrayBuffer[Elevator] = ArrayBuffer()
  val destinationRequests = collection.mutable.Map.empty[Int, ArrayBuffer[Int]] // elevatorId -> destinationFloors
  private var emergencyStopped = false

  override def pickup(floor: Int, direction: Int): Unit = {
    chooseBestElevator(floor, direction).map { result =>
      destinationRequests(result) += floor
    }.getOrElse {
      // handle the case where no suitable elevator is available
      println(s"No elevator available to pick up from floor $floor going $direction")
    }

//      direction match {
//        case x if x == 1 =>
//          goUpRequest(floor)=true
//        case x if x == -1 =>
//          goDownRequest(floor)=true
//        case _ =>
//          throw new IllegalArgumentException("Direction must be either up or down. ( 1 or -1)")
//      }
  }

  override def update(elevatorId: Int, currentFloor: Int, destinationFloor: Int): Unit = {
    breakable {
      for (elevator <- elevators) {
        elevator match {
          case ElevatorImpl(`elevatorId`, _, _, _) =>
            // Update the elevator's current floor and destination floor
            elevator.setFloor(currentFloor)
            elevator.setDestination(destinationFloor)

            // Update the elevator's direction based on the new destination floor
            val direction = if (destinationFloor < currentFloor) {
              -1
            } else if (destinationFloor > currentFloor) {
              1
            } else {
              0
            }
            elevator.setDirection(direction)

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

  override def addElevatorDestination(id: Int, destination: Int): Unit = {
    if (!destinationRequests.contains(id)) {
      destinationRequests(id) = collection.mutable.ArrayBuffer.empty[Int]
    }
    val destFloors = destinationRequests(id)
    if (!destFloors.contains(destination)) {
      destFloors += destination
    }
  }

  override def status: Array[(Int, Int, Int)] =
    elevators.map(x => x.getStatus).toArray

  def chooseBestDestination(id: Int): Unit = {
    val destinations = destinationRequests(id)
    // Get the elevator with the given id
    val elevator = elevators(id)
    var bestfloor = 0

    // If the elevator has no destinations, return None
    if (destinations.isEmpty) {
        update(id,elevator.currentFloor,bestfloor)
    }

    // Get the elevator's current floor and direction
    val currentFloor = elevator.currentFloor
    val direction = elevator.currentDirection
    if (direction == 0) {
      bestfloor = destinations.minBy(floor => math.abs(floor - currentFloor))
      update(id,currentFloor,bestfloor)
    }

    // If the elevator is moving up, find the closest destination above the current floor
    if (direction == 1) {
      val closestFloor = destinations.filter(_ >= currentFloor).minBy(floor => math.abs(floor - currentFloor))
      update(id,currentFloor,bestfloor)
    }

    // If the elevator is moving down, find the closest destination below the current floor
    if (direction == -1) {
      val closestFloor = destinations.filter(_ =< currentFloor).minBy(floor => math.abs(floor - currentFloor))
      update(id,currentFloor,bestfloor)
    }

  }
  def chooseBestElevator(requestFloor: Int, requestDirection: Int): Option[Int] = {
    val possibleElevators = elevators.filter { elevator =>
      if (elevator.currentFloor == requestFloor) {
        true // if an elevator is already on the same floor as the request, it is a possible candidate
      } else if (requestDirection == 1) {
        (elevator.currentDirection == 1 || elevator.currentDirection == 0) && elevator.currentFloor < requestFloor // the elevator must be going up and below the request floor
      } else if (requestDirection == -1) {
        (elevator.currentDirection == -1 || elevator.currentDirection == 0) && elevator.currentFloor > requestFloor // the elevator must be going down and above the request floor
      } else {
        false // invalid request direction
      }
    }

    if (possibleElevators.nonEmpty) {
      // find the closest elevator among the possible candidates
      Some(possibleElevators.minBy(elevator => math.abs(elevator.currentFloor - requestFloor)).id)
    } else {
      // find the closest elevator among all elevators
      elevators.foldLeft(Option.empty[Elevator]) { (closestElevatorOpt, elevator) =>
        closestElevatorOpt match {
          case Some(closestElevator) =>
            val closestDistance = math.abs(closestElevator.currentFloor - requestFloor)
            val currentDistance = math.abs(elevator.currentFloor - requestFloor)
            if (currentDistance < closestDistance) {
              Some(elevator)
            } else {
              closestElevatorOpt
            }
          case None =>
            Some(elevator)
        }
      }.map(_.id)
    }
  }


    override def step(): Unit = {
    //dla wszystkich wind po kolei zaktualizuj ich currentfloor direction i destination
    //rusz o 1 do przodu kazda ktora ma inny currentfloor od destination
    for (elevator <- elevators) {
      val destination = destinationRequests.get(elevator.id)
      //check if we reached some destination
          // if yes then pick a new destination with the same direction
      //if pick a new destination with the same direction or continue to same destination

      destination match {
        case Some(destination) if destination.nonEmpty =>
          if (elevator.currentDirection == 1) {
            if (elevator.currentFloor < destination(0))
               // Closest destination above current floor
          } else if (elevator.currentDirection == -1) {
            destination.max // Closest destination below current floor
          } else {
            elevator.currentFloor // Stay on current floor if no direction
          }
        case _ =>
          elevator.currentFloor // Stay on current floor if no requests
      }
    }

  }
}
