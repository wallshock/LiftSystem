import scala.collection.mutable.ArrayBuffer

class Simulator(val floors:Int, val elevators:Int) {

  def run(): Unit = {
    val system: ElevatorSystemImpl = ElevatorSystemImpl(floors)
    for (i <- 0 until elevators) {
      system.addElevator(ElevatorImpl(i, 0, 0, 0,ElevatorPanel(system)))
    }
  }
}
