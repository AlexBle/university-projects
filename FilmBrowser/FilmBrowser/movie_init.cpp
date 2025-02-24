#include "Movie.h"
#include "config.h"
Movie* create() {
	Movie movie_collection[5];
	movie_collection[0] = Movie("The Godfather", std::string(ASSET_PATH) + "godfather.png", "Francis Ford Coppola", "Marlon Brando, Al Pacino, James Caan", "The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.", "Crime, Drama", "1972", "175");
	movie_collection[1] = Movie("Good Will Hunting", std::string(ASSET_PATH) + "godwillhunting.png", "Gus Van Sant", "Robin Williams, Matt Damon, Ben Affleck", "Will Hunting, a janitor at M.I.T., has a gift for mathematics, but needs help from a psychologist to find direction in his life.", "Drama, Romance", "1997", "126");
	movie_collection[2] = Movie("Inception", std::string(ASSET_PATH) + "inception.png", "Christopher Nolan", "Leonardo Dicaprio, Joseph Gordon-Levitt, Elliot Page, Tom Hardy", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", "Action, Adventure", "2010", "158");
	movie_collection[3] = Movie("Independence Day", std::string(ASSET_PATH) + "indepedenceday.png", "Roland Emmerich", "Will Smith, Bill Pullman, Jeff Goldblum", "The aliens are coming and their goal is to invade and destroy Earth. Fighting superior technology, mankind's best weapon is the will to survive.", "Action", "1996", "145");
	
	Movie* array_ptr = movie_collection;
	return array_ptr;
}