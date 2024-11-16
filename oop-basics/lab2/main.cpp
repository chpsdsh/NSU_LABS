#include <iostream>
#include "commandParser.h"
#include "cell.h"
#include "game.h"

int main(int argc, char *argv[])
{
    CommandParser parser;
    if (!parser.parseCommand(argc, argv))
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
    Universe universe(20);
    Game game(universe);
    
    if (!game.gamePreparation(parser))
    {
        std::cerr << "Failed preparing game" << std::endl;
        return 1;
    }

    if (parser.offlineMode())
    {
        game.iterate(parser.getIterationsNumber());
        std::ofstream outputFile(parser.getOutputFile());

        if (!outputFile.is_open())
        {
            std::cerr << "Could not open output file" << std::endl;
            return 1;
        }
        else
        {
            outputFile << universe;
            std::cout << "Offline mode completed. Data was saved to " << parser.getOutputFile() << std::endl;
        }
    }
    else
    {
        game.run();
    }
    return 0;
}