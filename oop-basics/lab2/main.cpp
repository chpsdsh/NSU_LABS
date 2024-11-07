#include <iostream>
#include "commandParser.h"
#include "cell.h"
#include "game.h"

int main(int argc, char *argv[])
{
    CommandParser parser;
    if (parser.parseCommand(argc, argv) == 0)
    {
        if (parser.helpRequested())
        {
            return 0;
        }
        else
        {
            std::cerr << "Error parsing console" << std::endl;
            return 0;
        }
    }
    Game(25);

    
    return 0;
}