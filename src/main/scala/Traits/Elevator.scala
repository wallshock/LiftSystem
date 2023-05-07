package Traits

import scala.collection.mutable.ArrayBuffer

trait Elevator {
  def id: Int

  def currentFloor: Int

  def currentDirection: Int  // -1 going down, 0 not moving, 1 going up

  def currentDestination: Int
  
  def panel: Panel
  
  def setFloor(floor:Int):Unit
  def setDirection(direction:Int):Unit
  def setDestination(floor:Int):Unit
  def getStatus:(Int,Int,Int)
}
