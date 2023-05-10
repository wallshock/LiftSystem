package Traits

trait GuiObserver {
  
  def updateGuiBefore(): Unit
  def updateGuiAfter(): Unit
}
