#pragma ones
#include <iostream>
#include <vector>
#include <string>
#include "Parser.h"

class SoundProcessor
{
public:
    SoundProcessor(const InputParser &inputParser);
    bool run();

private:
    const InputParser &inputParser;
};