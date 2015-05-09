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
		c.maintenanceModeOff()
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
	/*
	* This object answers questions about the state of the elevator.
	*  It is also intended to interact with SystemStatus heavily,
	*   so that the controller may worry primarily with
	*   behavior.
	*/
	var direction = "stopped"
	var resetMode = false
	var maintenance = false
	var alarm = false
	def stopped() = SystemStatus.elevatorStopButtonLit
	def changeDoor(state:String) {
		val floor = getFloor
		val open = state == "open"
		floor match {
			case 1 =>  SystemStatus.door1Open = open
			case 2 =>  SystemStatus.door2Open = open
			case 3 =>  SystemStatus.door3Open = open
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
		if (getFloor > 1) direction = "down"
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

		changeDoor("closed")
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
			case "stop"			  => SystemStatus.elevatorStopButtonLit = on
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
				(direction == "down" && (SystemStatus.floor2UpButtonLit && !(SystemStatus.floor1UpButtonLit || lightOn("elev1")))),
				(direction == "up" && (SystemStatus.floor2DownButtonLit && !(SystemStatus.floor3DownButtonLit|| lightOn("elev3") ))  ) ).foldLeft(false)(_||_)
				}
		def lightOn(buttonName:String)= buttonName match{
				case "floor1"     => SystemStatus.floor1UpButtonLit
				case "floor2up"   => SystemStatus.floor2UpButtonLit
				case "floor2down" => SystemStatus.floor2DownButtonLit
				case "floor3"	  => SystemStatus.floor3DownButtonLit
				case "elev1"      => SystemStatus.elevator1ButtonLit
				case "elev2"	  => SystemStatus.elevator2ButtonLit
				case "elev3"	  => SystemStatus.elevator3ButtonLit
				case "upArrow"    => SystemStatus.UpArrowOn
				case "downArrow"  => SystemStatus.DownArrowOn
				case "stop"			  => SystemStatus.elevatorStopButtonLit

			}
}

class Controller {

	changeDoor("open")
	def ArrivedAt(floor:Int) {
		//logic to handle arrival

		floor match {
			case 1 => arriveFloor1
			case 2 => arriveFloor2
			case 3 => arriveFloor3
			}
		}
	def ButtonPress(buttonName:String) {
		if (elevatorStatus.maintenance || elevatorStatus.alarmActive) {
		 	println("Detected that maintentance/alarm button is pressed")
			}
		else {
			changeLight(buttonName,true)
			direction match {
			case "stopped" => {
				val floor = getFloor
				if ( (List("floor1", "elev1") contains buttonName) && (floor != 1) ) {
					floor1Call
					}

				else if ((List("floor3", "elev3") contains buttonName) && (floor != 3)) {
					floor3Call
				}

				else if ((List("floor2up","floor2down", "elev2") contains buttonName) && (floor !=2))
				{
					floor2Call
				}
				//Invalid button press.  Elevator is at requested floor
				else changeLight(buttonName, false)
			}
			case _ => {"Doing nothing"} //Do nothing
			}
		}
		}

	def arriveFloor1(){
	    Motor.stop
			changeLight("floor1", false)
			changeLight("elev1", false)
			changeDoor("open")
			if (elevatorStatus.resetMode) {
			     elevatorStatus.alarmComplete
			     }
			else {
				if (continue()) {
					elevatorMove("up")
				}
				else elevatorStatus.changeDir("stopped")
			}

    }
	def arriveFloor2(){

		if (stopAt2){
			println("Stopped at 2") //for debugging
			Motor.stop
			direction match {
			//TODO Change up and down arrow lights
				case "up" => changeLight("floor2up",false)
				case "down" => changeLight("floor2down",false)
				}
			changeLight("elev2", false)
			changeDoor("open")
			if (floor2continue) {
				direction match {
					case "up"   =>elevatorMove("up")
					case "down" =>elevatorMove("down")
				}
			}
			else if (elevatorStatus.maintenance) {
				floor1Call
			}
			else elevatorStatus.changeDir("stopped")
		}
		else if (elevatorStatus.lightOn("elev1") || elevatorStatus.lightOn("elev3")) {
		     //This means that there is a valid request in the opposite direction

		     direction match {
		           case "up"   => {
		                elevatorMove("down")
		                changeLight("floor2down",false)
		                }
		           case "down" => {
		                elevatorMove("up")
		                changeLight("floor2up",false)
		           }
		     }
		     }
		else elevatorStatus.changeDir("stopped")
		}

	def arriveFloor3(){
		changeLight("floor3", false)
		changeLight("elev3", false)
		if (continue()) {
			changeDoor("close")
			elevatorMove("down")
		}
		else {
			Motor.stop
			elevatorStatus.changeDir("stopped")
			changeDoor("open")
		}
	}

	private def elevatorMove(dir:String) ={
		val floor = getFloor
		if (floor != -1) {
		dir match {
			case "stopped" =>
			case "up"			 => {
				if (floor != 3) {
					changeDoor("close")
					Motor.up
					elevatorStatus.changeDir(dir)
				}
				else println ("Can't go up on floor 3")
			}
			case "down" =>{
				if (floor != 1) {
					changeDoor("close")
					Motor.down
					elevatorStatus.changeDir(dir)
				}
				else println("Can't go down on floor 1")
			}
			}
		}
		else println("Elevator is in motion. Can't set fresh direction.")
	}
	def maintenanceModeOn() = {
		elevatorStatus.maintenanceModeOn
		if (getFloor >1 && direction == "stopped") elevatorMove("down")
	}
	def maintenanceModeOff() = elevatorStatus.maintenanceModeOff

	def alarmModeOn() = {
		Motor.stop
		elevatorStatus.alarmModeOn
		alarmDirection()

	}
	def alarmModeOff() = {
		elevatorStatus.alarmModeOff
		if (getFloor != 1) Motor.down
		else {
			//if we're turning off the alarm and it's already on floor 1,
			//turn off the alarm immediately
		        elevatorStatus.alarmComplete
		        changeDoor("open")
		        changeLight("elev1",false)
		        }
	}

	def closestFloor():String = elevatorStatus.closestFloor

	def stopButton() = {
		if (elevatorStatus.stopped){
		 direction match {
			case "up"      => Motor.up
			case "down"    => Motor.down
			case "stopped" => Motor.stop
			}
		changeLight("stop", false)
		}
		else{
			Motor.stop
			changeLight("stop",true)
		}
		}

	private def alarmDirection() = {
		val closest = closestFloor()
		val current = Motor.lineOut
		if (isOnFloor){
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
	private def isOnFloor():Boolean = (getFloor != -1)

	private def floor1Call() {
		elevatorMove("down")
		if(getFloor == 2) changeLight("floor2down",false)
		else changeLight("floor3",false)
		}

	private def floor2Call() {
		if (getFloor==3){
			elevatorMove("down")
			changeLight("floor3",false)
			}
		else {
			elevatorMove("up")
			changeLight("floor1",false)
			}
		}

	private def floor3Call() {
		elevatorMove("up")
		if(getFloor == 2) changeLight("floor2up",false)
		else changeLight("floor1",false)
		}
	//Hooks for controller to ask for status
	private def direction() = elevatorStatus.direction
	private def resetMode() = elevatorStatus.resetMode
	private def alarm() = elevatorStatus.alarm
	private def continue() = elevatorStatus.continue
	private def changeLight(light:String,on:Boolean) = elevatorStatus.changeLight(light, on)
	private def getFloor():Int = elevatorStatus.getFloor
	private def changeDoor(state:String) = elevatorStatus.changeDoor(state)
	private def floor2continue():Boolean = elevatorStatus.floor2continue
	private def stopAt2():Boolean = elevatorStatus.stopAt2

}
