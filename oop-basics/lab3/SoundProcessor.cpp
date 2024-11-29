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
    std::cout << inputParser.getInputFileNames()[0] << std::endl;
    if (!inputFile.wavLoad())
    {
        std::cerr << "No input files";
        return false;
    }
    ConfigParser configParser(inputParser);
    if (!configParser.parse())
    {
        std::cerr << "No input files";
        return false;
    }
    std::vector<short int> samples = inputFile.getSamples();
    for (const auto &converter : configParser.getAudioConverters())
    {
        std::cout << "Applying converter" << std::endl;
        converter->apply(samples);
    }
    // std::cout<<samples.size()<<std::endl;
    // for(int i = 1000; i < 1400; i++){
    //     std::cout<<samples[i]<<" "<<i<<std::endl;
    // }
    std::cout << samples.size() << std::endl;

    WavHandler outputFile(inputParser.getOutputFileName(), samples, inputFile.getSampleRate());

    std::cout << "hueta" << samples.size() << outputFile.getSamples().size() << std::endl;
    if (!outputFile.wavSave())
    {
        std::cerr << "No input files";
        return false;
    }
    else
    {
        std::cout << "WAV file saved successfully" << std::endl;
    }
    return true;
}