#include "game.h"

Game::Game(int fieldSize) : fieldSize(fieldSize), iteration(0), gameField(fieldSize, std::vector<Cell>(fieldSize)) {}
Game::~Game() = default;

std::vector<std::vector<Cell>>& Game::getGameField() { return gameField; }
std::string Game::getRule() const { return rule; }
std::string Game::getUnverseName() const { return universeName; }
size_t Game::getFieldSize() const { return fieldSize; }

void Game::randomUniverse()
{
    for (size_t x = 0; x < fieldSize; ++x)
    {
        for (size_t y = 0; y < fieldSize; ++y)
        {
            gameField[x][y].setState(rand() % 2 == 0);
        }
    }
}

bool Game::isCorrectRule(const std::string &rule)
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
        randomUniverse();
    }
    else
    {
        std::ifstream inputFile(parser.getInputFile());
        if (!inputFile.is_open())
        {
            std::cerr << "Could not open input file" << std::endl;
            return false;
        }
        inputFile >> *this;
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
    std::cout << "Universe: " << universeName << std::endl;
    std::cout << "Rule: " << rule << std::endl;
    std::cout << "Game prepared successfully!" << std::endl;
    return true;
}

int Game::aliveNeighbours(int x, int y)
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
    for (int i = 0; i < iterations; ++i)
    {
        std::vector<std::vector<Cell>> nextGameField(fieldSize, std::vector<Cell>(fieldSize));
        for (int x = 0; x < fieldSize; ++x)
        {
            for (int y = 0; y < fieldSize; ++y)
            {
                int aliveNeighbours = (*this).aliveNeighbours(x, y);
                bool nextState;
                if (gameField[x][y].isAlive())
                {
                    nextState = (aliveNeighbours == 2 || aliveNeighbours == 3);
                }
                else
                {
                    nextState = (aliveNeighbours == 3);
                }
                nextGameField[x][y].setState(nextState);
            }
        }
        gameField = nextGameField;
    }
}

void Game::visualize() const
{
    for (size_t x = 0; x < fieldSize; ++x)
    {
        for (size_t y = 0; y < fieldSize; ++y)
        {
            std::cout << (gameField[x][y].isAlive() ? '*' : '.');
        }
        std::cout << '\n';
    }
}

void Game::run()
{
    std::string command;
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
        visualize();
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
                outputFile << *this;
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
std::ostream &operator<<(std::ostream &os, Game &game)
{
    os << "#Life 1.06\n";
    os << "#N " << game.universeName << "\n";
    os << "#R " << game.rule << "\n";
    for (size_t y = 0; y < game.fieldSize; ++y)
    {
        for (size_t x = 0; x < game.fieldSize; ++x)
        {
            if (game.gameField[y][x].isAlive())
            {
                os << x << " " << y << "\n";
            }
        }
    }
    return os;
}
std::istream &operator>>(std::istream &is, Game &game)
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
                game.universeName = line.substr(3);
            }
            else if (line.rfind("#R ", 0) == 0)
            {
                game.rule = line.substr(3);
                if (!game.isCorrectRule(game.rule))
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
                if (x >= 0 && x < game.fieldSize && y >= 0 && y < game.fieldSize)
                {
                    game.gameField[x][y].setState(true);
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
    if (game.universeName.empty())
    {
        game.universeName = "Unnamed";
    }
    if (game.rule.empty())
    {
        game.rule = "B3/S23";
    }
    is.clear();
    return is;
}