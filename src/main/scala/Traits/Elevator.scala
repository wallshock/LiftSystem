package Traits
trait Elevator {
  def id: Int

  def currentFloor: Int

  def direction: Int  // -1 going down, 0 not moving, 1 going up

  def target: Int
  
  def getStatus:(Int,Int,Int)
}
