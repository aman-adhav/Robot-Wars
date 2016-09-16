package robotwarsummative;

import java.awt.Color;
import becker.robots.City;
import becker.robots.Direction;
/**
 * Robot Summative : Tester Bot2
 * @author Aman Adhav
 * @version Date 2016-01-26
 */
public class AmanTestFighterRobot2 extends FighterRobot {

	
	private int health = 100;
	private int canmove;

	/**
	 * Constructor Method
	 * @param arena = city of the robot
	 * @param a = starting avenue of the robot
	 * @param s = starting street of the robot
	 * @param direction = starting direction of the robot
	 * @param id = id of the robot
	 * @param attack = attack power of the robot
	 */
	public AmanTestFighterRobot2(City arena, int a, int s,Direction direction, int id, int attack) {

		super(arena,a,s,direction,id,5, 2, 3);
		this.setLabel();

	}

	/**
	 * Set's the robots label
	 */
	public void setLabel(){
		this.setColor(Color.BLUE);
		this.setLabel(this.getID()+": "+Integer.toString(health));
		

		if (this.health <= 0){
			this.setColor(Color.BLACK);
		}
	}

	/**
	 * Goes to a coordinate given
	 * @param q = avenue of the desired location
	 * @param w = street of the desired location
	 */
	public void goToLocation(int q, int w){
		
		int count = 0;
		System.out.println("Clutch for Skins : "+q+"  "+w);
		
		if (this.getAvenue() < q){
			
			while(this.isFacingEast() == false){
				this.turnLeft();
			}
			
			while(this.getAvenue() != q ){
				this.move();
				count += 1;
			}
		}
		else if (this.getAvenue() > q){
			
			while(this.isFacingWest() == false){
				this.turnLeft();
			}
			
			while(this.getAvenue() != q ){
				this.move();
				count += 1;
			}
			
		}
		
		if (this.getStreet() < w){
			
			while(this.isFacingSouth() == false){
				this.turnLeft();
			}
			
			while(this.getStreet() != w ){
				this.move();
				count += 1;
			}
		}
		else if (this.getStreet() > w){
			
			while(this.isFacingNorth() == false){
				this.turnLeft();
			}
			
			while(this.getStreet() != w ){
				this.move();
				count += 1;
			}
			
		}
			

	}

	/**
	 * Requests turn from the battle manager
	 * @param energy = the energy that the robot has
	 * @param w = the information about the opponents
	 */
	public TurnRequest takeTurn(int energy, OppData[] w){

		TurnRequest amanbot;

		this.health = w[this.getID()].getHealth();
		this.setLabel();

		int kill = this.kill(w,this.getAvenue(),this.getStreet(),this.getID());
		
		int turnX = w[kill].getAvenue();
		int turnY = w[kill].getStreet();

		if (energy > 15){
			this.canmove = 3;
		}
		else{
			this.canmove = energy/5;
		}

		int moveX = stratX(turnX);
		int moveY = stratY(turnY);
		
		if (w[kill].getStreet() == this.getStreet() && w[kill].getAvenue() == this.getAvenue()){
			amanbot = new TurnRequest(moveX, moveY, kill, energy/20);
		}
		else {
			amanbot = new TurnRequest(moveX, moveY, -1, 0);
		}

		
		return amanbot;

	}
	

	/**
	 * Travels to a y location based on the maximum number of moves allowed
	 * @param turnY = Y location or street that the robot wants to move
	 * @return stratY = The actual street that the robot is allowed to move into
	 */
	private int stratY(int turnY) {
		
		int nowY = this.getStreet();
		
		if (nowY > turnY){
			
			while (turnY < nowY && 0 < this.canmove){
				
				this.canmove -= 1;
				nowY -= 1;
				
			}
			
		}
		else if (nowY < turnY)
		{	
			
			while (turnY > nowY && 0 < this.canmove){
				
				this.canmove -= 1;
				nowY += 1;
				
			}
			
		}
		return nowY;
		
	}

	/**
	 * Calculates the maximum point that my robot is allowed to move in x direction
	 * @param turnX = avenue of the enemy
	 * @return posX = avenue that my robot is allowed to move to
	 */
	private int stratX(int turnX) {
		
		int posX = this.getAvenue();
		
		if (posX > turnX){
			
			while (turnX < posX && 0 < this.canmove){
				
				this.canmove -= 1;
				posX -= 1;
				
			}
			
		}
		else if (posX < turnX){
			
			while (turnX > posX && 0 < this.canmove){
				
				this.canmove -= 1;
				posX += 1;
				
			}
			
		}
		return posX;
	}

	/**
	 * Selects a kill opponent to attack
	 * @param w = additional information about the opponents
	 * @param avenue = avenue of the robot
	 * @param street = street of the robot
	 * @param j = Id of the robot
	 * @return the id of the kill robot
	 */
	private int kill(OppData[] w, int avenue, int street, int j) {

		int fig = -10;
		int health = 0;

		for (int i = 0; i<w.length;i++){

			if (j != i && w[i].getHealth()>0){

				if(fig<0 || w[i].getHealth()<health){
					health = w[i].getHealth();
					fig = i;
				}
			}

		}
		return fig;
	}

	/**
	 * Receives Battle Results
	 * @param a = Health Lost
	 * @param b = Opponent Id
	 * @param c = Opponent Health Lost
	 */
	public void battleResult(int a,int b,int c){
		
		this.health = this.health-a;
		
		this.setLabel();

	}

}
