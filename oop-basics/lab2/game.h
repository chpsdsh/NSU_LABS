#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>

class Game{
    private:
        int fieldSize;
        int iteration;
        std::string universeName;
        std::string rule;
        std::vector<bool> gameField;
};