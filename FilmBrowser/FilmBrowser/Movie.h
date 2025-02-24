#pragma once
#include "graphics.h"

class Movie {
	std::string title;
	std::string image_path;
	std::string director;
	std::string cast;
	std::string categories;
	std::string production_year;
	std::string length;
	bool show = 1;

public:
	void update();
	void draw();
	void init();
	Movie();
	Movie(std::string title, std::string image_path, std::string director, std::string cast, std::string categories, std::string production_year, std::string length);
	~Movie();
	std::string get_title();
	std::string get_image_path();
	std::string get_director();
	std::string get_cast();
	std::string get_categories();
	std::string get_production_year();
	std::string get_length();
	bool showMovie();
};