#pragma once
#include "graphics.h"
#include "Button.h"
#include <Windows.h>  
#include "config.h"

class TextBox : public Button {
public:
	void draw();
	bool inBounds();
	void pressTextBox();
	bool getState();
	std::string charSearch(std::string searchWord);
	TextBox();
	~TextBox();
};