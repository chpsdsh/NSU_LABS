#include <iostream>
#include "Parser.h"
#include "SoundProcessor.h"

int main(int argc, char *argv[])
{
    InputParser parser(argc, argv);
    if (parser.parse())
    {
        SoundProcessor processor(parser);
        if (!processor.run())
        {
            std::cout << "Error while proocessing file" << std::endl;
        }
    }
    else
    {
        std::cout << "Can not parse input file" << std::endl;
    }
    return 0;
}