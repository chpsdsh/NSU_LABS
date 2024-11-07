#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>
#include "cell.h"
#include "commandParser.h"
#include <sstream>
#include <set>

class Game{
    private:
        int fieldSize;
        int iteration;
        std::string universeName;
        std::string rule;
        std::vector<std::vector<Cell>> gameField;
        friend std::istream& operator>>(std::istream& is, Game& game);
        friend std::ostream& operator<<(std::ostream& os, Game& game);
    public:
           Game(int fieldSize);
           ~Game();
           void randomUniverse();
           bool isCorrectRule(const std::string& rule);
           bool gamePreparation(const CommandParser& parser);
           void run();
}; 