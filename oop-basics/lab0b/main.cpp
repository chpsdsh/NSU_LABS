#include <iostream>
#include <fstream>
#include <map>
#include <list>
#include <string>
#include <sstream>
#include <vector>
#include <algorithm>

class FileData
{
private:
    int wordCount = 0;
    std::map<std::string, int> inputData;
    std::list<std::string> inputStrings;

public:
    void readInputData(const std::string &inputFileName)
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
            std::cerr << "Cannot open input file: " << inputFileName << std::endl;
        }
        inputFile.close();
    }

    void createMap()
    {
        for (const std::string &line : inputStrings)
        {
            std::istringstream iss(line);
            std::string word;
            while (iss >> word)
            {
                if (word[size(word) - 1] == ',' || word[size(word) - 1] == '.')
                    word.pop_back();
                wordCount++;
                inputData[word]++;
            }
        }
    }

    const std::map<std::string, int> &mapReturn() const
    {
        return inputData;
    }

    int wordCountReturn() const
    {
        return wordCount;
    }

    std::vector<std::pair<std::string, int>> getSortedData() const
    {
        std::vector<std::pair<std::string, int>> sortedData(inputData.begin(), inputData.end());

        std::sort(sortedData.begin(), sortedData.end(),
                  [](const std::pair<std::string, int> &a, const std::pair<std::string, int> &b)
                  {
                      return a.second > b.second;
                  });

        return sortedData;
    }
};

class WriteCSV
{
public:
    static void inputDataToCSV(const std::string &outputFileName, const std::vector<std::pair<std::string, int>> &sortedData, int wordCount)
    {
        std::ofstream outputFile(outputFileName);
        if (outputFile.is_open())
        {
            outputFile << "Слово,Частота,Частота(%)\n";
            for (const auto &[word, count] : sortedData)
            {
                outputFile << word << "," << count << "," << (float(count) / float(wordCount) * 100) << "\n";
            }
        }
        else
        {
            std::cerr << "Cannot open output file: " << outputFileName << std::endl;
        }
        outputFile.close();
    }
};

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        std::cerr << "Usage: " << argv[0] << " <inputFile> <outputFile>" << std::endl;
        return 1;
    }

    std::string inputFileName = argv[1];
    std::string outputFileName = argv[2];

    FileData data;
    data.readInputData(inputFileName);
    data.createMap();

    std::vector<std::pair<std::string, int>> sortedData = data.getSortedData();

    WriteCSV::inputDataToCSV(outputFileName, sortedData, data.wordCountReturn());

    return 0;
}
