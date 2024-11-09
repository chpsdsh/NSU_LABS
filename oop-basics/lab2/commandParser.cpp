#include "commandParser.h"

CommandParser::CommandParser() : iterationsNumber(0), helpUsed(false), inputFile("none"), outputFile("none") {};
bool CommandParser::helpRequested() const { return helpUsed; }
std::string CommandParser::getInputFile() const { return inputFile; }
std::string CommandParser::getOutputFile() const { return outputFile; }
size_t CommandParser::getIterationsNumber() const { return iterationsNumber; }
bool CommandParser::offlineMode() const { return mode; }

void CommandParser::requestHelp()
{
    std::cout << "Usage: <program executable file> [options]" << std::endl;
    std::cout << "The program provides next options calling by the flags:" << std::endl;
    std::cout << "-f\t--file\tFILENAME\tInput file with universe inside." << std::endl;
    std::cout << "-o\t--output\tFILENAME\tOutput file for saving universe." << std::endl;
    std::cout << "-i\t--iterations\tNUMBER\tNumber of iterations to run." << std::endl;
    std::cout << "-m\t--mode\tMODE\tMode of the game (online or offline)." << std::endl;
    std::cout << "-h\t--help\tDisplays this help menu and exit program." << std::endl;
}

bool CommandParser::parseCommand(int argc, char *argv[])
{
    for (size_t i = 1; i < argc; i++)
    {
        if (strcmp(argv[i], "-f") == 0 || strcmp(argv[i], "--file") == 0)
        {
            if (i + 1 < argc)
            {
                inputFile = std::string(argv[++i]);
            }
            else
            {
                std::cerr << "Error: No input filename provided. Use -h or --help." << std::endl;
                return false;
            }
        }

        else if (strcmp(argv[i], "-o") == 0 || strcmp(argv[i], "--output") == 0)
        {
            if (i + 1 < argc)
            {
                outputFile = std::string(argv[++i]);
            }
            else
            {
                std::cerr << "Error: No output filename provided. Use -h or --help." << std::endl;
                return false;
            }
        }

        else if (strcmp(argv[i], "-i") == 0 || strcmp(argv[i], "--iterations") == 0)
        {
            if (i + 1 < argc)
            {
                iterationsNumber = atoi(argv[++i]);
            }
            else
            {
                std::cerr << "Error: No number of iterations provided. Use -h or --help." << std::endl;
                return false;
            }
        }
        else if (strcmp(argv[i], "-m") == 0 || strcmp(argv[i], "--mode") == 0)
        {
            if (i + 1 < argc)
            {
                std::string inputMode = std::string(argv[++i]);
                if (inputMode == "offline")
                {
                    mode = true;
                }
                else if (inputMode == "online")
                {
                    mode = false;
                }
                else
                {
                    std::cerr << "Error: Invalid mode. Use 'offline' or 'online'. Use -h or --help for usage." << std::endl;
                    return false;
                }
            }
            else
            {
                std::cerr << "Error: No mode provided. Use -h or --help." << std::endl;
                return false;
            }
        }
        else if (strcmp(argv[i], "-h") == 0 || strcmp(argv[i], "--help") == 0)
        {
            helpUsed = true;
            requestHelp();
        }
    }
    if (!mode && iterationsNumber == 0)
    {
        iterationsNumber = 1;
    }
    return true;
}
