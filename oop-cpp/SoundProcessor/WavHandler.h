#include <iostream>
#include <fstream>
#include <vector>
#include <cstdint>
#include <stdexcept>
#include <cstring>
#include "Exceptions.h"

class WavHandler
{
public:
    WavHandler(const std::string &fileName);
    WavHandler(const std::string& fileName, std::vector<short int>& samples, int sampleRate);
    ~WavHandler() = default;

    bool wavSave();
    bool wavLoad();
    std::vector<short int>& getSamples();
    int getSampleRate() const ;
    int getNumChannels() const;
    int getBitsPerSample() const;
    bool getValidFormat() const;

private:
    bool readHeader(std::ifstream& file);
    bool writeHeader(std::ofstream& file);

    std::string fileName;
    int sampleRate;
    int numChannels;
    int bitsPerSample;
    bool validFormat = false;
    std::vector<short int> samples;
};