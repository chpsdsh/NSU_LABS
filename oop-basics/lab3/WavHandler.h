#include <iostream>
#include <fstream>
#include <vector>
#include <cstdint>
#include <stdexcept>
#include <cstring>

class WavHandler
{
public:
    WavHandler(const std::string &fileName);
    WavHandler(const std::string& fileName, std::vector<short int>& samples, int sampleRate);
    ~WavHandler() = default;

    bool wavSave();
    bool wavLoad();
    std::vector<short int>& getSamples()  { return samples; }
    int getSampleRate() const { return sampleRate; }
    void setSamples(std::vector<short int>& samples) { samples = samples;}
    void setSampleRate(int sampleRate) { sampleRate = sampleRate;}


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