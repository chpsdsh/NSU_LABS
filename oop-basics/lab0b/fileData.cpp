#include "fileData.h"

const std::map<std::string, int> &FileData::getInputData() const
{
    return inputData;
}

const std::list<std::string> &FileData::getInputStrings() const
{
    return inputStrings;
}

int FileData::getWordCounter() const
{
    return wordCount;
}

void FileData::readInputData(const std::string &inputFileName)
{
    std::ifstream inputFile(inputFileName);
    if (inputFile.is_open())
    {
        std::string newLine;
        while (getline(inputFile, newLine))
            inputStrings.push_back(newLine);
    }
    else
    {
        std::cerr << "Can not open input file: " << inputFileName << std::endl;
    }
    inputFile.close();
}

void FileData::createMap()
{
    std::regex wordRegex("[\\w]+");
    for (const auto &line : inputStrings)
    {
        auto wordsBegin = std::sregex_iterator(line.begin(), line.end(), wordRegex);
        auto wordsEnd = std::sregex_iterator();
        for (auto i = wordsBegin; i != wordsEnd; i++)
        {
            std::string word = (*i).str();
            wordCount++;
            inputData[word]++;
        }
    }
}

std::vector<const std::pair<const std::string, int> *> FileData::returnSortedPair() const
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
