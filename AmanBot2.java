package robotwarsummative;


import java.awt.Color;
import becker.robots.City;
import becker.robots.Direction;
/**
 * Robot Summative : Complex and enhanced algorithm bot
 * @author Aman Adhav
 * @version Date 2016-01-26
 */
public class AmanBot2 extends FighterRobot {

	//Variables
	private int health=100;
	private int starter = 0;
	private int maxmoves;
	private int movesmade = 0;
	private AmanBotOppData[] getval = null;
	private AmanBotOppData [] stalkplayer;	

	/**
	 * IlluminaBot Constructor
	 * @param arena = the environment where the battle is carried out
	 * @param a = starting avenue of the robot
	 * @param s = starting street of the robot
	 * @param direction = starting direction of the robot
	 * @param id = id of the robot
	 * @param attack = attack point of the robot
	 */
	public AmanBot2(City arena, int a, int s,Direction direction, int id, int attack) {

		super(arena,a,s,direction,id,5, 2, 3);
		this.setLabel();

	}

	
	public void setLabel(){
		
		this.setColor(Color.RED);
		this.setLabel(this.getID()+": "+Integer.toString(health));
		

		//Makes robot go black once health reaches zero
		if (this.health <= 0){
			this.setColor(Color.BLACK);
		}
	}

	/**
	 *  Makes Robot Go to a coordinate
	 * @param avenue = avenue that the robot will travel to
	 * @param street = street that the robot will travel to
	 */
	public void goToLocation(int avenue, int street){
		
		//Goes to the wanted avenue
		//Checks if the location desired is in east or west of the robot
		if (this.getAvenue() < avenue){
			
			//Makes robot face east
			while(this.isFacingEast() == false){
				this.turnLeft();
			}
			
			//Makes robot travel until it reaches an appropriate avenue
			while(this.getAvenue() != avenue){
				this.move();
			}
		}
		else if (this.getAvenue() > avenue){
			
			//Makes robot face west
			while(this.isFacingWest() == false){
				this.turnLeft();
			}
			
			//Makes robot travel until it reaches an appropriate avenue
			while(this.getAvenue() != avenue){
				this.move();
			}
			
		}
		
		//Goes to the desired street
		//Checks if the location desired is in south or north of the street
		if (this.getStreet() < street){
			
			//Makes robot face south
			while(this.isFacingSouth() == false){
				this.turnLeft();
			}
			
			//Makes robot travel until it reaches an appropriate street
			while(this.getStreet() != street){
				this.move();
			}
		}
		else if (this.getStreet() > street){
			
			//Makes robot face north
			while(this.isFacingNorth() == false){
				this.turnLeft();
			}
			
			//Makes robot travel until it reaches an appropriate street
			while(this.getStreet() != street){
				this.move();
			}
			
		}
			

	}

	/**
	 * TurnRequest, requests turns for the robot
	 * @param a = energy
	 * @param w = Opponent Data
	 */
	public TurnRequest takeTurn(int energy, OppData[] w){

		
		//Variables
		TurnRequest request;
		AmanBotOppData [] getval = oppDataPrepare(w);
		
		
		this.starter += 1;
						
		//Resets health and labeling
		this.health = w[this.getID()].getHealth();
		this.setLabel();

		//Passes in an extended oppdata to determine the kill
		int kill = this.kill(getval);
				
		//Gets the avenue and street of the kill robot
		int turnX = getval[kill].getAvenue();
		int turnY = getval[kill].getStreet();
		
		//Changes amount of steps based on energy
		if (energy > 15){
			this.maxmoves = 3;
		}
		else{
			this.maxmoves = energy/5;
		}
		
		//Makes robot go to the calculated allowed coordinate
		int moveX = huntX(turnX);
		int moveY = huntY(turnY);
		
		
		//Updates Number of the steps allowed
		this.maxmoves = this.getNumMoves();


		//Decided what to request based on it's position and energy
		if (w[kill].getStreet() == moveY && w[kill].getAvenue() == moveX && energy>=20){
			request = new TurnRequest(moveX, moveY, kill, energy/20);
		}
		else {
			request = new TurnRequest(moveX, moveY, -1, 0);
		}

		
		//Returns the request
		return request;

	}



	/**
	 * Prepares OppData Extension and makes it ready to be usable
	 * @param w = Regular OppData
	 * @return oppDataPrepare = Updated and Extended OppData
	 */
	private AmanBotOppData[] oppDataPrepare(OppData[] w) {
		
		//Checks if this is the first the oppdata gets expanded
		if(this.movesmade == 0)
		{

			getval = new AmanBotOppData[w.length];
			stalkplayer = new AmanBotOppData[w.length];
			
			//Transfers every value to the extended data
			for (int i=0; i< w.length ; i++){
				
				getval[i] = new AmanBotOppData(w[i].getID(), w[i].getAvenue(), w[i].getStreet(), w[i].getHealth());
				stalkplayer[i] = new AmanBotOppData(w[i].getID(), w[i].getAvenue(), w[i].getStreet(), w[i].getHealth());
			}
			
			this.movesmade += 1;
			
		}
		else
		{
			//Updates the extended opp data
			for (int i=0; i< getval.length ; i++)
			{	
				getval[i].setcountmoves(w[i].getAvenue(), w[i].getStreet());
			}
			
		}
		
		return getval;
	}

	/**
	 * Travels to a y location based on the maximum number of moves allowed
	 * @param turnY = Y location or street that the robot wants to move
	 * @return huntY = The actual street that the robot is allowed to move into
	 */
	private int huntY(int turnY) {
		
		int posY = this.getStreet();
		
		//Checks whether the kill location is at south or north
		if (posY > turnY){
			
			//Counts while the number of steps allowed is not breached
			while (turnY < posY && 0 < this.maxmoves){
				
				this.maxmoves -= 1;
				posY -= 1;
				
			}
			
		}
		else if (posY < turnY)
		{	
			//Counts while the number of steps allowed is not breached
			while (turnY > posY && 0 < this.maxmoves){
				
				this.maxmoves -= 1;
				posY += 1;
				
			}
			
		}
		return posY;
		
	}

	/**
	 * Calculates the maximum point that my robot is allowed to move in x direction
	 * @param turnX = avenue of the enemy
	 * @return posX = avenue that my robot is allowed to move to
	 */
	private int huntX(int turnX) {
		
		int posX = this.getAvenue();
		
		//Checks whether the kill location is at east or west
		if (posX > turnX){
			
			//Counts while the number of steps allowed is not breached
			while (turnX < posX && 0 < this.maxmoves){
				
				this.maxmoves -= 1;
				posX -= 1;
				
			}
			
		}
		else if (posX < turnX){
			
			//Counts while the number of steps allowed is not breached
			while (turnX > posX && 0 < this.maxmoves){
				
				this.maxmoves -= 1;
				posX += 1;
				
			}
			
		}
		return posX;
	}

	/**
	 * Calculates the best kill to attack on
	 * @param w = Extended OppData
	 * @return kill = The Id of the kill
	 */
	private int kill(AmanBotOppData[] w) {

		int fig = -10;
		
		OppData[] healthList = new AmanBotOppData[w.length];
		OppData[] distanceList = new AmanBotOppData[w.length];
		
		//Sets the stalkplayer file, if the program runs for the first time
		if (this.starter == 0){
			stalkplayer = new AmanBotOppData[w.length];
		}

		//Sets the movement list, inputs in values
		for (int i =0; i<w.length; i++){

			if(this.starter == 0){
				this.stalkplayer[i] = new AmanBotOppData(w[i].getID(),w[i].getAvenue(),w[i].getStreet(),w[i].getHealth());
			}

		}
		
		this.starter += 1;
		
		//Sorts the lists based on health, distance, movement amount
		healthList = this.sortHealth(w);
		distanceList = this.sortDistance(w);
		
		
		stalkplayer = this.sortMovement(w);
		
		
		
		//Finds the ideal kill based on health and distance
		fig = easyKill(healthList,distanceList,stalkplayer);

		return fig;
	}


	/**
	 * Given Health and Distance tries to calculate the best kill
	 * @param healthList = a list for the health of the opponent robots
	 * @param distanceList = a list for the distance of the opponent robots
	 * @param stalkplayer = a list for the amount of steps the opponent took ExtendedOppData
	 * @return int = The Id of the potential kill
	 */
	private int easyKill(OppData[] healthList, OppData[] distanceList, AmanBotOppData[] stalkplayer) {
		
		//Variables
		int average;
		int smallestAverage = -100;
		int fig = 0;

			//Counts from healthlist
		
			for(int w=0; w<stalkplayer.length; w++){
			
			for(int i=0; i<healthList.length; i++){

					//Counts from distance list
					for(int q=0; q<distanceList.length; q++){

						//Checks if the opponents match up
						if (healthList[i].getID() == distanceList[q].getID() && healthList[i].getID() == stalkplayer[w].getID()){

							//Ads the average
							average = i + q - w;

							//Finds the smallest average
							if (smallestAverage == -100 || average < smallestAverage){
								smallestAverage = i+q;
								fig = distanceList[q].getID();

							}

						}

					}

			}
			}


	return fig;
}

	/**
	 * Sorts the OppData based on the distance
	 * @param w = Opponent data that is unsorted
	 * @return oppData[] = sorted opponent data based on distance
	 */
	private OppData[] sortDistance(OppData[] w) {
		OppData [] distanceList = new OppData[w.length];
		
		//Duplicates the list
		for (int i =0; i<w.length; i++){
			distanceList[i] = w[i];
		}

		//Variables
		int closeX = 0;
		int closeY = 0;
		int fig = 0;
		int totalDistance = 1000;
		int enemyCounter = 0;
		
		//Counts the opponents
		for (int q=0; q<distanceList.length;q++){
			
			if (distanceList[q].getHealth() > 0){
				enemyCounter += 1;
			}
			
		}
		
		//Size^2 efficiency, Selection Sorting
		for (int q = 0; q<distanceList.length;q++){

			for (int i = q; i<distanceList.length;i++){

				//Checks if he targets himself or a dead person
				if (this.getID() != i && w[i].getHealth()>0){

					closeX = Math.abs(w[i].getAvenue()-this.getAvenue());
					closeY = Math.abs(w[i].getStreet()-this.getStreet());
					
					//Finds the smallest distance
					if(closeX+closeY<totalDistance && i>fig){
						totalDistance = closeX+closeY;
						fig = i;
					}

				}

			}
			
			//Places the smallest distance in the beginning of the street
			distanceList[q] = w[fig];
			//fig = -10;
			totalDistance = 1000;


		}
		
		return distanceList;
	}

	/**
	 * Sorts OppData based on the health
	 * @param w = unsorted opponent data based on health
	 * @return OppData[] = sorted opponent data based on health
	 */
	private OppData[] sortHealth(OppData[] w) {
		
		//Variables
		int fig = 0;
		int health = 101;
		int enemyCounter = 0;
		OppData [] healthList = new OppData[w.length];
		
		//Duplicates the health list
		for (int i =0; i<w.length; i++){
			healthList[i] = w[i];
		}

		//Counts the enemies
		for (int q=0; q<healthList.length;q++){
			
			if (healthList[q].getHealth() > 0){
				enemyCounter += 1;
			}
			
		}
		
		//Selections sort, size^2 efficiency
		for (int q = 0; q<healthList.length;q++){

			for (int i = q; i<healthList.length;i++){

				//Checks if it attacks himself or a dead robot
				if (this.getID() != i && w[i].getHealth()>0){

					//Finds the smallest health
					if(w[i].getHealth()<health && i>fig){
						health = w[i].getHealth();
						fig = i;
					}

				}

			}
			
			//Places the smallest value to the beginning of the file
			healthList[q] = w[fig];
			health = 101;


		}
		
		return healthList;
	}
	
	/**
	 * Sorts the Opponent data based on the extended information from IlluminaOppData
	 * @param w = Unsorted opponent data
	 * @return IlluminaOppData = sorted movement based on the movement amount
	 */
	private AmanBotOppData[] sortMovement(AmanBotOppData[] w) {

		//Variables
		int fig = 0;
		int mov = 999999; // max movement
		

		//Selection sort
		for (int q = 0; q<this.stalkplayer.length;q++){

			for (int i = q; i<this.stalkplayer.length;i++){

				if (this.getID() != i && w[i].getHealth()>0){


					//Chooses the smallest value
					if(w[i].getcountmoves() < mov && i>fig || i==0){
						mov = w[i].getcountmoves();
						fig = i;
					}

				}

			}
			
			//Adds it to the beginning of the list
			this.stalkplayer[q] = w[fig];
			mov = 999;
		}
		
		return stalkplayer;
	}

	/**
	 * Receives Battle Results
	 * @param a = Health Lost
	 * @param b = Opponent Id
	 * @param c = Opponent Health Lost
	 */
	public void battleResult(int a,int b,int c){
		
		//Change health status
		this.health = this.health-a;
		
		this.setLabel();

	}

}