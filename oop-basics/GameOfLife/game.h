#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>
#include "cell.h"
#include "commandParser.h"
#include <sstream>
#include <set>

class Universe
{
public:
    Universe(size_t fieldSize);
    ~Universe();
    void randomUniverse();
    [[nodiscard]] int aliveNeighbours(int x, int y);
    [[nodiscard]] std::string getUnverseName() const;
    [[nodiscard]] size_t getFieldSize() const;
    [[nodiscard]] std::vector<std::vector<Cell>> &getGameField();
    [[nodiscard]] std::string getRule() const;
    bool isCorrectRule(const std::string &rule);
    void changeGameField(std::vector<std::vector<Cell>> newGameFied);

private:
    size_t fieldSize;
    std::string universeName;
    std::string rule;
    std::vector<std::vector<Cell>> gameField;
    friend std::istream &operator>>(std::istream &is, Universe &universe);
    friend std::ostream &operator<<(std::ostream &os, Universe &universe);
};

class Game
{
public:
    Game(Universe& universe);
    ~Game();

    bool gamePreparation(const CommandParser &parser);
    bool checkInput(const CommandParser &parser);
    void iterate(const size_t iterations);
    void run();
    void visualize();
    bool commandProcessing(const std::string &command);

private:
    size_t iteration;
    Universe universe;
};

