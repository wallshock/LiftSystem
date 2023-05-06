package Traits

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait ElevatorSystem {
  
  def elevators:ArrayBuffer[Elevator]
  def goUpRequest:ArrayBuffer[Int]

  def goDownRequest:ArrayBuffer[Int]
  def addElevator(elevator: Elevator): Unit

  def pickup(floor: Int, direction: Int): Unit

  def update(elevatorId: Int, floor: Int, direction: Int): Unit

  def step(): Unit

  def status: Array[(Int, Int, Int)]
}
