#pragma once

#include "graphics.h"
#include "Widget.h"
#include "Button.h"
#include "Bar.h"
#include "config.h"
#include "Movie.h"
#include "TextBox.h"
#include <iostream>
#include <Windows.h> 
#include <algorithm>


class FilmBrowser {
	Button* button_drama = nullptr;
	Button* button_comedy = nullptr;
	Button* button_crime = nullptr;
	Button* button_adventure = nullptr;
	Button* button_action = nullptr;
	Button* button_scifi = nullptr;
	Button* button_history = nullptr;
	Button* button_fantasy = nullptr;
	Button* button_reset = nullptr;

	Button* button_bar_up = nullptr;
	Button* button_bar_down = nullptr;
	bool b_init = 0;
	
	Bar* bar = nullptr;
	bool bar_init = 0;

	TextBox* yearFrom = nullptr;
	std::string yearFrom_string = "0";
	int yearFrom_int;
	TextBox* yearTo = nullptr;
	std::string yearTo_string = "2023";
	int yearTo_int;

	TextBox* titleSearch = nullptr;
	std::string title_string;
	TextBox* directorSearch = nullptr;
	std::string director_string;
	TextBox* starSearch = nullptr;
	std::string star_string;

	Movie movie_collection[20];
	Movie current_movie;
	int active_movies = 19;
	int selec;

	Movie active_movies_list[20];

	graphics::Brush brr;
	graphics::Brush brrr;
	graphics::Brush br;

public:
	void update();
	void draw();
	void init();
	void search_categories(std::string category);
	void search_year(int yearFrom, int yearTo);
	void search_string(std::string searchWord, int select); 
	FilmBrowser();
	~FilmBrowser();
};