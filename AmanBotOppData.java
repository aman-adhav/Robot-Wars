package robotwarsummative;
/**
 * Robot Summative : Extended OppData
 * @author Aman Adhav
 * @version Date 2016-01-26
 */
public class AmanBotOppData extends OppData {

	
	private int counter = 0;
	private int lastY;
	private int lastX;
	private int countmoves = 0 ;
	
	/**
	 * Constructor
	 * @param id = Id of the robot
	 * @param a = avenue of the robot
	 * @param s = street of the robot
	 * @param health = health of the robot
	 */
	public AmanBotOppData(int id, int a, int s, int health) {
		super(id, a, s, health);
		lastY = a;
		lastX = s;
	}
	
	/**
	 * Changes the amount of steps the robot took
	 * @param avenue = Opponent Robot's current avenue
	 * @param street = Opponent Robot's current street
	 */
	public void setcountmoves(int avenue, int street){
		
		if(this.counter == 0){
			//Sets up the old avenues for the first time
			this.lastY = avenue;
			this.lastX = street;
			this.counter += 1;
			
		}
		else{
			//Adds in the number of steps taken by the opponent robot
			this.countmoves += Math.abs(this.lastY-avenue);
			this.countmoves += Math.abs(this.lastX-street);
			this.lastY = avenue;
			this.lastX = street;
			
		}
		
		
	}
	
	/**
	 * Returns / gives the amount of the steps the opponent robot took
	 * @return
	 */
	public int getcountmoves(){
		
		return this.countmoves;
		
	}
	
}