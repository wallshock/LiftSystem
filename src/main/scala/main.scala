@main
def main(): Unit = {
  val system = ElevatorSystemImpl()
  for (i <- 0 to 10)
    system.addElevator(ElevatorImpl(i,0,0,0))
    val size = system.elevators.length

}