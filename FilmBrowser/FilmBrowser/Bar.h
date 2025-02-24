#pragma once
#include "graphics.h"
#include "Button.h"


class Bar : public Button {
private:
	int bar_size_vertical;
	int bar_size_horizontal;
	int bar_pos_vertical;
	int bar_pos_horizontal;
	graphics::Brush br2;
public:
	void update(int bar_pos_vertical, bool new_state);
	void update(int new_bar_size_vertical);
	void draw();
	void init(int center_x, int center_y, int width, int height, int bar_center_x, int bar_center_y, int bar_width, int bar_height);
	int getBarVert();
	int getBarHor();
	bool mouseOnBar();
	bool inBoundsUp();
	bool inBoundsDown();
	void scrollBar(Bar * bar);
	int get_bar_center_y();
	Bar();
	~Bar();
};