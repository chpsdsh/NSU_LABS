#include "Converters.h"

MuteConverter::MuteConverter(int startSec, int endSec) : startSec(startSec), endSec(endSec) {};
MixConverter::MixConverter(std::vector<int16_t> &samplesToMix, int startSec) : samplesToMix(samplesToMix), startSec(startSec) {};
DistortionConverter::DistortionConverter(int16_t threshold, int startSec, int endSec) : threshold(threshold),
                                                                                        startSec(startSec), endSec(endSec) {};

void MuteConverter::apply(std::vector<int16_t> &samples)
{
    int startIndex = startSec * 44100 / 1000;
    int endIndex = endSec * 44100 / 1000;

    for (int i = startIndex; i < endIndex && i < samples.size(); ++i)
    {
        samples[i] = 0;
    }
}

void MixConverter::apply(std::vector<int16_t> &samples)
{
    int startIndex = startSec * 44100 / 1000;
    for (int i = 0; i < samplesToMix.size() && (startIndex + i) < samples.size(); ++i)
    {
        samples[startIndex + i] = (samples[startIndex + i] + samplesToMix[i]) / 2;
    }
}

void DistortionConverter::apply(std::vector<int16_t> &samples)
{
    int startIndex = startSec * 44100 / 1000;
    int endIndex = endSec * 44100 / 1000;
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
