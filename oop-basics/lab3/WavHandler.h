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
    ~WavHandler();
    void wavLoad();
    void wavSave(const std::string &outputFileName);

private:
    std::string fileName;
    int sampleRate;
    int numChannels;
    int bitsPerSample;
    std::vector<int16_t> samples;
    bool validateHeader(std::vector<uint8_t> header);
};