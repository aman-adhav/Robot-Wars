package robotwarsummative;


import becker.robots.*;
import java.util.*;

public class BattleManager {

	// Public constants for the width and height of the arena.  Can be used in other classes by saying BattleManager.WIDTH.
	public static final int WIDTH = 20;
	public static final int HEIGHT = 12;

	// Constants used in the game to govern the max amount of health, energy, etc. 
	private static final int NUM_SIDES_ON_DICE = 10;
	private static final int MAX_HEALTH = 100;
	private static final int MAX_ENERGY = 100;
	private static final int ATTACKER_ENERGY_CHANGE = 20;
	private static final int DEFENDER_ENERGY_CHANGE = 10;
	private static final int TIE_ENERGY_CHANGE = 5;
	private static final int MOVES_ENERGY_COST = 5;
	private static final int TURN_ENERGY_INCREASE = 1;
	private static final int DEAD_FIGHTER_ID = -1;
	private static final int NO_FIGHT_PENALTY = 5;
	private static final int HEALTH_LOST_PENALTY = 1;
	private static final int WIN_FIGHT_HEALTH_GAINS = 2;
	private static final int KILLED_DEFENDER_HEALTH_GAINS = 10;
	private static final int NUM_ROUNDS_OF_NO_FIGHTS = 5;

	private static City arena = new City(HEIGHT,WIDTH);
	private static int num_players = 9;						// number of FighterRobots
	private static FighterRobot [] player = new FighterRobot[num_players];
	private static int[] health = new int[num_players];		// an array to keep track of the FighterRobots' health (index position refers to the robot ID#)
	private static int[] energy = new int[num_players];		// an array to keep track of the FighterRobots' energy (index position refers to the robot ID#)
	private static int[] numFights = new int[num_players];	// an array to keep track of the number of fights, a penalty will apply if robot does not initiate a fight in 5 rounds.
	private static PlayerStats[] stats = new PlayerStats[num_players];
	private static int num_lost = 0;

	/**
	 * Main method that sets up the arena and controls the fighting process between the FightingRobots
	 * @param args
	 */
	public static void main(String[] args) {
		createCity();
		createPlayers();

		OppData[] playerData = new OppData[num_players];

		//continues to take turn until one player wins
		while (num_lost < num_players - 1)
		{
			//each player will take turn
			for (int i = 0; i < num_players; i++)
			{
				if (num_lost < num_players - 1)
				{
					//create updated Opponent data of each player and sends as a parameter to the current player
					for (int j = 0; j < num_players; j++)
					{
						if (health[j] <= 0)
							playerData[j] = new OppData(j, -1, -1, 0);
						else
							playerData[j] = new OppData(j, player[j].getAvenue(), player[j].getStreet(), health[j]);
					}

					//the current player takes its turn if it is still in play 
					if (health[i] > 0)
					{
						System.out.println("Player id " + i + " initial energy level " + energy[i]);
						doTurn(i, player[i].takeTurn(energy[i], playerData));
						System.out.println("Turn Result:");
						for (int k = 0; k < num_players; k ++)
						{
							if (health[k] > 0)
							{
								if (energy[k] + TURN_ENERGY_INCREASE <= MAX_ENERGY)
									energy[k] += TURN_ENERGY_INCREASE;
								else
									energy[k] = MAX_ENERGY;
							}
							System.out.println("ID " + k + " Health " + health[k] + " Energy " + energy[k]);
						}
						System.out.println("******************************************");
					}
				}
			}
		}

		System.out.println("Player Statistics");
		System.out.format("%-5s%-15s%-15s%-20s%-20s%-20s%-25s%-20s", "ID", "Rounds Won", "Rounds Loss", "Fights Initiated", "Fights Defended", "Number of Moves", "Number of Penalities", "Number of Kills");
		System.out.println();
		System.out.format("%39s%2s%-6s%-8s%-6s%-6s%-6s", "Wins", "", "Loss", "Ties", "Wins", "Loss", "Ties");
		System.out.println();
		for (int n = 0; n < num_players; n++)
		{
			System.out.format("%3d",n);
			System.out.format("%7d", stats[n].getRoundsWin());
			System.out.format("%15d", stats[n].getRoundsLoss());
			System.out.format("%13d", stats[n].getFightsInitiatedWin());
			System.out.format("%6d", stats[n].getFightsInitiatedLoss());
			System.out.format("%6d", stats[n].getFightsInitiatedTie());
			System.out.format("%7d", stats[n].getFightsDefendWin());
			System.out.format("%6d", stats[n].getFightsDefendLoss());
			System.out.format("%6d", stats[n].getFightsDefendTie());
			System.out.format("%17d", stats[n].getTotalNumMoves());
			System.out.format("%20d", stats[n].getNumPenalties());
			System.out.format("%20d", stats[n].getNumKills());
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Penalties given to the following robots:");
		for (int n = 0; n < num_players; n++)
		{
			System.out.println("ID #" + n);
			ArrayList comments = stats[n].getPenalitiesComments();
			for (int a = 0; a < comments.size(); a++)
				System.out.println("\t" + comments.get(a));
		}
	}

	/**
	 * Helper method used to sets up the arena
	 */
	private static void createCity(){	
		// Build walls for the arena
		for (int i = 0; i<WIDTH; i++)
		{
			Wall topWalls = new Wall (arena, 0, i, Direction.NORTH);
			Wall bottomWalls = new Wall (arena, HEIGHT-1, i, Direction.SOUTH);
		}

		for (int i = 0; i<HEIGHT; i++)
		{
			Wall leftWalls = new Wall (arena, i, 0, Direction.WEST);
			Wall rightWalls = new Wall (arena, i, WIDTH-1, Direction.EAST);
		}
	}

	/**
	 * Helper method used to sets up the players and randomly place them in the arena
	 */
	private static void createPlayers()
	{
		Random generator = new Random();
		// create robots for the arena 
		player[0] = new AmanBot(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 0, MAX_HEALTH);
		player[1] = new AmanBot(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 1, MAX_HEALTH);
		player[2] = new AmanBot(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 2, MAX_HEALTH);
		player[3] = new AmanTestFighterRobot(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 3, MAX_HEALTH);
		player[4] = new AmanTestFighterRobot2(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 4, MAX_HEALTH);
		player[5] = new AmanTestFighterRobot2(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 5, MAX_HEALTH);
		player[6] = new AmanBot2(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 6, MAX_HEALTH);
		player[7] = new AmanBot2(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 7, MAX_HEALTH);
		player[8] = new AmanBot2(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, 8, MAX_HEALTH);
		
		
		for (int i = 0; i < num_players; i++)
		{
			//player[i] = new AmanTestFighterRobot(arena,generator.nextInt(HEIGHT),generator.nextInt(WIDTH),Direction.NORTH, i, MAX_HEALTH);
			stats[i] = new PlayerStats();
			health[i] = MAX_HEALTH;
			energy[i] = MAX_ENERGY;
			numFights[i] = 0;
		}
	}

	/**
	 * Helper method used to control the turn taking process involving the movement, fighting and defending between FighterRobots.
	 * @param attackerID	The FighterRobot ID whose currently taking its turn.
	 * @param request		The TurnRequest object sent by the FighterRobot whose currently taking its turn.
	 */
	private static void doTurn(int attackerID, TurnRequest request)
	{
		int attackerHealthLost = 0;
		int defenderHealthLost = 0;

		int defenderID = request.getFightID();
		System.out.println("fighting id: " + defenderID);
		if (defenderID == DEAD_FIGHTER_ID)
		{
			if (numFights[attackerID] == NUM_ROUNDS_OF_NO_FIGHTS)
			{
				applyPenalty(attackerID, attackerHealthLost, NO_FIGHT_PENALTY, "Penalty: Did not initiate a fight in 5 rounds.");
				numFights[attackerID] = 0;
			}
			numFights[attackerID] ++;
		}
		else
		{
			numFights[attackerID] = 0;
		}

		int numOfMoves = Math.abs(player[attackerID].getAvenue() - request.getEndAvenue()) + Math.abs(player[attackerID].getStreet() - request.getEndStreet());
		//requested location is beyond the battlefield
		if (request.getEndAvenue() > WIDTH - 1 || request.getEndStreet() > HEIGHT - 1 || request.getEndStreet() < 0 || request.getEndAvenue() < 0)
		{
			attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: Requested location is beyond battlefield");
		}
		//requested location requires more than the maximum number of moves designated by player
		else if (numOfMoves > player[attackerID].getNumMoves())
		{
			attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: Requested location requires more than maximum number of moves");
		}
		//requested location requires more than the available energy	
		else if ((energy[attackerID] - numOfMoves * MOVES_ENERGY_COST) < 0)
		{
			attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: Requested location requires more than available energy");
		}
		//penalty applies if requested number of rounds of fight is greater than the attack power assigned
		else if (player[attackerID].getAttack() < request.getNumRounds() || request.getNumRounds() < -1)
		{
			attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: invalid requested number of rounds of fight");
		}
		else
		{
			player[attackerID].goToLocation(request.getEndAvenue(), request.getEndStreet());
			energy[attackerID] = energy[attackerID] - (numOfMoves * MOVES_ENERGY_COST);
			stats[attackerID].addTotalNumMoves(numOfMoves);

			//player dies if moved to the wrong location
			if (player[attackerID].getAvenue() != request.getEndAvenue() || player[attackerID].getStreet() != request.getEndStreet())
			{
				attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, health[attackerID], "Penalty: Moved to the wrong location" );
			}
			//fight will occur if the player moves to meet the requested opponent
			
			else if (defenderID == attackerID)
			{
				attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: Try to fight yourself");
			}
			else if (defenderID != DEAD_FIGHTER_ID)
			{
				//fight only occurs if the player's location is the same as the requested opponent
				if (energy[attackerID] > 0 && player[attackerID].getAvenue()==player[defenderID].getAvenue() && player[attackerID].getStreet()==player[defenderID].getStreet())
				{
					for (int n = 0; n < request.getNumRounds(); n ++)
					{
						if (energy[attackerID] > 0 && health[attackerID] > 0 && health[defenderID] > 0)
						{
							if (energy[defenderID] <= 0)
							{
								defenderHealthLost ++;	
								health[defenderID] -= 1;
								energy[attackerID] = energy[attackerID] + ATTACKER_ENERGY_CHANGE;
								stats[defenderID].addRoundsLoss();
								stats[attackerID].addRoundsWin();									
							}
							else
							{
								Random generator = new Random();
								int[] attackDice = new int[player[attackerID].getAttack()];
								int[] defenceDice = new int[player[defenderID].getDefence()];

								for (int i = 0; i < attackDice.length; i++)
									attackDice[i] = generator.nextInt(NUM_SIDES_ON_DICE) + 1;
								Arrays.sort(attackDice);

								for (int j = 0; j < defenceDice.length; j++)
									defenceDice[j] = generator.nextInt(NUM_SIDES_ON_DICE) + 1;	
								Arrays.sort(defenceDice);

								//output the results from the dice rolled
								System.out.print("Attacker rolled: ");
								for (int a = 0; a < attackDice.length; a++)
									System.out.print(attackDice[a] + " ");	
								System.out.print (" Defender rolled: ");
								for (int d = 0; d < defenceDice.length; d++)
									System.out.print(defenceDice[d] + " ");	
								System.out.println();

								System.out.println("\t highest number: attacker " + attackDice[player[attackerID].getAttack()-1] + " defender " + defenceDice[player[defenderID].getDefence()-1]);

								//match up the highest numbered rolled from the attacker and defender
								if (defenceDice[player[defenderID].getDefence()-1] > attackDice[player[attackerID].getAttack()-1])
								{
									attackerHealthLost ++;	
									health[attackerID] -= 1;
									energy[attackerID] = energy[attackerID] - ATTACKER_ENERGY_CHANGE;
									energy[defenderID] = energy[defenderID] + DEFENDER_ENERGY_CHANGE;									
									stats[attackerID].addRoundsLoss();
									stats[defenderID].addRoundsWin();
								}
								else if (defenceDice[player[defenderID].getDefence()-1] == attackDice[player[attackerID].getAttack()-1])
								{
									energy[attackerID] = energy[attackerID] - TIE_ENERGY_CHANGE;
									energy[defenderID] = energy[defenderID] - TIE_ENERGY_CHANGE;																		
								}
								else
								{
									defenderHealthLost ++;	
									health[defenderID] -= 1;
									energy[defenderID] = energy[defenderID] - DEFENDER_ENERGY_CHANGE;
									energy[attackerID] = energy[attackerID] + ATTACKER_ENERGY_CHANGE;
									stats[defenderID].addRoundsLoss();
									stats[attackerID].addRoundsWin();									
								}

							}
						}
						//adjust energy level
						if (energy[attackerID] < 0)
							energy[attackerID] = 0;
						else if (energy[attackerID] > MAX_ENERGY)
							energy[attackerID] = MAX_ENERGY;
						
						if (energy[defenderID] < 0)
							energy[defenderID] = 0;
						else if (energy[defenderID] > MAX_ENERGY)
							energy[defenderID] = MAX_ENERGY;
					}

					adjustStats(attackerID, defenderID, attackerHealthLost, defenderHealthLost);

					//return battle result to defender
					player[defenderID].battleResult(defenderHealthLost, attackerID, attackerHealthLost);
					player[defenderID].setLabel();
					if (health[defenderID]==0)
						num_lost ++;

					System.out.println("Energy Result: Attacker ID " + attackerID + " energy: " + energy[attackerID] + " Defender ID " + defenderID + " energy: " + energy[defenderID]);
					System.out.println("Health Result: Attacker ID " + attackerID + " health " + health[attackerID] + " Defender ID " + defenderID + " health " + health[defenderID]);						
				}
				else
				{
					attackerHealthLost = applyPenalty(attackerID, attackerHealthLost, HEALTH_LOST_PENALTY, "Penalty: Did not have enough energy to fight or Did not move to the opponent location");
				}
			}
		}

		//return battle result to attacker
		player[attackerID].battleResult(attackerHealthLost, defenderID, defenderHealthLost);
		player[attackerID].setLabel();
		if (health[attackerID]==0)
			num_lost ++;
	}
	
	private static int applyPenalty(int attackerID, int attackerHealth, int numPenalty, String penality)
	{
		// prevent the robot from going into negative health due to penalties
		if (health[attackerID]<numPenalty)
			health[attackerID]=0;
		else
			health[attackerID] -= numPenalty;
		
		for (int i = 0; i < numPenalty; i++)
		{
			attackerHealth ++;
			stats[attackerID].addNumPenalties();
		}
		stats[attackerID].addPenaltiesComments(penality);
		//System.out.println(penality);

		return attackerHealth;
	}
	
	private static void adjustStats(int attackerID, int defenderID, int attackerHL, int defenderHL)
	{
		//add number of fights win, loss or tie
		if (attackerHL > defenderHL)
		{
			stats[defenderID].addFightsDefendWin();
			stats[attackerID].addFightsInitiatedLoss();
		}
		else if (attackerHL < defenderHL)
		{
			stats[defenderID].addFightsDefendLoss();
			stats[attackerID].addFightsInitiatedWin();
			if (health[attackerID] + WIN_FIGHT_HEALTH_GAINS < MAX_HEALTH)
				health[attackerID] += WIN_FIGHT_HEALTH_GAINS;
			else
				health[attackerID] = MAX_HEALTH;
		}
		else
		{
			stats[defenderID].addFightsDefendTie();
			stats[attackerID].addFightsInitiatedTie();
		}

		//add number of kills for player statistics
		if (health[attackerID] == 0)
			stats[defenderID].addNumKills();
		else if (health[defenderID] == 0)
		{
			stats[attackerID].addNumKills();
			if (health[attackerID] + KILLED_DEFENDER_HEALTH_GAINS < MAX_HEALTH)
				health[attackerID] += KILLED_DEFENDER_HEALTH_GAINS;
			else
				health[attackerID] = MAX_HEALTH;
		}
	}
}