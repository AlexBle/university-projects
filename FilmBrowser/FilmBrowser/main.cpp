#include "graphics.h"
#include "config.h"
#include "FilmBrowser.h"

void update(float ms) {
	FilmBrowser* browser = reinterpret_cast<FilmBrowser *>(graphics::getUserData());
	browser->update();
}

void draw() {
	FilmBrowser* browser = reinterpret_cast<FilmBrowser*>(graphics::getUserData());
	browser->draw();
}

int main() {
	graphics::createWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Film Browser");

	graphics::setDrawFunction(draw);
	graphics::setUpdateFunction(update);

	graphics::setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
	graphics::setCanvasScaleMode(graphics::CANVAS_SCALE_FIT);
	graphics::Brush br;
	br.fill_color[0] = 0.2f;
	br.fill_color[1] = 0.0f;
	br.fill_color[2] = 0.5f;
	graphics::setWindowBackground(br);

	FilmBrowser browser;
	browser.init();
	graphics::setUserData(&browser);

	graphics::startMessageLoop();
	

	return 0;
}