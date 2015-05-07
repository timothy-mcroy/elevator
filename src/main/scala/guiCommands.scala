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

class Controller {
	var direction = "stopped"
	var stopFloor = 0
	
	def ArrivedAt(floor:Int) {
	//logic to handle arrivale
	
	}
	def ButtonPress(buttonName:String) = {
	if SystemStatus.maintenance {  }
	buttonName match = {
		case "floor1"     =>
		case "floor2up"   =>
		case "floor2down" =>
		case "floor3"	  =>
		
		}
	}
	def getFloor():Int 
	
	
}
