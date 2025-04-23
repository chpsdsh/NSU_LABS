#include "WavHandler.h"

WavHandler::WavHandler(const std::string &fileName) : fileName(fileName) {};
WavHandler::WavHandler(const std::string &fileName, std::vector<short int> &samples, int sampleRate) : fileName(fileName), samples(samples), sampleRate(sampleRate) {};
std::vector<short int> &WavHandler::getSamples() { return samples; }
int WavHandler::getSampleRate() const { return sampleRate; }
int WavHandler::getNumChannels() const{return numChannels;}
int WavHandler::getBitsPerSample() const {return bitsPerSample;}
bool WavHandler::getValidFormat() const {return validFormat;}

bool WavHandler::wavLoad()
{
    std::ifstream file(fileName, std::ios::binary);
    if (!file.is_open())
    {
        throw NoFileExeption();
    }

    if (!readHeader(file))
    {
        throw UnknownFormatError("Can't read header");
    }

    short int sample;
    while (file.read(reinterpret_cast<char *>(&sample), sizeof(sample)))
        samples.push_back(sample);

    if (!file.eof() && file.fail())
    {
        throw FileReadError("Failed reading a file");
    }

    return true;
}

bool WavHandler::wavSave()
{
    std::ofstream file(fileName, std::ios::binary);
    if (!file.is_open())
    {
        throw FileReadError("Reading error");
    }

    if (!writeHeader(file))
    {
        throw UnsupportedFormatError("Error writing header");
    }

    for (const auto &sample : samples)
    {
        file.write(reinterpret_cast<const char *>(&sample), sizeof(sample));
    }

    if (!file)
    {
        throw FileReadError(fileName);
    }

    return true;
}

bool WavHandler::readHeader(std::ifstream &file)
{
    char chunkId[4];
    file.read(chunkId, 4);
    if (std::string(chunkId, 4) != "RIFF")
    {
        throw UnsupportedFormatError("Not RIFF format");
    }

    uint32_t chunkSize;
    file.read(reinterpret_cast<char *>(&chunkSize), 4);

    char format[4];
    file.read(format, 4);
    if (std::string(format, 4) != "WAVE")
    {
        throw UnsupportedFormatError("Not WAVE format");
    }

    while (file.read(chunkId, 4))
    {
        uint32_t subchunkSize;
        file.read(reinterpret_cast<char *>(&subchunkSize), 4);

        if (std::string(chunkId, 4) == "fmt ")
        {
            uint16_t audioFormat;
            file.read(reinterpret_cast<char *>(&audioFormat), 2);
            if (audioFormat != 1)
            {
                throw UnsupportedFormatError("Not correct audio format");
            }

            file.read(reinterpret_cast<char *>(&numChannels), 2);
            if (numChannels != 1)
            {
                throw UnsupportedFormatError("Not Mono");
            }

            file.read(reinterpret_cast<char *>(&sampleRate), 4);
            if (sampleRate != 44100)
            {
                throw UnsupportedFormatError("Incorrect sample rate");
            }

            file.ignore(6); // skip byteRate + blockAlign (2 + 4)

            uint16_t bitsPerSample;
            file.read(reinterpret_cast<char *>(&bitsPerSample), 2);
            if (bitsPerSample != 16)
            {
                throw UnsupportedFormatError("Not 16 bits per sample");
            }
            this->bitsPerSample = bitsPerSample;
        }

        else if (std::string(chunkId, 4) == "data")
        {
            file.read(reinterpret_cast<char *>(&subchunkSize), 4);
            validFormat = true;
            return true;
        }
        else
        {
            file.seekg(subchunkSize, std::ios::cur);
        }
    }

    throw UnsupportedFormatError(fileName);
    validFormat = false;
    return false;
}

bool WavHandler::writeHeader(std::ofstream &file)
{
    uint32_t chunkSize = 36 + samples.size() * sizeof(short int);
    uint32_t byteRate = sampleRate * 1 * 16 / 8;
    uint16_t blockAlign = 1 * 16 / 8;
    uint32_t subchunk2Size = samples.size() * sizeof(short int);

    file.write("RIFF", 4);
    file.write(reinterpret_cast<const char *>(&chunkSize), 4);
    file.write("WAVE", 4);
    if (!file)
    {
        return false;
    }

    file.write("fmt ", 4);
    uint32_t subchunk1Size = 16;
    uint16_t audioFormat = 1;
    uint16_t numChannels = 1;
    uint16_t bitsPerSample = 16;

    file.write(reinterpret_cast<const char *>(&subchunk1Size), 4);
    file.write(reinterpret_cast<const char *>(&audioFormat), 2);
    file.write(reinterpret_cast<const char *>(&numChannels), 2);
    file.write(reinterpret_cast<const char *>(&sampleRate), 4);
    file.write(reinterpret_cast<const char *>(&byteRate), 4);
    file.write(reinterpret_cast<const char *>(&blockAlign), 2);
    file.write(reinterpret_cast<const char *>(&bitsPerSample), 2);
    if (!file)
    {
        return false;
    }
    file.write("data", 4);
    file.write(reinterpret_cast<const char *>(&subchunk2Size), 4);
    if (!file)
    {
        return false;
    }
    return true;
}
