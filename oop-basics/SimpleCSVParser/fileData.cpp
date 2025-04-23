#include "fileData.h"

const std::map<std::string, int> &FileData::getInputData() const
{
    return inputData;
}

const int FileData::getWordCounter() const
{
    return wordCount;
}

void FileData::createMap(const std::string &inputFileName)
{
    std::ifstream inputFile(inputFileName);
    std::regex wordRegex("[a-zA-Z'-]+");
    if (inputFile.is_open())
    {
        std::string line;
        while (getline(inputFile, line))
        {
            auto wordsBegin = std::sregex_iterator(line.begin(), line.end(), wordRegex);
            auto wordsEnd = std::sregex_iterator();
            
            for (auto i = wordsBegin; i != wordsEnd; i++)
            {
                std::string word = (*i).str();
                std::transform(word.begin(), word.end(), word.begin(), ::tolower);
                wordCount++;
                inputData[word]++;
            }
        }
    }
    else
    {
        std::cerr << "Can not open input file" << inputFileName << std::endl;
    }
}

const std::vector<const std::pair<const std::string, int> *> FileData::returnSortedPair() const
{
    std::vector<const std::pair<const std::string, int> *> sortedData;

    for (const auto &elem : inputData)
    {
        sortedData.push_back(&elem);
    }

    std::sort(sortedData.begin(), sortedData.end(), [](const auto *a, const auto *b)
              { return a->second > b->second; });

    return sortedData;
}
