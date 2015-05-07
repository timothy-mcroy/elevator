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
	val maintenance = false
	def ArrivedAt(floor:Int) {
	//logic to handle arrivale

	floor match {
		case 1 => {
		Motor.stop
		SystemStatus.floor1UpButtonLit  = false
		SystemStatus.elevator1ButtonLit = false
		changeDoor(floor)
		if (continue()) {
			changeDoor(floor)
			Motor.up
			direction = "up"
			}
		else direction = "stopped"

		}
		case 2 =>{

			if (stopAt2){
				Motor.stop
				direction match {
				//TODO Change up and down arrow lights
					case "up" => SystemStatus.floor2UpButtonLit = false
					case "down" => SystemStatus.floor2DownButtonLit = false
					}
				SystemStatus.elevator2ButtonLit = false
				changeDoor(floor)
				if (floor2continue) {
					changeDoor(floor)
					direction match {
						case "up"   => Motor.up
						case "down" => Motor.down
					}
				}
			}
				else direction = "stopped"

			}

		case 3 => {
			Motor.stop
			SystemStatus.floor3DownButtonLit  = false
			SystemStatus.elevator3ButtonLit = false
			changeDoor(floor)
			if (continue()) {
				changeDoor(floor)
				Motor.down
				direction = "down"
			}
			else direction = "stopped"

		}
		}
	}
	def ButtonPress(buttonName:String) {
	if (maintenance) {
	 	println("Detected that maintentance button is pressed")
		}
	direction match {
		case "stopped" => {
		//presumes doors are open
			val floor = getFloor
			if ( (List("floor1", "elev1") contains buttonName) && (floor != 1) ) {
				changeDoor(floor)
				Motor.down

				}

			else if ((List("floor3", "elev3") contains buttonName) && (floor != 3)) {
				changeDoor(floor)
				Motor.up
			}
			else if (List("floor2up", "elev2") contains buttonName)
			{
				changeDoor(floor)
				if (floor==3) Motor.down
				else Motor.up

			}
			//Invalid button press.  Elevator is at requested floor
			else changeLight(buttonName, false)


		}
		case _ => {	} //Do nothing
	}
}

	def getFloor():Int = {
		Motor.lineOut match {
			case 36 => 1
			case 20 => 2
			case 2  => 3
			case _  => -1  //not on a floor
			}
	}
	//def isOnFloor()
	def changeDoor(floor:Int) {
		assert (isOnFloor(), "You can't open the door!")
		floor match {
			case 1 =>  SystemStatus.door1Open = ! SystemStatus.door1Open
			case 2 =>  SystemStatus.door2Open = ! SystemStatus.door2Open
			case 3 =>  SystemStatus.door3Open = ! SystemStatus.door3Open
			}
	}
	def continue():Boolean = {
		List(SystemStatus.elevator1ButtonLit,
				SystemStatus.elevator2ButtonLit,
				SystemStatus.elevator3ButtonLit,
				SystemStatus.floor1UpButtonLit,
				SystemStatus.floor2UpButtonLit,
				SystemStatus.floor3DownButtonLit,
				SystemStatus.floor2DownButtonLit).foldLeft(true)(_||_)
	}
	def floor2continue():Boolean = direction match {
		case "up"   => {
			(SystemStatus.elevator3ButtonLit ||
			SystemStatus.floor3DownButtonLit)
			}
		case "down" => {
			(SystemStatus.elevator1ButtonLit ||
			SystemStatus.floor1UpButtonLit)
			}
		}

	def stopAt2():Boolean = {
		List(SystemStatus.elevator2ButtonLit,
			(direction == "up" && SystemStatus.floor2UpButtonLit),
			(direction == "down" && SystemStatus.floor2DownButtonLit)).foldLeft(true)(_||_)
			}
	def changeLight(buttonName:String,on:Boolean){
	buttonName match {
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
	def isOnFloor():Boolean = (getFloor() == -1)
}
