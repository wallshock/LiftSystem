package Traits
import Panels.FloorPanel

trait Building {
    def floors:Int

    def floorPanels:Array[FloorPanel]
    def elevatorSystem:ElevatorSystem

}
