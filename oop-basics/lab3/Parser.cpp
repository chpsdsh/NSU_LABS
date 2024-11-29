#include "Parser.h"

InputParser::InputParser(int argc, char **argv) : argc(argc), argv(argv) {};
std::string InputParser::getConfigFileName() const { return configFileName; }
std::string InputParser::getOutputFileName() const { return outputFileName; }
std::vector<std::string> InputParser::getInputFileNames() const { return inputFileNames; }
ConfigParser::ConfigParser(const InputParser &inputParser) : inputParser(inputParser) {};
std::vector<std::unique_ptr<Converter>> ConfigParser::getAudioConverters()
{
    return std::move(audioConverters);
}

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
                for (size_t j = i + 3; j < argc; ++j)
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

bool ConfigParser::parse()
{
    std::ifstream config(inputParser.getConfigFileName());
    if (!config.is_open())
    {
        throw ConfigParserError("Can't open config file");
    }
    std::string line;
    while (std::getline(config, line))
    {
        if (line.empty() || line[0] == '#')
        {
            continue;
        }
        std::istringstream iss(line);

        Command command;
        iss >> command.name;
        std::string arg;
        while (iss >> arg)
        {
            command.argv.push_back(arg);
        }
        processCommand(command);
    }
    config.close();
    return true;
}

void ConfigParser::processCommand(Command &command)
{
    try
    {
        if (command.name == "mute")
        {
            auto converter = ConverterFactory::createConverter(command);
            audioConverters.push_back(std::move(converter));
            std::cout << "Mute Converter added" << std::endl;
        }
        else if (command.name == "mix" && command.argv[0][0] == '$')
        {
            std::string numberStr = command.argv[0].substr(1);
            std::string fileToMix = inputParser.getInputFileNames()[std::stoi(numberStr) - 1];
            command.argv[0] = fileToMix;
            auto converter = ConverterFactory::createConverter(command);
            audioConverters.push_back(std::move(converter));
            std::cout << "Mix Converter added" << std::endl;
        }
        else if (command.name == "distortion")
        {
            auto converter = ConverterFactory::createConverter(command);
            audioConverters.push_back(std::move(converter));
            std::cout << "Distortion Converter added" << std::endl;
        }
        else
        {
            throw InvalidCommandError("Unknown converter");
        }
    }
    catch (const std::exception &e)
    {
        std::cerr << "Error processing command: " << e.what() << std::endl;
    }
}
