#include "WavHandler.h"

WavHandler::WavHandler(std::string &fileName) : fileName(fileName) {};
WavHandler::WavHandler(const std::string& filename,  std::vector<short int>& samples,int sampleRate):
fileName(fileName), samples(samples), sampleRate(sampleRate){};
int WavHandler::getSampleRate() const{return sampleRate;}


std::vector<short int> WavHandler::getSamples() const { return samples; }


void WavHandler::wavLoad()
{
    std::ifstream file(fileName, std::ios::binary);
    if (!file.is_open())
    {
        throw std::runtime_error("Error: Could not open WAV file " + fileName);
    }

    // Заголовок WAV файла (44 байта)
    std::vector<uint8_t> header(44);
    file.read(reinterpret_cast<char *>(header.data()), header.size());
    
    if (file.gcount() != 44)
    {
        throw std::runtime_error("Error: WAV file has incorrect header size.");
    }

    // Проверяем "RIFF" и "WAVE" в заголовке
    if (std::strncmp(reinterpret_cast<char*>(header.data()), "RIFF", 4) != 0 || 
        std::strncmp(reinterpret_cast<char*>(&header[8]), "WAVE", 4) != 0)
    {
        throw std::runtime_error("Error: Invalid WAV file format.");
    }

    // Извлекаем параметры
    int dataSize = *reinterpret_cast<int32_t*>(&header[40]);  // Размер данных
    sampleRate = *reinterpret_cast<int32_t*>(&header[24]);    // Частота дискретизации
    numChannels = *reinterpret_cast<int16_t*>(&header[22]);   // Количество каналов
    bitsPerSample = *reinterpret_cast<int16_t*>(&header[34]);  // Битность сэмпла (обычно 16)

    // Проверка, что файл PCM моно 16 бит 44,100 Гц
    if (numChannels != 1 || bitsPerSample != 16 || sampleRate != 44100)
    {
        throw std::runtime_error("Error: Unsupported WAV format. Only mono 16-bit 44.1kHz PCM is supported.");
    }

    // Читаем данные (сэмплы)
    int numSamples = dataSize / (bitsPerSample / 8);  // Рассчитываем количество сэмплов (на основе dataSize)

    samples.resize(numSamples);
    file.read(reinterpret_cast<char *>(samples.data()), dataSize);
    
    if (file.gcount() != dataSize)
    {
        throw std::runtime_error("Error: Failed to read all data from WAV file.");
    }

    file.close();
}

void WavHandler::wavSave(const std::string &outputFileName)
{
    std::ofstream file(outputFileName, std::ios::binary);
    if (!file.is_open())
    {
        throw std::runtime_error("Error: Could not open output file " + outputFileName);
    }

    // Заголовок WAV-файла
    std::vector<uint8_t> header(44, 0);

    // RIFF заголовок
    std::memcpy(header.data(), "RIFF", 4);
    int32_t fileSize = 36 + samples.size() * (bitsPerSample / 8);
    std::memcpy(&header[4], &fileSize, 4);
    std::memcpy(&header[8], "WAVE", 4);

    // fmt подзаголовок
    std::memcpy(&header[12], "fmt ", 4);
    int32_t fmtChunkSize = 16;
    std::memcpy(&header[16], &fmtChunkSize, 4);
    int16_t audioFormat = 1; // PCM
    std::memcpy(&header[20], &audioFormat, 2);
    std::memcpy(&header[22], &numChannels, 2);
    std::memcpy(&header[24], &sampleRate, 4);
    int32_t byteRate = sampleRate * numChannels * (bitsPerSample / 8);
    std::memcpy(&header[28], &byteRate, 4);
    int16_t blockAlign = numChannels * (bitsPerSample / 8);
    std::memcpy(&header[32], &blockAlign, 2);
    std::memcpy(&header[34], &bitsPerSample, 2);

    // data подзаголовок
    std::memcpy(&header[36], "data", 4);
    int32_t dataChunkSize = samples.size() * (bitsPerSample / 8);
    std::memcpy(&header[40], &dataChunkSize, 4);

    // Запись заголовка и данных
    file.write(reinterpret_cast<const char *>(header.data()), header.size());
    file.write(reinterpret_cast<const char *>(samples.data()), dataChunkSize);
    file.close();
}

bool WavHandler::validateHeader(std::vector<uint8_t> header)
{
    int16_t audioFormat = *reinterpret_cast<const int16_t *>(&header[20]);
    numChannels = *reinterpret_cast<const int16_t *>(&header[22]);
    sampleRate = *reinterpret_cast<const int32_t *>(&header[24]);
    bitsPerSample = *reinterpret_cast<const int16_t *>(&header[34]);

    return audioFormat == 1 && numChannels == 1 && sampleRate == 44100 && bitsPerSample == 16;
}