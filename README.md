
# The assignment
A game of drawing 6 sticks where one stick is shorter. The table has 6 seats for 6 players. There is a croupier at the table running the game. N players periodically enter the room at x intervals (0 <= x <= 1 second) and sit down if there is available seat.

If all seats are taken, the player leaves the room. After the table is full, the game begins. The table is round and each place is numbered (1 to 6).

The first round begins with the first player to draw the stick out of the croupier's hand. Before drawing a stick, all other players guess at one of two outcomes (did the player draw a shorter or a regular stick).

Only the croupier is aware of which stick is shorter. After all players have made their predictions, the first player draws a stick. Depending on the outcome, players get one point if their prediction was correct.

If a player drew a short stick, he leaves the table and the game starts from the beginning. If he has drawn a normal stick then the game goes to the next round. So one party can have macximum 6 rounds.

M rounds are played before the croupier shows who is the player with most points.

# Rules
There must be two console applications:

one for the croupier - it'll have ServerSocket in it
second one for the players - it'll have one thread for each player opening socket connection
All messages between client (players) and server (croupier) must be in JSON format.

Both applications should run on the same computer in localhost.

Each player should have their own UUID.

# How to run program
First, start the server (ServerMain class), and type in maximum number of rounds. Then, start the clients (ClientMain class), and type in total number of players
