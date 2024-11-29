#include "WavHandler.h"

WavHandler::WavHandler(const std::string &fileName) : fileName(fileName) {};
WavHandler::WavHandler(const std::string& fileName, std::vector<short int>& samples,int sampleRate):
fileName(fileName), samples(samples), sampleRate(sampleRate){};



bool WavHandler::wavLoad()
{
    std::ifstream file(fileName, std::ios::binary);
    if (!file.is_open())
    {
        std::cerr << "Error! Could not open file: " << fileName << std::endl;
        return false;
    }

    if (!readHeader(file))
        return false;

    short int sample;
    while (file.read(reinterpret_cast<char*>(&sample), sizeof(sample)))
        samples.push_back(sample);

    if (!file.eof() && file.fail())
    {
        std::cerr << "Error! Unexpected end of file or read error: " << fileName << std::endl;
        return false;
    }

    return true;
}

bool WavHandler::wavSave()
{
    std::ofstream file(fileName, std::ios::binary);
    if (!file.is_open())
    {
        std::cerr << "Error! Could not open file for writing: " << fileName << std::endl;
        return false;
    }

    if (!writeHeader(file))
        return false;

    for (const auto& sample : samples)
        file.write(reinterpret_cast<const char*>(&sample), sizeof(sample));

    if (!file)
    {
        std::cerr << "Error! Write operation failed for file: " << fileName << std::endl;
        return false;
    }

    return true;
}

bool WavHandler::readHeader(std::ifstream& file)
{
    char chunk_id[4];
    file.read(chunk_id, 4);
    if (std::string(chunk_id, 4) != "RIFF")
    {
        std::cerr << "Error! Not a valid WAV file (missing RIFF header)." << std::endl;
        validFormat = false;
        return false;
    }

    uint32_t chunk_size;
    file.read(reinterpret_cast<char*>(&chunk_size), 4);

    char format[4];
    file.read(format, 4);
    if (std::string(format, 4) != "WAVE")
    {
        std::cerr << "Error! Not a valid WAV file (missing WAVE header)." << std::endl;
        validFormat = false;
        return false;
    }

    while (file.read(chunk_id, 4))
    {
        uint32_t subchunk_size;
        file.read(reinterpret_cast<char*>(&subchunk_size), 4);

        if (std::string(chunk_id, 4) == "fmt ")
        {
            uint16_t audio_format;
            file.read(reinterpret_cast<char*>(&audio_format), 2);
            if (audio_format != 1)
            {
                std::cerr << "Error! Unsupported audio format (only PCM is supported)." << std::endl;
                validFormat = false;
                return false;
            }

            file.read(reinterpret_cast<char*>(&numChannels), 2);
            if (numChannels != 1)
            {
                std::cerr << "Error! Only mono WAV files are supported." << std::endl;
                validFormat = false;
                return false;
            }

            file.read(reinterpret_cast<char*>(&sampleRate), 4);
            if (sampleRate != 44100)
            {
                std::cerr << "Error! Only 44100 Hz sample rate is supported." << std::endl;
                validFormat = false;
                return false;
            }

            file.ignore(6); // skip byte_rate + block_align (2 + 4)

            uint16_t bits_per_sample;
            file.read(reinterpret_cast<char*>(&bits_per_sample), 2);
            if (bits_per_sample != 16)
            {
                std::cerr << "Error! Only 16-bit WAV files are supported." << std::endl;
                validFormat = false;
                return false;
            }
            bitsPerSample = bits_per_sample;
        }

        else if (std::string(chunk_id, 4) == "data")
        {
            file.read(reinterpret_cast<char*>(&subchunk_size), 4);
            validFormat = true;
            return true;
        }
        else
        {
            file.seekg(subchunk_size, std::ios::cur);
        }
    }

    std::cerr << "Error! Missing data header in WAV file." << std::endl;
    validFormat = false;
    return false;
}

bool WavHandler::writeHeader(std::ofstream& file)
{
    uint32_t chunk_size = 36 + samples.size() * sizeof(short int);
    uint32_t byte_rate = sampleRate * 1 * 16 / 8;
    uint16_t block_align = 1 * 16 / 8;
    uint32_t subchunk2_size = samples.size() * sizeof(short int);

    file.write("RIFF", 4);
    file.write(reinterpret_cast<const char*>(&chunk_size), 4);
    file.write("WAVE", 4);
    if (!file) return false;

    file.write("fmt ", 4);
    uint32_t subchunk1_size = 16;
    uint16_t audio_format = 1;
    uint16_t num_channels = 1;
    uint16_t bits_per_sample = 16;

    file.write(reinterpret_cast<const char*>(&subchunk1_size), 4);
    file.write(reinterpret_cast<const char*>(&audio_format), 2);
    file.write(reinterpret_cast<const char*>(&num_channels), 2);
    file.write(reinterpret_cast<const char*>(&sampleRate), 4);
    file.write(reinterpret_cast<const char*>(&byte_rate), 4);
    file.write(reinterpret_cast<const char*>(&block_align), 2);
    file.write(reinterpret_cast<const char*>(&bits_per_sample), 2);
    if (!file) return false;

    file.write("data", 4);
    file.write(reinterpret_cast<const char*>(&subchunk2_size), 4);
    if (!file) return false;

    return true;
}