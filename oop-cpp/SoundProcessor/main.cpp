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
            throw SoundProcessorRunError("Error while running");
        }
    }
    else
    {
        throw ConfigParserError("Configuration Parse Error");
    }
    return 0;
}