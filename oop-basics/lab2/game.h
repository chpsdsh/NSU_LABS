#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>
#include "cell.h"
#include "commandParser.h"
#include <sstream>
#include <set>

class Game
{
public:
    Game(int fieldSize);
    ~Game();
    void randomUniverse();
    bool isCorrectRule(const std::string &rule);
    bool gamePreparation(const CommandParser &parser);
    bool checkInput(const CommandParser &parser);
    [[nodiscard]] int aliveNeighbours(int x, int y);
    void iterate(const size_t iterations);
    void run();
    void visualize() const;
    bool commandProcessing(const std::string &command);
    [[nodiscard]] std::vector<std::vector<Cell>> &getGameField();
    [[nodiscard]] std::string getRule() const;
    [[nodiscard]] std::string getUnverseName() const;
    [[nodiscard]] size_t getFieldSize() const;

private:
    size_t fieldSize;
    size_t iteration;
    std::string universeName;
    std::string rule;
    std::vector<std::vector<Cell>> gameField;
    friend std::istream &operator>>(std::istream &is, Game &game);
    friend std::ostream &operator<<(std::ostream &os, Game &game);
};