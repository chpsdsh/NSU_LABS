#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>
#include "cell.h"
#include "commandParser.h"

class Game{
    private:
        int fieldSize;
        int iteration;
        std::string universeName;
        std::string rule;
        std::vector<std::vector<Cell>> gameField;
        friend std::ostream& operator<<(std::ostream& ostr, Game& game);
        friend std::istream& operator>>(std::istream& istr, Game& game);
    public:
           Game(int fieldSize);
           ~Game();
           bool gamePreparation(const CommandParser& parser);
           void run();
}; 