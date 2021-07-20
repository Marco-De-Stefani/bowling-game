# bowling-game
#############################################################################
###   Bowling game simple implementation                                  ###
#############################################################################

Hi, this is a simple bowling game implementation based on Spring Boot with a Hibernate H2 database.

An example of the game can be found in resources/gameRequests.http

- API for adding a Player or for retrieving players --> /players
- API for adding a new Game to a player --> /bowling/game
- API for getting the score of the current game --> /bowling/game/{gameId}/score
- API for adding a Frame to a Game --> /bowling/games/{gameId}/frames/

