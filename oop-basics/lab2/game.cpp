#include "game.h"

Game::Game(int fieldSize) : fieldSize(fieldSize), iteration(0), gameField(fieldSize, std::vector<Cell>(fieldSize)) {}
Game::~Game() = default;
