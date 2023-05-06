package Traits

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait ElevatorSystem {
  def floors:Int
  def elevators:ArrayBuffer[Elevator]
  def goUpRequest:Array[Boolean]

  def goDownRequest:Array[Boolean]
  def addElevator(elevator: Elevator): Unit

  def pickup(floor: Int, direction: Int): Unit
  def update(elevatorId: Int, floor: Int, direction: Int): Unit

  def step(): Unit
  def addElevatorDestination(id:Int,destination:Int): Unit

  def status: Array[(Int, Int, Int)]
}
