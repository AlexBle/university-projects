#pragma once
#include "graphics.h"
#include "Widget.h"

class Button: public Widget {
protected:
	graphics::Brush br;
	bool state = 0;
public:
	void update(bool new_state);
	void draw() override;
	void init(int size_ver , int size_hor, int pos_vert, int pos_hor);
	bool inBounds();
	bool pressButton();
	Button();
	~Button();
};