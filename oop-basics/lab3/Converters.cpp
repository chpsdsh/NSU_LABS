#include "Converters.h"

MuteConverter::MuteConverter(int startSec, int endSec) : startSec(startSec), endSec(endSec) {};
MixConverter::MixConverter(const std::string& fileToMix, int startSec) : fileToMix(fileToMix), startSec(startSec) {};
DistortionConverter::DistortionConverter(short int threshold, int startSec, int endSec) : threshold(threshold),
                                                                                          startSec(startSec), endSec(endSec) {};

void MuteConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100/1000;
    int endIndex = endSec * 44100/1000;
    std::cout<<startIndex<<" "<<endIndex<<" "<<samples.size()<<std::endl;
    for (int i = startIndex; i < endIndex && i < samples.size(); ++i)
    {
        samples[i] = 0;
    }
}

void MixConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100 / 1000;
    WavHandler fileToMix(fileToMix);
    fileToMix.wavLoad();
    std::vector<short int> samplesToMix = fileToMix.getSamples();
    for (int i = 0; i < samplesToMix.size() && (startIndex + i) < samples.size(); ++i)
    {
        samples[startIndex + i] = (samples[startIndex + i] + samplesToMix[i]) / 2;
    }
}

void DistortionConverter::apply(std::vector<short int> &samples)
{
    int startIndex = startSec * 44100;
    int endIndex = endSec * 44100;
    for (int i = startIndex; i < endIndex; ++i)
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

std::unique_ptr<Converter> ConverterFactory::createConverter(Command& command)
{
    if (command.name == "mute" && command.argv.size() == 2)
    {
        int startSec = std::stoi(command.argv[0]); // Начало в секундах
        int endSec = std::stoi(command.argv[1]);   // Конец в секундах
        return std::make_unique<MuteConverter>(startSec, endSec);
    }
    else if (command.name == "mix" && command.argv.size() == 2)
    {
        const std::string& fileToMix = command.argv[0]; // Путь к файлу для смешивания
        int startSec = std::stoi(command.argv[1]);     // Время начала в секундах
        return std::make_unique<MixConverter>(fileToMix, startSec);
    }
    else if (command.name == "distortion" && command.argv.size() == 3)
    {
        short int threshold = std::stoi(command.argv[0]); // Порог для искажения
        int startSec = std::stoi(command.argv[1]);         // Время начала в секундах
        int endSec = std::stoi(command.argv[2]);           // Время конца в секундах
        return std::make_unique<DistortionConverter>(threshold, startSec, endSec);
    }
    else
    {
        throw std::invalid_argument("Unknown command or invalid parameters.");
    }
}
