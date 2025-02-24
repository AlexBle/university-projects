#pragma once

class Widget {
protected:
	int size_vertical;
	int size_horizontal;
	int pos_vertical;
	int pos_horizontal;
public:
	virtual void update();
	virtual void draw();
	virtual void init();
};