# robotwars

Robot War game

By Aman Adhav

One would assume that the incorporation of multiple different algorithms in this Java program would have better results against other robots and their algorithms. In my project, I have incorporated 4 different algorithms in 3 different robots. When testing the robots, the results from each test followed a particular trend that I will discuss in this article.

For this summative, I created 3 different kinds of robots:

AmanTestFighter
AmanTestFighter2
AmanBot2

AmanTestFighter
This robot incorporates an algorithm to attack the robot that is closest to its current location.
It incorporates classes such as huntX and huntY which find the closest enemy on a particular street or avenue. In order to find the nearest robot, it finds the nearest robot by distance. This is how it tries to find its ideal kill. The robots at the very back are the farthest while the robots at the front are the closest making them an easy kill. The AmanTestFighter robot incorporates the algorithm to save the most energy. However, if the closest robot has the most energy and health, it fails to understand this situation. When it comes to analyzing the energy and health the robot has, AmanTestFighter will fall flat. It is an effective robot however it won't last long on the battlefield.

AmanTestFighter2:
Unlike the AmanTestFighter robot, the algorithm for the AmanTestFighter2 is to attack the robot with the least health. It incorporates a similar setup for the classes like the AmanTestFighter however, the algorithm increases the chances of the robot getting extra bonus energy and points.
Although it can be effective, the weakest robot might be farthest away and thus it might cause the robot to loose a lot of energy in the process.

The main robots that incorporate the most complex algorithms is AmanBot2.

AmanBot2:
It incorporates three algorithms each for specific purposes. The AmanBot2 takes in distance, health and the number of steps the enemy took. It is basically the incorporation of AmanTestFighter2 and AmanTestFighter with enhanced capabilities. The robot takes in data from the AmanBotOppData class. This class is the extension of the original OppData class as it incorporates the number of steps the other enemy bots took in the past terms. The incorporation
of the amount of energy a robot consumed is the most important part. If a particular robots travels more, than it would have consumed more energy. Thus as the OppData offers the number of steps the other robots moved, it enable the AmanBot2 to be smarter than other robots.

The distance and health of each enemy robot is sorted (selection sort). The one with the least distance and least health is the ideal robot to target in this situation. Now the robot is incorporating three different methods of finding its ideal target. However it sometimes gets very confused as to which robots to attack.
Distance
Health
Energy
Low
Low
Low
Low
High
High
Low
Low
High
Low
High
Low
High
High
High
	
	There are around 27 combinations that go with this. Therefore the robot starts to get very
confused as to finding the ideal target. To make it even more complex, the robot also incorporates a medium level to make another 81 combinations. The robots stops moving and thus starts receiving penalties. In this situation, I tried to introduced the medium level in order to incorporate fuzzy logic in the end. Nonetheless, the robot was very cautious as to attacking other robots. The other robots fight alone and individually. Through observing AmanBot2 in the games, the robot seemed to attack when other bots were fighting. It is almost as if it is stealing the kills. This makes AmanBot2 very smart however at the same time confused.

Please find the attached Trials file (the AmanBot's are 1,2,7,8)

TRIAL # 1
Player Statistics
ID   Rounds Won     Rounds Loss    Fights Initiated    Fights Defended     Number of Moves     Number of Penalities     Number of Kills     
                                   Wins  Loss  Ties    Wins  Loss  Ties  
  0     72            101           15     0     1      1    22     3               10                  10                   1
  1    123            119           27     6     1      1    24     0               12                   5                   0
  2     19             94            2     1     0      4    21     1               23                  10                   0
  3    178            127           41     5    27      5    23    13               25                  10                   2
  4    144            128           30     4    12      3    28     1               27                   5                   1
  5    286             43           65     5     5      4     3    25               24                   5                   4
  6      8             80            0     0     1      1    20     1               14                  20                   0
  7      8             90            0     0     0      1    21     2                5                  10                   0
  8     14             70            1     0     0      1    19     1                2                  30                   0

Penalties given to the following robots:
ID #0
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #1
	Penalty: Did not initiate a fight in 5 rounds.
ID #2
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #3
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #4
	Penalty: Did not initiate a fight in 5 rounds.
ID #5
	Penalty: Did not initiate a fight in 5 rounds.
ID #6
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #7
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #8
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
#---------------------------------------------------------------------------------------------------------------#

TRIAL # 2

Player Statistics
ID   Rounds Won     Rounds Loss    Fights Initiated    Fights Defended     Number of Moves     Number of Penalities     Number of Kills     
                                   Wins  Loss  Ties    Wins  Loss  Ties  
  0     29             99            5     2     1      0    23     0               14                   5                   0
  1    119            115           25     2     1      7    25     8               10                   5                   1
  2    151            123           34     3     3      2    27     2               11                   5                   2
  3     69            112           17     2     6      0    22     0                4                   0                   1
  4    282             39           66     4     2      2     4    22               14                   0                   1
  5    183            126           42     7    23      3    23     0               11                   0                   3
  6      9             90            0     0     0      1    21     0                4                  10                   0
  7     14             80            0     0     0      1    20     2                4                  20                   0
  8     23             95            1     0     0      4    25     2                1                   5                   0

Penalties given to the following robots:
ID #0
	Penalty: Did not initiate a fight in 5 rounds.
ID #1
	Penalty: Did not initiate a fight in 5 rounds.
ID #2
	Penalty: Did not initiate a fight in 5 rounds.
ID #3
ID #4
ID #5
ID #6
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #7
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #8
	Penalty: Did not initiate a fight in 5 rounds.
#-------------------------------------------------------------------------------------#

TRIAL # 3

Player Statistics
ID   Rounds Won     Rounds Loss    Fights Initiated    Fights Defended     Number of Moves     Number of Penalities     Number of Kills     
                                   Wins  Loss  Ties    Wins  Loss  Ties  
  0    107            133           24     2     2      5    32     1               36                   5                   1
  1    110            117           28     4     4      2    25     3               37                   5                   1
  2     24            102            4     1     3      3    23     2               13                   5                   0
  3    143            135           36     7    15      0    25     5               25                   0                   1
  4    204            164           46     4    30      6    35    18               35                   5                   3
  5    297             36           69     5     6      1     3    23               38                   5                   2
  6     11             70            0     0     0      3    19     2               10                  30                   0
  7     20             90            0     0     0      2    23     3               13                  10                   0
  8     11             80            0     0     0      1    22     3               10                  20                   0

Penalties given to the following robots:
ID #0
	Penalty: Did not initiate a fight in 5 rounds.
ID #1
	Penalty: Did not initiate a fight in 5 rounds.
ID #2
	Penalty: Did not initiate a fight in 5 rounds.
ID #3
ID #4
	Penalty: Did not initiate a fight in 5 rounds.
ID #5
	Penalty: Did not initiate a fight in 5 rounds.
ID #6
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #7
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
ID #8
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
	Penalty: Did not initiate a fight in 5 rounds.
#-------------------------------------------------------------------------------------#
