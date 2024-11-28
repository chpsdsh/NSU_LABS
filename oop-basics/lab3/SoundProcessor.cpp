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
    inputFile.wavLoad();
    ConfigParser configParser(inputParser);
    if(!configParser.parse()){
        std::cerr << "No input files";
        return false;
    }
    std::vector<short int> samples = inputFile.getSamples();
    for (const auto &converter : configParser.getAudioConverters())
    {
        std::cout<<"Applying converter"<<std::endl;
        converter->apply(samples);
    }
    
    //for(int i = 10; i < samples.size()-1; ++i){
    //    std::cout<<samples[i]<<std::endl;
    //}
    //std::cout<<inputParser.getOutputFileName()<<samples.size()<< inputFile.getSampleRate()<<std::endl;
    WavHandler outputFile(inputParser.getOutputFileName(),samples,inputFile.getSampleRate());
    std::cout<<"penis"<<std::endl;

    //outputFile.wavLoad();
    return true;
}