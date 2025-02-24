#include "Button.h"
#include "graphics.h"

void Button::update(bool new_state)
{
	//True is pressed button and False is not pressed
	//this method updetes the state of the button
	state = new_state;
}

void Button::draw() //draws the button 
{
	br.outline_opacity = 0.0f;
	br.fill_color[0] = 0.3f;
	br.fill_color[1] = 0.0f;
	br.fill_color[2] = 0.6f;
	if (state) {
		br.fill_color[0] = 0.7f;
		br.fill_color[1] = 0.0f;
		br.fill_color[2] = 0.9f;
	}
	graphics::drawRect(pos_horizontal, pos_vertical, size_horizontal, size_vertical, br);
}

void Button::init(int size_ver, int size_hor, int pos_vert, int pos_hor)
{
	size_vertical = size_ver;
	size_horizontal = size_hor;
	pos_vertical = pos_vert;
	pos_horizontal = pos_hor;
}

bool Button::inBounds() {
	//checks if the mouses coordinates are on the button
	//depends if the button was already pressed
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	return mouse.button_left_pressed && graphics::windowToCanvasX(mouse.cur_pos_x) < pos_horizontal + size_horizontal/2 && graphics::windowToCanvasY(mouse.cur_pos_y) < pos_vertical + size_vertical/2 && graphics::windowToCanvasX(mouse.cur_pos_x) > pos_horizontal - size_horizontal/2 && graphics::windowToCanvasY(mouse.cur_pos_y) > pos_vertical - size_vertical/2;
}

bool Button::pressButton()
{
	//if thhe mouse is in bound then it can press the button and change its state
	if (this->inBounds()) {
		this->update(1);
		return 1;
	}
	else {
		this->update(0);
		return 0;
	}
}

Button::Button()
{
	
}

Button::~Button()
{
}


