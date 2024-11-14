#include "Parser.h"

InputParser::InputParser(int argc, char **argv) : argc(argc), argv(argv) {};
std::string InputParser::getConfigFileName() const { return configFileName; }
ConfigParser::ConfigParser(const std::string& confgFileName): configFileName(confgFileName){};

bool InputParser::parse()
{
    if (argc < 2)
    {
        std::cout << "Usage: sound_processor [-h] [-c config.txt output.wav input1.wav [input2.wav …]]\n";
        helpShown = true;
        return false;
    }
    for (size_t i = 1; i < argc; ++i)
    {
        if (std::strcmp(argv[i], "-h") == 0)
        {
            std::cerr << "Usage: sound_processor [-h] [-c config.txt output.wav input1.wav [input2.wav …]]" << std::endl;
            helpShown = true;
            return false;
        }
        if (std::strcmp(argv[i], "-c") == 0)
        {
            if (i + 3 < argc)
            {
                configFileName = argv[i + 1];
                outputFileName = argv[i + 2];
                for (size_t j = i + 2; j < argc; ++j)
                {
                    inputFileNames.push_back(argv[j]);
                }
                return true;
            }
            else
            {
                std::cerr << "Error: Invalid arguments for -c option" << std::endl;
                helpShown = true;
                return false;
            }
        }
    }

    std::cerr << "Error: Missing required arguments." << std::endl;
    helpShown = true;
    return false;
}

std::vector<Command> ConfigParser::Parse(){
    std::vector<Command> Commands;
    std::ifstream config(configFileName);
    if(!config.is_open()){
        std::cerr << "Error: Could not open config file " << configFileName << ".\n";
        return Commands;
    }
    std::string line;
    while(std::getline(config, line)){
        if(line.empty()||line[0] == '#'){
            continue;
        }
        std::istringstream iss(line);
        Command command;
        iss >> command.name;
        std::string param;
        while(iss >> param){
            command.parameters.push_back(param);
        }
        Commands.push_back(command);
    }
    config.close();
    return Commands;
}

