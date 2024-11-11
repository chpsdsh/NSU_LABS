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
public:
    CommandParser();
    bool parseCommand(int argc, char *argv[]);
    static void requestHelp();
    bool helpRequested() const;
    bool offlineMode() const;
    [[nodiscard]] std::string getInputFile() const;
    [[nodiscard]] std::string getOutputFile() const;
    [[nodiscard]] size_t getIterationsNumber() const;

private:
    size_t iterationsNumber;
    std::string inputFile;
    std::string outputFile;
    bool helpUsed = false;
    bool mode;
};