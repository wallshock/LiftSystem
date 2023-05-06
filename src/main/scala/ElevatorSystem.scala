trait ElevatorSystem {
  def addElevator(elevator: Elevator): Unit
  def pickup(floor: Int, direction: Direction): Unit
  def update(elevatorId: Int, floor: Int, direction: Direction, targets: Seq[Int]): Unit
  def step(): Unit
  def status(): Seq[(Int,Int,Int)]
}