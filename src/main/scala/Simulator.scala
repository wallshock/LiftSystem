import scala.collection.mutable.ArrayBuffer
import Panels.ElevatorPanel
class Simulator(val floors:Int, val elevators:Int) {

  def run(): Unit = {
    val system: ElevatorSystemImpl = ElevatorSystemImpl(floors)
    val AVSysytemHq = AVSystemHeadquaters(floors,system)
    for (i <- 0 until elevators) {
      Thread.sleep(1000)
      system.addElevator(ElevatorImpl(i, 0, 0, 0,ElevatorPanel(system)))
    }
    for(i<- 1 to floors/2){
        Thread.sleep(1000)
        AVSysytemHq.floorPanels(i).goUpButtonPressed()
        AVSysytemHq.floorPanels(2*i).goDownButtonPressed()
        AVSysytemHq.elevatorSystem.step()
    }
    while(true){
      Thread.sleep(1000)
      AVSysytemHq.elevatorSystem.step()
    }
  }
}
