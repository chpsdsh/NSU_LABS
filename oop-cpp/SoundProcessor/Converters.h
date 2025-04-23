#pragma once
#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <memory>
#include <stdexcept>
#include <cstdint>
#include "WavHandler.h"

struct Command
{
    std::string name;
    std::vector<std::string> argv;
};

class Converter
{
public:
    virtual ~Converter() = default;
    virtual void apply(std::vector<short int> &samples) = 0;
};

class MuteConverter : public Converter
{
public:
    MuteConverter(int startSec, int endSec);
    void apply(std::vector<short int> &samples) override;

private:
    int startSec;
    int endSec;
};

class MixConverter : public Converter
{
public:
    MixConverter(const std::string &fileToMix, int startSec);
    void apply(std::vector<short int> &samples) override;

private:
    std::string fileToMix;
    int startSec;
};

class DistortionConverter : public Converter
{
public:
    DistortionConverter(short int threshold, int startSec, int endSec);
    void apply(std::vector<short int> &samples) override;

private:
    short int threshold;
    int startSec;
    int endSec;
};

class ConverterFactory
{
public:
    static std::unique_ptr<Converter> createConverter(Command &command);
};