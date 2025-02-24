#include "TextBox.h"
#include <iostream>
void TextBox::draw()
{
	br.outline_opacity = 0.5f;
	br.fill_color[0] = 0.3f;
	br.fill_color[1] = 0.3f;
	br.fill_color[2] = 0.3f;
	if (state) {
		br.outline_opacity = 0.9f;
	}
	graphics::drawRect(pos_horizontal, pos_vertical, size_horizontal, size_vertical, br);
}

bool TextBox::inBounds() {
	//checks if the mouses coordinates are on the button
	//depends if the button was already pressed
	graphics::MouseState mouse;
	graphics::getMouseState(mouse);
	return (mouse.button_left_pressed && graphics::windowToCanvasX(mouse.cur_pos_x) < pos_horizontal + size_horizontal / 2 && graphics::windowToCanvasY(mouse.cur_pos_y) < pos_vertical + size_vertical / 2 && graphics::windowToCanvasX(mouse.cur_pos_x) > pos_horizontal - size_horizontal / 2 && graphics::windowToCanvasY(mouse.cur_pos_y) > pos_vertical - size_vertical / 2) || state;
}

void TextBox::pressTextBox()
{
	//if thhe mouse is in bound then it can press the button and change its state
	if (this->inBounds()) {
		this->update(1);
	}
}

std::string TextBox::charSearch(std::string searchWord) {
	if (graphics::getKeyState(graphics::SCANCODE_A)) {
		searchWord += "A";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_B)) {
		searchWord += "B";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_C)) {
		searchWord += "C";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_D)) {
		searchWord += "D";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_E)) {
		searchWord += "E";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_F)) {
		searchWord += "F";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_G)) {
		searchWord += "G";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_H)) {
		searchWord += "H";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_I)) {
		searchWord += "I";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_J)) {
		searchWord += "J";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_K)) {
		searchWord += "K";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_L)) {
		searchWord += "L";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_M)) {
		searchWord += "M";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_N)) {
		searchWord += "N";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_O)) {
		searchWord += "O";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_P)) {
		searchWord += "P";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_Q)) {
		searchWord += "Q";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_R)) {
		searchWord += "R";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_S)) {
		searchWord += "S";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_T)) {
		searchWord += "T";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_U)) {
		searchWord += "U";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_V)) {
		searchWord += "V";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_W)) {
		searchWord += "W";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_X)) {
		searchWord += "X";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_Y)) {
		searchWord += "Y";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_Z)) {
		searchWord += "Z";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_SPACE)) {
		searchWord += " ";
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_BACKSPACE)) {
		if (searchWord != "") {
			searchWord.pop_back();
		}
		Sleep(ELAPSE_TIME);
	}
	else if (graphics::getKeyState(graphics::SCANCODE_RETURN)) {
		this->update(0);
		searchWord += "|";
	}
	return searchWord;
	
}

bool TextBox::getState()
{
	return state;
}

TextBox::TextBox()
{
}

TextBox::~TextBox()
{
}
