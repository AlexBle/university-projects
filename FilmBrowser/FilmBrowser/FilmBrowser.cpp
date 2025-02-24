#include "FilmBrowser.h"
void FilmBrowser::update()
{
	//initialization of all the buttons 
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	if (!b_init && graphics::getGlobalTime() > 10) {
		b_init = 1;

		button_reset = new Button();
		button_reset->init(30,80,200,50);
		button_action = new Button();
		button_action->init(30, 80, 235 , 50);
		button_adventure = new Button();
		button_adventure->init(30, 80, 270, 50);
		button_comedy = new Button();
		button_comedy->init(30, 80, 305, 50);
		button_crime = new Button();
		button_crime->init(30, 80, 340, 50);
		button_drama = new Button();
		button_drama->init(30, 80, 375, 50);
		button_fantasy = new Button();
		button_fantasy->init(30, 80, 410, 50);
		button_history = new Button();
		button_history->init(30, 80, 445, 50);
		button_scifi = new Button();
		button_scifi->init(30, 80, 480, 50);
		button_bar_up = new Button();
		button_bar_up->init(25,20,12,990);
		button_bar_down = new Button();
		button_bar_down->init(25, 20, 487, 990);

	}
	if (b_init) {
		if (button_reset->pressButton()) {
			for (int i = 0; i < 20; i++) {
				active_movies_list[i] = movie_collection[i];
			}
			active_movies = 19;
			bar->update(450 / active_movies);
			yearFrom_int = 0;
			yearTo_int = 2023;
			title_string = "";
			director_string = "";
			star_string = "";
		}
		
		if (button_action->pressButton()) {
			search_categories("Action");
		}
		if (button_adventure->pressButton()) {
			search_categories("Adventure");
		}
		if (button_comedy->pressButton()) {
			search_categories("Comedy");
		}
		if (button_crime->pressButton()) {
			search_categories("Crime");
		}
		if (button_drama->pressButton()) {
			search_categories("Drama");
		}
		if (button_fantasy->pressButton()) {
			search_categories("Fantasy");
		}
		if (button_history->pressButton()) {
			search_categories("History");
		}
		if (button_scifi->pressButton()) {
			search_categories("SciFi");
		}

		//button that moves the scrollbar up
		//it depends on the number of movies
		if (button_bar_up->pressButton() && bar->inBoundsUp()) {
			bar->update(bar->get_bar_center_y() - 450 / active_movies, 0);
		}
		//button that moves the scrollbar down
		//it depends on the number of movies
		if (button_bar_down->pressButton() && bar->inBoundsDown()) {
			bar->update(bar->get_bar_center_y() + 450 / active_movies, 0);
		}
	}
	
	if (yearFrom) {
		yearFrom->pressTextBox();
		if (yearFrom->getState()) {
			yearTo->update(0);
			directorSearch->update(0);
			titleSearch->update(0);
			starSearch->update(0);
			if (graphics::getKeyState(graphics::SCANCODE_1)) {
				yearFrom_string += "1";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_2)) {
				yearFrom_string += "2";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_3)) {
				yearFrom_string += "3";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_4)) {
				yearFrom_string += "4";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_5)) {
				yearFrom_string += "5";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_6)) {
				yearFrom_string += "6";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_7)) {
				yearFrom_string += "7";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_8)) {
				yearFrom_string += "8";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_9)) {
				yearFrom_string += "9";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_0)) {
				yearFrom_string += "0";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_BACKSPACE)) {
				if (yearFrom_string != "") {
					yearFrom_string.pop_back();
				}
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_RETURN)) {
				yearFrom->update(0);
				if (yearFrom_string != "") {
					yearFrom_int = stoi(yearFrom_string);
					if (yearTo_string == "") {
						yearTo_string = "2023";
					}
					yearTo_int = stoi(yearTo_string);
					search_year(yearFrom_int,yearTo_int);
				}
			}
		}
	}
	if (yearTo) {
		yearTo->pressTextBox();
		if (yearTo->getState()) {
			yearFrom->update(0);
			directorSearch->update(0);
			titleSearch->update(0);
			starSearch->update(0);
			if (graphics::getKeyState(graphics::SCANCODE_1)) {
				yearTo_string += "1";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_2)) {
				yearTo_string += "2";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_3)) {
				yearTo_string += "3";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_4)) {
				yearTo_string += "4";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_5)) {
				yearTo_string += "5";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_6)) {
				yearTo_string += "6";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_7)) {
				yearTo_string += "7";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_8)) {
				yearTo_string += "8";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_9)) {
				yearTo_string += "9";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_0)) {
				yearTo_string += "0";
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_BACKSPACE)) {
				if (yearTo_string != "") {
					yearTo_string.pop_back();
				}
				Sleep(ELAPSE_TIME);
			}
			else if (graphics::getKeyState(graphics::SCANCODE_RETURN)) {
				yearTo->update(0);
				if (yearTo_string != "") {
					if (yearFrom_string == "") {
						yearFrom_string = "0";
					}
					yearFrom_int = stoi(yearFrom_string);
					yearTo_int = stoi(yearTo_string);
					search_year(yearFrom_int, yearTo_int);
				}
			}
		}
	}
	

	//initiaization of the scroll bar and text boxes
	if (!bar_init && graphics::getGlobalTime() > 10) {
		bar_init = 1;
		bar = new Bar();
		bar->init(990, 250, 20, 450, 990, 25+225/active_movies, 15, 450/active_movies);

		yearFrom = new TextBox();
		yearFrom->init(30, 80, 220, 150);
		yearTo = new TextBox();
		yearTo->init(30, 80, 290, 150);
		titleSearch = new TextBox();
		titleSearch->init(30, 180, 43, 100);
		directorSearch = new TextBox();
		directorSearch->init(30, 180, 103, 100);
		starSearch = new TextBox();
		starSearch->init(30, 180, 163, 100);
	}
	if (bar_init) {
		bar->scrollBar(bar);
		
		titleSearch->pressTextBox();
		if (titleSearch->getState()) {
			directorSearch->update(0);
			starSearch->update(0);
			yearTo->update(0);
			yearFrom->update(0);
			title_string = titleSearch->charSearch(title_string);
			if (title_string.find("|") != std::string::npos) {
				title_string.pop_back();
				search_string(title_string, 0);
			}
		}

		directorSearch->pressTextBox();
		if (directorSearch->getState()) {
			titleSearch->update(0);
			starSearch->update(0);
			yearTo->update(0);
			yearFrom->update(0);
			director_string = titleSearch->charSearch(director_string);
			if (director_string.find("|") != std::string::npos) {
				director_string.pop_back();
				search_string(director_string, 1);
			}
		}
		
		starSearch->pressTextBox();
		if (starSearch->getState()) {
			directorSearch->update(0);
			titleSearch->update(0);
			yearTo->update(0);
			yearFrom->update(0);
			star_string = titleSearch->charSearch(star_string);
			if (star_string.find("|") != std::string::npos) {
				star_string.pop_back();
				search_string(star_string, 2);
			}
		}
	}
	
}

void FilmBrowser::draw()
{	
	//Draws all the widgets

	graphics::drawRect(100, 250, 200, 500, br);

	if (b_init) {
		button_reset->draw();
		brrr.outline_opacity = 0.0f;
		brrr.fill_color[0] = 0.6f;
		brrr.fill_color[1] = 0.1f;
		brrr.fill_color[2] = 0.3f;
		graphics::drawRect(50, 200, 80, 30,brrr);
		graphics::drawText(25, 192 + 15, 20, "RESET", brr);
		button_action->draw();
		graphics::drawText(25, 228 + 15, 20, "Action", brr);
		button_adventure->draw();
		graphics::drawText(13, 262 + 15, 18, "Adventure", brr);
		button_comedy->draw();
		graphics::drawText(18, 297 + 15, 20, "Comedy", brr);
		button_crime->draw();
		graphics::drawText(27, 332 + 15, 20, "Crime", brr);
		button_drama->draw();
		graphics::drawText(23, 367 + 15, 20, "Drama", brr);
		button_fantasy->draw();
		graphics::drawText(20, 402 + 15, 19, "Fantasy", brr);
		button_history->draw();
		graphics::drawText(22, 437 + 15, 20, "History", brr);
		button_scifi->draw();
		graphics::drawText(30, 472 + 15, 20, "SciFi", brr);
		
		bar->draw();
		
		button_bar_up->draw();
		
		button_bar_down->draw();
		
		yearFrom->draw();
		graphics::drawText(120, 225, 20, yearFrom_string, brr);
		graphics::drawText(105, 198, 20, "Year From:", brr);
		
		yearTo->draw();
		graphics::drawText(120, 295, 20, yearTo_string, brr);
		graphics::drawText(110, 268, 20, "Year To:", brr);

		titleSearch->draw();
		graphics::drawText(11, 20, 20, "Search  by Title:", brr);
		graphics::drawText(15, 48, 15, title_string, brr);

		directorSearch->draw();
		graphics::drawText(11, 80, 20, "Search  by Director:", brr);
		graphics::drawText(15, 108, 15, director_string, brr);

		starSearch->draw();
		graphics::drawText(11, 140, 20, "Search  by Star:", brr);
		graphics::drawText(15, 168, 15, star_string, brr);
	}

	
	//of the bar is moved the selected movie(index selec) changes
	//then the new selected movie is being displayed
	//thats because 
	if (bar) {
		if (active_movies > 7) {
			selec = div(bar->get_bar_center_y(), 450 / active_movies).quot-1 ;
		}
		else if (active_movies > 0) {
			selec = div(bar->get_bar_center_y(), 450 / active_movies).quot;
		}
		
	}
	else {
		selec = 0;
	}
	if (selec > active_movies) {
		selec = active_movies;
	}
	if (selec < 0) {
		selec = 0;
	}

	//Draw the movie details
	current_movie = active_movies_list[selec];
	if (active_movies != 0) {
		current_movie.draw();
		graphics::drawText(550, 52, 40, current_movie.get_title(), brr);
		graphics::drawText(550, 100, 25, "Director:  " + current_movie.get_director(), brr);
		graphics::drawText(550, 150, 25, "Stars:  " + current_movie.get_cast(), brr);
		graphics::drawText(550, 200, 25, "Year:  " + current_movie.get_production_year(), brr);
		graphics::drawText(550, 250, 25, "Categories: " + current_movie.get_categories(), brr);
	}
}

void FilmBrowser::init()
{
	//initialization of the movies on the array
	
	graphics::preloadBitmaps(ASSET_PATH);
	graphics::setFont(std::string(ASSET_PATH) + "font1.ttf");

	movie_collection[0] = Movie("The Godfather", std::string(ASSET_PATH) + "godfather.png", "Francis Ford Coppola", "Marlon Brando, Al Pacino", "Crime, Drama", "1972", "175");
	movie_collection[1] = Movie("The Godfather Part II", std::string(ASSET_PATH) + "godfather2.png", "Francis Ford Coppola", "Robert De Niro, Al Pacino", "Crime, Drama", "1974", "202");
	movie_collection[2] = Movie("Inception", std::string(ASSET_PATH) + "inception.png", "Christopher Nolan", "Leonardo Dicaprio, Elliot Page", "Action, Adventure, SciFi", "2010", "158");
	movie_collection[3] = Movie("Independence Day", std::string(ASSET_PATH) + "indepedenceday.png", "Roland Emmerich", "Will Smith, Jeff Goldblum", "Action, SciFi", "1996", "145");
	movie_collection[4] = Movie("The Shawshank Redemption", std::string(ASSET_PATH) + "shawshank.png", "Frank Darabont", "Tim Robbins, Morgan Freeman", "Drama", "1994", "142");
	movie_collection[5] = Movie("Good Will Hunting", std::string(ASSET_PATH) + "goodwillhunting.png", "Gus Van Sant", "Robin Williams, Matt Damon", "Drama, Romance", "1997", "126");
	movie_collection[6] = Movie("Schindler's List", std::string(ASSET_PATH) + "schindlerslist.png", "Steven Spielberg", "Liam Neeson, Ralph Fiennes", "Drama, History", "1993", "195"); 
	movie_collection[7] = Movie("The Fellowship of the Ring", std::string(ASSET_PATH) + "lotr1.png", "Peter Jackson", "Elijah Wood, Ian McKellen", "Action, Adventure, Fantasy", "2001", "198");
	movie_collection[8] = Movie("The Two Towers", std::string(ASSET_PATH) + "lotr2.png", "Peter Jackson", "Elijah Wood, Ian McKellen", "Action, Adventure, Fantasy", "2002", "199");
	movie_collection[9] = Movie("The Return of the King", std::string(ASSET_PATH) + "lotr3.png", "Peter Jackson", "Elijah Wood, Ian McKellen", "Action, Adventure, Fantasy", "2003", "201");
	movie_collection[10] = Movie("The Dark Knight", std::string(ASSET_PATH) + "darkknight.png", "Christopher Nolan", "Christian Bale, Heath Ledger", "Action, Crime", "2008", "152");
	movie_collection[11] = Movie("Pulp Fiction", std::string(ASSET_PATH) + "pulpfiction.png", "Quentin Tarantino", "John Travolta, Uma Thurman", "Crime, Drama", "1994", "154");
	movie_collection[12] = Movie("Forrest Gump", std::string(ASSET_PATH) + "forrestgump.png", "Robert Zemeckis", "Tom Hanks, Robin Wright", "Drama, Comedy", "1994", "142");
	movie_collection[13] = Movie("Fight Club", std::string(ASSET_PATH) + "fightclub.png", "David Fincher", "Brad Pitt, Edward Norton","Drama", "1999", "139");
	movie_collection[14] = Movie("The Empire Strikes Back", std::string(ASSET_PATH) + "starwars5.png", "Irvin Kershner", "Mark Hamil, Carrie Fisher", "Action, Adventure, SciFi", "1980", "124");
	movie_collection[15] = Movie("The Matrix", std::string(ASSET_PATH) + "matrix.png", "Wachowski Siblings", "Keanu Reeves, Laurence Fishburne", "Action, SciFi", "1999", "136");
	movie_collection[16] = Movie("Goodfellas", std::string(ASSET_PATH) + "goodfellas.png", "Martin Scorsese", "Robert De Niro, Ray Liotta", "Crime, Drama", "1990", "145");
	movie_collection[17] = Movie("Seven", std::string(ASSET_PATH) + "seven.png", "David Fincher", "Brad Pitt, Morgan Freeman", "Crime, Drama", "1995", "127");
	movie_collection[18] = Movie("Terminator 2", std::string(ASSET_PATH) + "terminator2.png", "James Cameron", "Arnold Schwarzenegger, Linda Hamilton", "Action, SciFi", "1991", "137");
	movie_collection[19] = Movie("Django Unchained", std::string(ASSET_PATH) + "django.png", "Quentin Tarantino", "Jamie Foxx, Christoph Waltz", "Action, Drama", "2012", "165");
	
	for (int i = 0; i < 20; i++) {
		active_movies_list[i] = movie_collection[i];
	}

	brr.outline_opacity = 0.0f;
	brr.fill_color[0] = 0.9f;
	brr.fill_color[1] = 0.9f;
	brr.fill_color[2] = 0.9f;
	
	br.outline_opacity = 0.0f;
	br.fill_color[0] = 0.1f;
	br.fill_color[1] = 0.0f;
	br.fill_color[2] = 0.3f;

}

void FilmBrowser::search_categories(std::string category)
{
	active_movies = 0;
	for (Movie mov : movie_collection) {
		if (mov.get_categories().find(category) != std::string::npos) {
			active_movies_list[active_movies] = mov;
			active_movies++;
		}
	}
	if (active_movies != 0) {
		bar->update(450 / active_movies);
	}
}
 
void FilmBrowser::search_year(int yearFrom, int yearTo) {
	
	active_movies = 0;
	int mov_year;
	for (Movie mov : movie_collection) {
		mov_year = stoi(mov.get_production_year());
		if (mov_year >= yearFrom && mov_year <= yearTo) {
			active_movies_list[active_movies] = mov;
			active_movies++;
		}
	}
	if (active_movies!=0){
		bar->update(450 / active_movies);
	}
}
void FilmBrowser::search_string(std::string searchWord, int select) {
	std::string selection;
	active_movies = 0;
	for (Movie mov : movie_collection) {
		if (select == 0) {
			selection = mov.get_title();
		}
		else if (select == 1) {
			selection = mov.get_director();
		}
		else {
			selection = mov.get_cast();
		}
		std::transform(selection.begin(), selection.end(), selection.begin(), ::toupper);
		if (selection.find(searchWord) != std::string::npos) {
			active_movies_list[active_movies] = mov;
			active_movies++;
		}
	}
	if (active_movies != 0) {
		bar->update(450 / active_movies);
	}
}

FilmBrowser::FilmBrowser()
{
}

FilmBrowser::~FilmBrowser()
{
}
