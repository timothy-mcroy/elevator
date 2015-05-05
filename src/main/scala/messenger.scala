import akka.actor._

import guiGlobals._
class messenger() extends Actor
{

	def receive = {
		case "elevator1Button" => guiOutput.elevFloor1;

		case "elevator2Button" => guiOutput.elevFloor2;

		case "elevator3Button" => guiOutput.elevFloor3;

		case "elevatorStopButton" => guiOutput.elevStop;

		case "floor1UpButton" => guiOutput.Floor1Up;

		case "floor2UpButton" => guiOutput.Floor2Up;

		case "floor2DownButton" => guiOutput.Floor2Down;

		case "floor3DownButton" => guiOutput.Floor3Down;

		case "maintenanceModeOn" => guiOutput.MaintenanceModeOn;

		case "maintenanceModeOff" => guiOutput.MaintenanceModeOff;

		case "alarmModeOn" => guiOutput.AlarmModeOn;

		case "alarmModeOff" =>	guiOutput.AlarmModeOff;
		
		case "arrivedFloor1" => guiOutput.ArrivedFloor1;
		
		case "arrivedFloor2" => guiOutput.ArrivedFloor2;
		
		case "arrivedFloor3" => guiOutput.ArrivedFloor3;
	}

}
