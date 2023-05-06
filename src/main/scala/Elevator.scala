trait Elevator {
  def id: Int
  def currentFloor: Int
  def direction: Direction
  def targets: Seq[Int]
  def addTarget(targetFloor: Int): Unit
}