import Panels.FloorPanel
import Traits.{Building, ElevatorSystem}

class AVSystemHeadquaters(val floors:Int,val elevatorSystem:ElevatorSystem) extends Building{
  val floorPanels = new Array[FloorPanel](floors)
}
