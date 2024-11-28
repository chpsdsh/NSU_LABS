#include <iostream>
#include <fstream>
#include <vector>
#include <cstdint>
#include <stdexcept>
#include <cstring>

class WavHandler
{
public:
    WavHandler(std::string &fileName);
    WavHandler(const std::string& fileName, std::vector<short int>& samples, int sampleRate);
    void wavLoad();
    void wavSave(const std::string &outputFileName);
    std::vector<short int> getSamples() const;
    int getSampleRate() const;
private:
    std::string fileName;
    int sampleRate;
    int numChannels;
    int bitsPerSample;
    std::vector<short int> samples;
    bool validateHeader(std::vector<uint8_t> header);
};