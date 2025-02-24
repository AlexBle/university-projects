#include "Bar.h"
#include "graphics.h"

void Bar::update(int b_p_v, bool new_state) //update method for when the bar is cklicked 
{
	bar_pos_vertical = b_p_v;
	state = new_state;
}

void Bar::update(int new_bar_size_vertical) //update method to change the vertical position of the bar
{
	bar_size_vertical = new_bar_size_vertical;
}

void Bar::draw() //method for making the 2 bars
{
	br2.outline_opacity = 0.0f;
	br2.fill_color[0] = 0.1f;
	br2.fill_color[1] = 0.1f;
	br2.fill_color[2] = 0.1f;

	br.outline_opacity = 0.0f;
	br.fill_color[0] = 0.3f;
	br.fill_color[1] = 0.3f;
	br.fill_color[2] = 0.3f;
	if (state) {
		br.fill_color[0] = 0.5f;
		br.fill_color[1] = 0.5f;
		br.fill_color[2] = 0.5f;
	}
	
	graphics::drawRect(pos_horizontal, pos_vertical, size_horizontal, size_vertical, br2);//secondary bar
	graphics::drawRect(bar_pos_horizontal, bar_pos_vertical, bar_size_horizontal, bar_size_vertical, br);//primary scroll bar
	//it is being drawn on top of the secondary bar
}

void Bar::init(int center_x, int center_y, int width, int height, int bar_center_x, int bar_center_y, int bar_width, int bar_height)
{
	//initializations of the 2 bars
	//secondary bar
	pos_horizontal = center_x;
	pos_vertical = center_y;
	size_horizontal = width;
	size_vertical = height;
	//primal bar
	bar_pos_horizontal = bar_center_x;
	bar_pos_vertical = bar_center_y;
	bar_size_horizontal = bar_width;
	bar_size_vertical = bar_height;
}

int Bar::getBarVert() {
	return bar_pos_vertical;
}

int Bar::getBarHor()
{
	return bar_pos_horizontal;
}

bool Bar::mouseOnBar() 
{
	//checks the position of the mouse and returns True if it is on top of the primal bar
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	return graphics::windowToCanvasY(mouse.cur_pos_y) <= bar_pos_vertical + bar_size_vertical/2 && graphics::windowToCanvasY(mouse.cur_pos_y) >= bar_pos_vertical - bar_size_vertical/2 && graphics::windowToCanvasX(mouse.cur_pos_x) <= bar_pos_horizontal + bar_size_horizontal/2 && graphics::windowToCanvasX(mouse.cur_pos_x) >= bar_pos_horizontal - bar_size_horizontal/2;
}

bool Bar::inBoundsUp()
{
	//checks if the primal bar is at its upper bounds as a scroll bar
	return (bar_pos_vertical - bar_size_vertical / 2 >= pos_vertical - size_vertical/2 );
}

bool Bar::inBoundsDown()
{
	//checks if the primal bar is at its upper bounds as a scroll bar
	return (bar_pos_vertical + bar_size_vertical / 2 <= pos_vertical + size_vertical/2 );
}

void Bar::scrollBar(Bar* bar) //The actual implementation of the scloll with the mouse
{
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	if (mouse.dragging && ((bar->mouseOnBar()) || state)) {//if 
		if (bar->inBoundsDown() && bar->inBoundsUp()) {
			//So that the primal bar moves up and doewn along with the mouse
			//even if after the click the mouse is not on top of the primal bar
			bar->update(graphics::windowToCanvasY(mouse.cur_pos_y,1), 1);
		}
		else if (bar->inBoundsDown()) {
			//So that the primal bar stays in its lower bound no matter what the mouse does
			bar->update(bar->getBarVert() + 1, 1);
		}
		else {
			//So that the primal bar stays in its upper bound no matter what the mouse does
			bar->update(bar->getBarVert() - 1, 1);
		}
	}
	else {
		//else do nothing
		bar->update(bar->getBarVert(), 0);
	}
}

int Bar::get_bar_center_y()
{
	return bar_pos_vertical;
}

Bar::Bar()
{
}

Bar::~Bar()
{
}
