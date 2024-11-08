#pragma once
#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>
#include <cstring>
#include <fstream>


class CommandParser
{
private:
    size_t iterationsNumber;
    std::string inputFile;
    std::string outputFile;
    bool helpUsed = false;
    bool mode;
public:
    CommandParser();
    bool parseCommand(int argc, char *argv[]);
    static void requestHelp();
    bool helpRequested() const;
    bool offlineMode() const;
     std::string getInputFile() const;
    std::string getOutputFile() const;
     size_t getIterationsNumber() const;
    
};