#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <memory>
#include <stdexcept>
#include <cstdint>


class Converter
{
public:
    virtual ~Converter() = default;
    virtual void apply(std::vector<int16_t> &samples) = 0;
};

class MuteConverter : public Converter
{
public:
    MuteConverter(int startSec, int endSec);
    void apply(std::vector<int16_t> &samples) override;

private:
    int startSec;
    int endSec;
};

class MixConverter : public Converter
{
public:
    MixConverter(std::vector<int16_t> &samplesToMix, int startSec);
    void apply(std::vector<int16_t> &samples) override;

private:
    std::vector<int16_t> samplesToMix;
    int startSec;
};

class DistortionConverter : public Converter
{
public:
    DistortionConverter(int16_t threshold, int startSec, int endSec);
    void apply(std::vector<int16_t> &samples) override;

private:
    int16_t threshold;
    int startSec;
    int endSec;
};

class ConverterFactory
{
    static std::unique_ptr<Converter> createConverter(const std::string &name, const std::vector<std::string> &parameters);
    static void convertersInfo();
};