#include "Converters.h"

MuteConverter::MuteConverter(int startSec, int endSec) : startSec(startSec), endSec(endSec) {};
MixConverter::MixConverter(const std::string &fileToMix, int startSec) : fileToMix(fileToMix), startSec(startSec) {};
DistortionConverter::DistortionConverter(short int threshold, int startSec, int endSec) : threshold(threshold),
                                                                                          startSec(startSec), endSec(endSec) {};

void MuteConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100;
    int endIndex = endSec * 44100;
    for (size_t i = startIndex; i < endIndex && i < samples.size(); ++i)
    {
        samples[i] = 0;
    }
}

void MixConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100;
    std::cout << fileToMix << std::endl;
    WavHandler fileMix(fileToMix);
    fileMix.wavLoad();

    std::vector<short int> samplesToMix = fileMix.getSamples();
    for (int i = 0; i < samplesToMix.size() && (startIndex + i) < samples.size(); ++i)
    {
        samples[startIndex + i] = (samples[startIndex + i] + samplesToMix[i]) / 2;
    }
}

void DistortionConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100;
    int endIndex = endSec * 44100;
    for (int i = startIndex; i < endIndex && i < samples.size(); ++i)
    {
        if (samples[i] > threshold)
        {
            samples[i] = threshold;
        }
        else if (samples[i] < -threshold)
        {
            samples[i] = -threshold;
        }
    }
}

std::unique_ptr<Converter> ConverterFactory::createConverter(Command &command)
{
    if (command.name == "mute" && command.argv.size() == 2)
    {
        int startSec = std::stoi(command.argv[0]);
        int endSec = std::stoi(command.argv[1]);
        return std::make_unique<MuteConverter>(startSec, endSec);
    }
    else if (command.name == "mix" && command.argv.size() == 2)
    {
        const std::string &fileToMix = command.argv[0];
        int startSec = std::stoi(command.argv[1]);
        return std::make_unique<MixConverter>(fileToMix, startSec);
    }
    else if (command.name == "distortion" && command.argv.size() == 3)
    {
        short int threshold = std::stoi(command.argv[0]);
        int startSec = std::stoi(command.argv[1]);
        int endSec = std::stoi(command.argv[2]);
        return std::make_unique<DistortionConverter>(threshold, startSec, endSec);
    }
    else
    {
        throw std::invalid_argument("Unknown command or invalid parameters.");
    }
}
