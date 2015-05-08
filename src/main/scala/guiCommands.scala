object guiOutput {
	val c = new Controller
	def Floor1Up()
	{
		//Place your code here for when the up button is pressed on floor 1.
		c.ButtonPress("floor1")
		println("Floor 1 Up Button Pressed")
	}

	def Floor2Up()
	{
		//Place your code here for when the up button is pressed on floor 2.
		c.ButtonPress("floor2up")
		println("Floor 2 Up Button Pressed")
	}

	def Floor2Down()
	{
		//Place your code here for when the down button is pressed on floor2.
		c.ButtonPress("floor2down")
		println("Floor 2 Down Button Pressed")
	}

	def Floor3Down()
	{
		//Place your code here for when the down button is pressed on floor3.
		c.ButtonPress("floor3")
		println("Floor 3 Down Button Pressed")
	}

	def elevFloor1()
	{
		//Place your code here for when the 1 button is pressed in the elevator.
		c.ButtonPress("elev1")
		println("Elevator Button 1 Pressed")
	}

	def elevFloor2()
	{
		//Place your code here for when the 2 button is pressed in the elevator
		c.ButtonPress("elev2")
		println("Elevator Button 2 Pressed")
	}

	def elevFloor3()
	{
		//Place your code here for when the 3 button is pressed in the elevator
		c.ButtonPress("elev3")
		println("Elevator Button 3 Pressed")
	}

	def elevStop()
	{
		//Place your code here for when the stop button is pressed in the elevator

		println("Elevator Stop Button Pressed")
		c.stopButton()
	}

	def MaintenanceModeOn()
	{
		//Place your code here for when the maintanence mode is switched to on.
		c.maintenanceModeOn()
		println("Maintenance Mode On")
	}
	def MaintenanceModeOff()
	{
		//Place your code here for when the maintanence mode is switched to off.
		println("Maintenance Mode Off")
		c.maintenance = false 	//good candidate for rearranging
	}

	def AlarmModeOn()
	{
		//Place your code here for when the alarm mode is switched to on.
		c.alarmModeOn()
		println("Alarm On")
	}

	def AlarmModeOff()
	{
		//Place your code here for when the alarm mode is switched to off.
		println("Alarm Off")
		c.alarmModeOff()
	}

	def ArrivedFloor1()
	{
		//Place your code here for when the elevator arrived to floor 1.
		c.ArrivedAt(1)
		println("Floor 1")
	}

	def ArrivedFloor2()
	{
		//Place your code here for when the elevator arrived to floor 2.
		c.ArrivedAt(2)
		println("Floor 2")
	}

	def ArrivedFloor3()
	{
		//Place your code here for when the elevator arrived to floor 3.
		c.ArrivedAt(3)
		println("Floor 3")
	}

}

object elevatorStatus {
	var direction = "stopped"
	var resetMode = false
	var stopVal = false
	var maintenance = false
	var alarm = false
	def changeDoor(floor:Int) {
		floor match {
			case 1 =>  SystemStatus.door1Open = ! SystemStatus.door1Open
			case 2 =>  SystemStatus.door2Open = ! SystemStatus.door2Open
			case 3 =>  SystemStatus.door3Open = ! SystemStatus.door3Open
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
	def maintenanceModeOn() = {
		maintenance = true	
		changeLight("floor1", true)
	}
	def maintenanceModeOff() = {
	maintenance = false
	}		
	def alarmModeOn() = {
	
			for(x <- List("floor2up","floor2down","floor1","floor3","elev1","elev2","elev3")) {changeLight(x, false)}
			alarm = true
			changeLight(closestFloor(),true)
		}
	def alarmModeOff() = {
		resetMode = true
		
		changeDoor(getFloor)
		//Motor.down
		//elevatorStatus.changeDir("down")
	}
	def alarmComplete(){
	        alarm = false
	        resetMode = false
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
	def closestFloor():String = {
		val line = Motor.lineOut()
		if ( line <= 10) "elev3"
		else if (line <= 27) "elev2"
		else "elev1"
	}
	def continue():Boolean = {
		List(SystemStatus.elevator1ButtonLit,
				SystemStatus.elevator2ButtonLit,
				SystemStatus.elevator3ButtonLit,
				SystemStatus.floor1UpButtonLit,
				SystemStatus.floor2UpButtonLit,
				SystemStatus.floor3DownButtonLit,
				SystemStatus.floor2DownButtonLit).foldLeft(false)(_||_)
		}
        def changeDir(dir:String) = dir match {
            case "up" => direction = "up"
            case "down" => direction = "down"
            case "stopped" => direction = "stopped"
        }
        def alarmActive() = alarm || resetMode		
        def stopResume() = stopVal = true
}

class Controller {
	def direction() = elevatorStatus.direction
	def resetMode() = elevatorStatus.resetMode
	//def stopVal = elevatorStatus.stopVal
	var maintenance = false
	def alarm() = elevatorStatus.alarm
	elevatorStatus.changeDoor(1)
	def continue() = elevatorStatus.continue()
	def changeLight(light:String,on:Boolean) = elevatorStatus.changeLight(light,on)
	def ArrivedAt(floor:Int) {
		//logic to handle arrival

		floor match {
			case 1 => arriveFloor1
			case 2 => arriveFloor2
			case 3 => arriveFloor3
			}
		}
	def ButtonPress(buttonName:String) {
		if (maintenance || elevatorStatus.alarmActive) {
		 	println("Detected that maintentance/alarm button is pressed")
			
			
			}
else
{
		//println("In ButtonPress, direction is = " + direction)
		changeLight(buttonName,true)
		direction match {
			case "stopped" => {
			//presumes doors are open
				
				val floor = getFloor
				//println("go to floor 3 " + ( (List("floor3", "elev3") contains buttonName) && (floor != 3) ) )
				if ( (List("floor1", "elev1") contains buttonName) && (floor != 1) ) {
					elevatorStatus.changeDoor(floor)
					Motor.down
					elevatorStatus.changeDir("down")
					if(floor == 2) changeLight("floor2down",false)
					else changeLight("floor3",false)
					
					}

				else if ((List("floor3", "elev3") contains buttonName) && (floor != 3)) {
					elevatorStatus.changeDoor(floor)
					Motor.up
					elevatorStatus.changeDir("up")
					if(floor == 2) changeLight("floor2up",false)
					else changeLight("floor1",false)
				}
				else if ((List("floor2up","floor2down", "elev2") contains buttonName) && (floor !=2))
				{
					elevatorStatus.changeDoor(floor)
					if (floor==3){
						Motor.down
						elevatorStatus.changeDir("down")
						changeLight("floor3",false)
					}
					else {
						Motor.up
						elevatorStatus.changeDir("up")
						changeLight("floor1",false)
						}
				}
					//Invalid button press.  Elevator is at requested floor
					else changeLight(buttonName, false)


			}
			case _ => {	} //Do nothing
			}
		}
		}
	def arriveFloor1(){
	        Motor.stop
	        SystemStatus.floor1UpButtonLit  = false
		SystemStatus.elevator1ButtonLit = false
		elevatorStatus.changeDoor(1)
		if (elevatorStatus.resetMode) {
		     elevatorStatus.alarmComplete
		     }
		else {
		
		
		
		if (continue()) {
			elevatorStatus.changeDoor(1) 
			Motor.up
			elevatorStatus.changeDir("up")
			}
		else elevatorStatus.changeDir("stopped")
		}
             }
	def arriveFloor2(){

		if (stopAt2){
			println("Stopped at 2") //for debugging
			Motor.stop
			println(direction)
			direction match {
			//TODO Change up and down arrow lights
				case "up" => changeLight("floor2up",false)
				case "down" => changeLight("floor2down",false)
				}
			changeLight("elev2", false)
			elevatorStatus.changeDoor(2)
			if (floor2continue) {
				elevatorStatus.changeDoor(2)
				direction match {
					case "up"   =>{
					Motor.up
					//changeLight("floor2up",false)
						}
					case "down" => {
					Motor.down
					//changeLight("floor2down",false)
					}			
				}
			}
			else if (SystemStatus.elevator1ButtonLit || SystemStatus.elevator3ButtonLit) {
			     //This means that there is a valid request in the opposite direction
			     elevatorStatus.changeDoor(2)
			     println("this is where it's breaking")
			     
			     direction match {
			           case "up"   => {
			                elevatorStatus.changeDir("down")
			                Motor.down
			                changeLight("floor2down",false)
			                
			           
			                }
			           case "down" => {
			                elevatorStatus.changeDir("up")
			                Motor.up
			                changeLight("floor2up",false)			           
			           }
			     }
			     }
			else elevatorStatus.changeDir("stopped")
		}


		}
        
	def arriveFloor3(){
		Motor.stop
		SystemStatus.floor3DownButtonLit  = false
		SystemStatus.elevator3ButtonLit = false
		elevatorStatus.changeDoor(3)
		println(continue())
		if (continue()) {
			println("Going down!")
			elevatorStatus.changeDoor(3)
			Motor.down
			elevatorStatus.changeDir("down")
		}
		else elevatorStatus.changeDir("stopped")

		}

	def maintenanceModeOn() = {
		maintenance = true	
		changeLight("floor1", true)
	}
	
	def maintenanceModeOff() = {
		maintenance = false
	}

	def alarmModeOn() = {
	Motor.stop	
	elevatorStatus.alarmModeOn
	alarmDirection()
		
	}

	def alarmModeOff() = { 
	elevatorStatus.alarmModeOff
	if (getFloor != 1) Motor.down
	else {  //if we're turning off the alarm and it's already on floor 1, turn off the alarm immediately
	        elevatorStatus.alarmComplete
	        elevatorStatus.changeDoor(1)
	        changeLight("elev1",false)
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
			case -1=> { }
			}
		}
	/*def continue():Boolean = {
		List(SystemStatus.elevator1ButtonLit,
				SystemStatus.elevator2ButtonLit,
				SystemStatus.elevator3ButtonLit,
				SystemStatus.floor1UpButtonLit,
				SystemStatus.floor2UpButtonLit,
				SystemStatus.floor3DownButtonLit,
				SystemStatus.floor2DownButtonLit).foldLeft(false)(_||_)
		}*/
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
			(direction == "down" && SystemStatus.floor2DownButtonLit),
			(direction == "down" && (SystemStatus.floor2UpButtonLit && !SystemStatus.floor1UpButtonLit)),
			(direction == "up" && (SystemStatus.floor2DownButtonLit && !SystemStatus.floor3DownButtonLit)  ) ).foldLeft(false)(_||_)
			}
	/*def changeLight(buttonName:String,on:Boolean){
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
		}*/
	def isOnFloor():Boolean = (getFloor() != -1)
	
	def closestFloor():String = {
		val line = Motor.lineOut()
		if ( line <= 10) "elev3"
		else if (line <= 27) "elev2"
		else "elev1"
	}

	def stopButton() = {
		if (elevatorStatus.stopVal){
		 direction match {
			case "up"      => Motor.up
			case "down"    => Motor.down
			case "stopped" => Motor.stop
			}
		elevatorStatus.stopVal = false
		}
		else{
		Motor.stop
		elevatorStatus.stopVal = true
		}
		}	
	def alarmDirection() = {
		val closest = closestFloor()
		val current = Motor.lineOut
		if (elevatorStatus.getFloor != -1){
		println("onFloors, alarm conditional branch")
		Motor.stop
		elevatorStatus.changeDir("stopped")
		 }
		else{
		closest match {
			case "elev1" => Motor.down
			case "elev2" => if (( 10 < current) && (current < 20)) Motor.down else Motor.up
			case "elev3" => Motor.up 
		}	
       }		

}
}
