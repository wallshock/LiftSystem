import Panels.FloorPanel
import Traits.{Building, ElevatorSystem}

class AVSystemHeadquaters(val floors:Int,val elevatorSystem:ElevatorSystem) extends Building{
  val floorPanels: Array[FloorPanel] = Array.tabulate(floors+1)(i => new FloorPanel(i, elevatorSystem))
}
