object guiOutput {

	def Floor1Up()
	{
		//Place your code here for when the up button is pressed on floor 1.
		println("Floor 1 Up Button Pressed")
	}
	
	def Floor2Up()
	{
		//Place your code here for when the up button is pressed on floor 2.
		println("Floor 2 Up Button Pressed")
	}

	def Floor2Down()
	{
		//Place your code here for when the down button is pressed on floor2.
		println("Floor 2 Down Button Pressed")
	}

	def Floor3Down()
	{
		//Place your code here for when the down button is pressed on floor3.
		println("Floor 3 Down Button Pressed")
	}

	def elevFloor1()
	{
		//Place your code here for when the 1 button is pressed in the elevator.
		println("Elevator Button 1 Pressed")
	}

	def elevFloor2()
	{
		//Place your code here for when the 2 button is pressed in the elevator
		println("Elevator Button 2 Pressed")
	}

	def elevFloor3()
	{
		//Place your code here for when the 3 button is pressed in the elevator
		println("Elevator Button 3 Pressed")
	}

	def elevStop()
	{
		//Place your code here for when the stop button is pressed in the elevator
		println("Elevator Stop Button Pressed")
	}

	def MaintenanceModeOn()
	{
		//Place your code here for when the maintanence mode is switched to on.
		println("Maintenance Mode On")
	}
	def MaintenanceModeOff()
	{
		//Place your code here for when the maintanence mode is switched to off.
		println("Maintenance Mode Off")
	}

	def AlarmModeOn()
	{
		//Place your code here for when the alarm mode is switched to on.
		println("Alarm On")
	}

	def AlarmModeOff()
	{
		//Place your code here for when the alarm mode is switched to off.
		println("Alarm Off")
	}
	
	def ArrivedFloor1()
	{
		//Place your code here for when the elevator arrived to floor 1.
		println("Floor 1")
	}
	
	def ArrivedFloor2()
	{
		//Place your code here for when the elevator arrived to floor 2.
		println("Floor 2")
	}
	
	def ArrivedFloor3()
	{
		//Place your code here for when the elevator arrived to floor 3.
		println("Floor 3")
	}

}

class Priorities() {
	private var downward = List()
	private var upward = List()
	
	def put(floor:Int, dir:String) = match {
		case "down" => {floor::downward
				downward = downward.sortWith(_ > _ ) }
		case "up"   => {floor::upward
				upward = upward.sortWith(_ < _) }
				}
				
	/* Removes all copies of a floor from both upward and downward */			
	def clean ( floor:Int ) = {
		downward = downward diff List(floor)
		upward	 = upward diff List(floor)
		}
	def pop(dir:String ) = dir match {
	//Removes the first item from both the upward and downward queues
	//Returns -1 if there are no more items in the given direction 
		case "down"  => downward match { 
					case Nil => -1
					case h::t => {
						downward = t
						h }
						}
		case "up"    => upward match {
					case Nil => -1
					case h::t => {
						upward = t
						h }
					      }
					}
	def peek(dir:String) = dir match {
		case "down" => downward match { 
					case Nil => -1
					case h::t => h
					}
		case "up"   => upward match { 
					case Nil => -1
					case h::t => h
					}
		}
}


