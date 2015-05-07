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
		
		c.buttonPressed
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
	
	floor match = {
		case 1 => {
		motor.stop
		SystemStatus.floor1UpButtonLit  = false
		SystemStatus.elevator1ButtonLit = false
		changeDoor(floor)
		if continue() {
			changeDoor(floor)
			motor.up
			direction = "up"
			}
		else direction = "stopped"
		
		} 
		case 2 =>{
		
			if (stopAt2){
				motor.stop
				direction match = {
				//TODO Change up and down arrow lights
					case "up" => floor2UpButton = false
					case "down" => floor2DownButton = false
					}
				SystemStatus.elevator2ButtonLit = false
				changeDoor(floor)
				if floor2continue {
					changeDoor(floor)
					direction match = {
						case "up"   => motor.up
						case "down" => motor.down
					}
				else {
				direction = "stopped"
				}
			}
			}	
			
		}
		case 3 => {
			motor.stop
			SystemStatus.floor3UpButtonLit  = false
			SystemStatus.elevator3ButtonLit = false
			changeDoor(floor)
			if continue() {
				changeDoor(floor)
				motor.down
				direction = "down"
			}
			else direction = "stopped"
		
		}
		}
	}
	def ButtonPress(buttonName:String) = {
	if SystemStatus.maintenance {  }
	direction match = {
		case "stopped" => {
		//presumes doors are open
			val floor = getFloor
			if (("floor1", "elev1") contains buttonName && floor != 1) {
				changeDoor(floor)
				motor.down
				
				}
				
			else if (("floor3", "elev3") contains buttonName && floor != 3) {
				changeDoor(floor)
				motor.up
			}
			else if (("floor2up", "elev2") contains buttonName)
			{
				changeDoor(floor)
				if (floor==3) motor.down
				else motor.up
			
			}
			//Invalid button press.  Elevator is at requested floor
			else changeLight(buttonName)
				 
		
		}
		case _ => {]		
		
		}
		
		
		
		
		
		
		
		
		case _  => 
	
	buttonName match = {
		case "floor1"     =>
		case "floor2up"   =>
		case "floor2down" =>
		case "floor3"	  =>
		case "elev1"      =>
		case "elev2"	  =>
		case "elev3"	  =>
		
		}
	}
	def getFloor():Int = {
		motor.lineOut match = {
			case 36 => 1
			case 20 => 2
			case 2  => 3
			}
	}
	def isOnFloor()
	def changeDoor(floor:Int) {
		assert isOnFloor()
		floor match = {
			case 1 => door1Open = !door1Open
			case 2 => door2Open = !door2Open
			case 3 => door3Open = !door3Open
			}
	}
	def continue():Boolean {
		List(SystemStatus.elevator1ButtonLit,
		SystemStatus.elevator2ButtonLit,
		SystemStatus.elevator3ButtonLit,
		SystemStatus.floor1UpButtonLit,
		SystemStatus.floor2UpButtonLit,
		SystemStatus.floor3UpButtonLit,
		SystemStatus.floor2DownButtonLit) exists (true)
	}
	def floor2continue(){
	direction match = {
		case "up"   => {
			(SystemStatus.elevator3ButtonLit ||
			SystemStatus.floor3UpButtonLit)
			}
		case "down" => {
			(SystemStatus.elevator1ButtonLit ||
			SystemStatus.floor1UpButtonLit)
		}
	
	}
	def stopAt2(){
		SystemStatus.elevator2ButtonLit || 
			(direction == "up" && floor2UpButton) ||
			(direction == "down" && floor2DownButton)
			}
	def changeLight(buttonName:String,on:Boolean){
	buttonName match = {
		case "floor1"     => SystemStatus.floor1UpButtonLit = on
		case "floor2up"   => SystemStatus.floor2UpButtonLit = on
		case "floor2down" => SystemStatus.floor2DownButtonLit = on
		case "floor3"	  => SystemStatus.floor3DownButtonLit = on
		case "elev1"      => SystemStatus.elevator1ButtonLit  = on
		case "elev2"	  => SystemStatus.elevator2ButtonLit  = on
		case "elev3"	  => SystemStatus.elevator3ButtonLit  = on
		case "upArrow"    => SystemStatus.UpArrowOn = on
		case "downArrow"  => SystemStatus.DownArrowOn = on
		}
	
	
	
}


