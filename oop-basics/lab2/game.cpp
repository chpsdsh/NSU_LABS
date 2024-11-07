#include "game.h"

Game::Game(int fieldSize) : fieldSize(fieldSize), iteration(0), gameField(fieldSize, std::vector<Cell>(fieldSize)) {}
Game::~Game() = default;

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
    if (rule[0] != 'B' || rule.find('/S') == std::string::npos)
    {
        return false;
    }
    size_t slashPos = rule.find("/S");
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

bool Game::gamePreparation(const CommandParser &parser)
{
    if (parser.offlineMode())
    {
        if (parser.getInputFile() == "none")
        {
            std::cout << "No input file. Generating random universe." << std::endl;
            randomUniverse();
        }
        else{
            std::ifstream inputFile(parser.getInputFile());
            if(!inputFile.is_open()){
                std::cerr<<"Could not open input file"<<std::endl;
                return false;
            }
        }
    }
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