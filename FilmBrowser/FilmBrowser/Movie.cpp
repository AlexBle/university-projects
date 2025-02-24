#include "Movie.h"
#include "config.h"

void Movie::update()
{
}

void Movie::draw()
{
	graphics::Brush br;
	br.texture = get_image_path();
	br.outline_opacity = 0.0f;
	graphics::drawRect(375,250,300,450,br);
}

void Movie::init()
{
}

Movie::Movie()
{
}

Movie::Movie(std::string title, std::string image_path, std::string director, std::string cast, std::string categories, std::string production_year, std::string length)
{
	this->title = title;
	this->image_path = image_path;
	this->director = director;
	this->cast = cast;
	this->categories = categories;
	this->length = length;
	this->production_year = production_year;
}

Movie::~Movie()
{
}

std::string Movie::get_title()
{
	return title;
}

std::string Movie::get_image_path()
{
	return image_path;
}

std::string Movie::get_director()
{
	return director;
}

std::string Movie::get_cast()
{
	return cast;
}


std::string Movie::get_categories()
{
	return categories;
}

std::string Movie::get_production_year()
{
	return production_year;
}

std::string Movie::get_length()
{
	return length;
}

bool Movie::showMovie()
{
	return show;
}
