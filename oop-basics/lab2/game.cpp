#include "game.h"

Game::Game(Universe& universe) :  iteration(0), universe(universe){}
Game::~Game() = default;
Universe::Universe(size_t fieldSize) : fieldSize(fieldSize),  gameField(fieldSize, std::vector<Cell>(fieldSize)) {}
Universe::~Universe() = default;

std::vector<std::vector<Cell>> &Universe::getGameField() { return gameField; }
std::string Universe::getRule() const { return rule; }
std::string Universe::getUnverseName() const { return universeName; }
size_t Universe::getFieldSize() const { return fieldSize; }

void Universe::changeGameField(std::vector<std::vector<Cell>> newGameFied){
    gameField = newGameFied;
}


void Universe::randomUniverse()
{
    for (size_t x = 0; x < fieldSize; ++x)
    {
        for (size_t y = 0; y < fieldSize; ++y)
        {
            gameField[x][y].setState(rand() % 2 == 0);
        }
    }
}

bool Universe::isCorrectRule(const std::string &rule)
{
    if (rule[0] != 'B' || rule.find('/') == std::string::npos)
    {
        return false;
    }
    size_t slashPos = rule.find('/');
    std::string birthPart = rule.substr(1, slashPos - 1);
    std::string survivalPart = rule.substr(slashPos + 2);

    std::set<char> birthDigits;
    for (char ch : birthPart)
    {
        if (!std::isdigit(ch) || ch < '0' || ch > '8' || !birthDigits.insert(ch).second)
        {
            return false;
        }
    }

    std::set<char> survivalDigits;
    for (char ch : survivalPart)
    {
        if (!std::isdigit(ch) || ch < '0' || ch > '8' || !survivalDigits.insert(ch).second)
        {
            return false;
        }
    }

    return true;
}

bool Game::checkInput(const CommandParser &parser)
{
    if (parser.getInputFile() == "none")
    {
        std::cout << "No input file. Generating random universe." << std::endl;
        universe.randomUniverse();
    }
    else
    {
        std::ifstream inputFile(parser.getInputFile());
        if (!inputFile.is_open())
        {
            std::cerr << "Could not open input file" << std::endl;
            return false;
        }
        inputFile >> universe;
        if (!inputFile)
        {
            std::cerr << "Failed while loading universe from file" << std::endl;
            return false;
        }
    }
    return true;
}

bool Game::gamePreparation(const CommandParser &parser)
{
    if (parser.getIterationsNumber() >= 0)
    {
        if (parser.offlineMode())
        {
            if (!(*this).checkInput(parser))
            {
                return false;
            }
            if (parser.getOutputFile() == "none")
            {
                std::cerr << "No output file" << std::endl;
                return false;
            }
        }
        else
        {
            if (!(*this).checkInput(parser))
            {
                return false;
            }
        }
    }
    else
    {
        std::cerr << "Number of iterations mast be >= 0" << std::endl;
        return false;
    }
    std::cout << "Universe: " << universe.getUnverseName() << std::endl;
    std::cout << "Rule: " <<  universe.getRule() << std::endl;
    std::cout << "Game prepared successfully!" << std::endl;
    return true;
}

int Universe::aliveNeighbours(int x, int y)
{
    int aliveNeighbours = 0;
    for (int cx = -1; cx <= 1; ++cx)
    {
        for (int cy = -1; cy <= 1; ++cy)
        {
            if (cx == 0 && cy == 0)
                continue;
            int neighbourX = (x + cx + fieldSize) % fieldSize;
            int neighbourY = (y + cy + fieldSize) % fieldSize;
            if (gameField[neighbourX][neighbourY].isAlive())
            {
                ++aliveNeighbours;
            }
        }
    }
    return aliveNeighbours;
}

void Game::iterate(const size_t iterations)
{
    size_t slashPos = universe.getRule().find('/');
    std::string birthPart = universe.getRule().substr(1, slashPos - 1);
    std::string survivalPart = universe.getRule().substr(slashPos + 2);

    std::set<int> birthDigits;
    std::set<int> survivalDigits;
    for (char ch : birthPart)
    {
        birthDigits.insert(ch - '0');
    }
    for (char ch : survivalPart)
    {
        survivalDigits.insert(ch - '0');
    }

    for (int i = 0; i < iterations; ++i)
    {
        std::vector<std::vector<Cell>> nextGameField(universe.getFieldSize(), std::vector<Cell>(universe.getFieldSize()));
        for (int x = 0; x < universe.getFieldSize(); ++x)
        {
            for (int y = 0; y < universe.getFieldSize(); ++y)
            {
                int aliveNeighbours = universe.aliveNeighbours(x, y);
                bool nextState = false;

                if (universe.getGameField()[x][y].isAlive())
                {
                    nextState = (survivalDigits.count(aliveNeighbours) > 0);
                }
                else
                {
                    nextState = (birthDigits.count(aliveNeighbours) > 0);
                }

                nextGameField[x][y].setState(nextState);
            }
        }
        universe.changeGameField(nextGameField);
        visualize();
        std::cout << "\n";
    }
}

void Game::visualize() 
{
    for (size_t x = 0; x < universe.getFieldSize(); ++x)
    {
        for (size_t y = 0; y < universe.getFieldSize(); ++y)
        {
            std::cout << (universe.getGameField()[x][y].isAlive() ? '*' : '.');
        }
        std::cout << '\n';
    }
}

void Game::run()
{
    std::string command;
    visualize();
    while (true)
    {
        std::cout << "Enter command (tick, dump, exit, help): ";
        std::getline(std::cin, command);

        if (!commandProcessing(command))
        {
            break;
        }
    }
}

bool Game::commandProcessing(const std::string &command)
{
    std::istringstream iss(command);
    std::string com;
    iss >> com;
    if (com == "tick" || com == "t")
    {
        int iterations = 1;
        if (iss >> iterations)
        {
            iterate(iterations);
        }
        else
        {
            iterate(1);
        }
    }
    else if (com == "dump")
    {
        std::string fileName;
        if (iss >> fileName)
        {
            std::ofstream outputFile(fileName);
            if (!outputFile.is_open())
            {
                std::cerr << "Can not open file" << std::endl;
            }
            else
            {
                outputFile << universe;
            }
        }
        else
        {
            std::cerr << "Filename not provided" << std::endl;
        }
    }
    else if (com == "exit")
    {
        return false;
    }
    else if (com == "help")
    {
        std::cout << "Available commands:\n"
                  << "  tick <n=1> - Perform n iterations (default is 1)\n"
                  << "  dump <filename> - Save the current state to a file\n"
                  << "  exit - End the game\n"
                  << "  help - Show this help message\n";
    }
    else
    {
        std::cerr << "No such command" << std::endl;
    }
    return true;
}

std::ostream &operator<<(std::ostream &os, Universe& universe)
{
    os << "#Life 1.06\n";
    os << "#N " << universe.universeName << "\n";
    os << "#R " << universe.rule << "\n";
    for (size_t y = 0; y < universe.fieldSize; ++y)
    {
        for (size_t x = 0; x < universe.fieldSize; ++x)
        {
            if (universe.gameField[y][x].isAlive())
            {
                os << x << " " << y << "\n";
            }
        }
    }
    return os;
}
std::istream &operator>>(std::istream &is, Universe& universe)
{
    std::string line;
    bool firstline = false;
    while (std::getline(is, line))
    {
        if (line.empty())
            continue;
        if (line[0] == '#')
        {
            if (line != "#Life 1.06" && !firstline)
            {
                is.setstate(std::ios::failbit);
                return is;
            }
            firstline = true;

            if (line.rfind("#N ", 0) == 0)
            {
                universe.universeName = line.substr(3);
            }
            else if (line.rfind("#R ", 0) == 0)
            {
                universe.rule = line.substr(3);
                if (!universe.isCorrectRule(universe.rule))
                {
                    is.setstate(std::ios::failbit);
                    return is;
                }
            }
        }
        else
        {
            std::istringstream coordStream(line);
            int x, y;
            if (coordStream >> x >> y)
            {
                if (x >= 0 && x < universe.fieldSize && y >= 0 && y < universe.fieldSize)
                {
                    universe.gameField[x][y].setState(true);
                }
                else
                {
                    std::cerr << "Warning: Coordinates out of bounds: " << x << ", " << y << std::endl;
                }
            }
            else
            {
                is.setstate(std::ios::failbit);
                return is;
            }
        }
    }
    if (universe.universeName.empty())
    {
        universe.universeName = "Unnamed";
    }
    if (universe.rule.empty())
    {
        universe.rule = "B3/S23";
    }
    is.clear();
    return is;
}