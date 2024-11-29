#include "SoundProcessor.h"

SoundProcessor::SoundProcessor(const InputParser &inputParser) : inputParser(inputParser) {}

bool SoundProcessor::run()
{
    if (inputParser.getInputFileNames().empty())
    {
        std::cerr << "No input files";
        return false;
    }

    WavHandler inputFile(inputParser.getInputFileNames()[0]);

    if (!inputFile.wavLoad())
    {
        throw FileReadError("No input files");
    }

    ConfigParser configParser(inputParser);

    if (!configParser.parse())
    {
        throw FileReadError("No input files");
    }

    std::vector<short int> samples = inputFile.getSamples();

    for (const auto &converter : configParser.getAudioConverters())
    {
        std::cout << "Applying converter" << std::endl;
        converter->apply(samples);
    }

    WavHandler outputFile(inputParser.getOutputFileName(), samples, inputFile.getSampleRate());

    if (!outputFile.wavSave())
    {
        throw FileReadError("Can not save file");
    }
    else
    {
        std::cout << "WAV file saved successfully" << std::endl;
    }
    return true;
}