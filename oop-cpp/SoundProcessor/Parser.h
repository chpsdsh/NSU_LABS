#pragma once
#include <iostream>
#include <vector>
#include <string>
#include <cstring>
#include <fstream>
#include <sstream>
#include <memory>
#include "Converters.h"

class InputParser
{
public:
    InputParser(int argc, char **argv);
    bool parse();
    std::string getConfigFileName() const;
    std::string getOutputFileName() const;
    std::vector<std::string> getInputFileNames() const;
    bool getHelp() const;

private:
    int argc;
    char **argv;
    std::string configFileName;
    std::string outputFileName;
    std::vector<std::string> inputFileNames;
    bool helpShown = false;
};

class ConfigParser
{
public:
    ConfigParser(const InputParser &inputParser);
    bool parse();
    void processCommand(Command &command);
    std::vector<std::unique_ptr<Converter>> getAudioConverters();

private:
    const InputParser &inputParser;
    std::vector<std::unique_ptr<Converter>> audioConverters;
};
