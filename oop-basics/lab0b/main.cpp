#include <iostream>
#include <fstream>
#include <map>
#include <list>
#include <string>
#include <sstream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <regex>

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
        std::regex wordRegex("[\\w]+");
        for (const auto &line : inputStrings)
        {
        auto wordsBegin = std::sregex_iterator(line.begin(), line.end(), wordRegex);
        auto wordsEnd = std::sregex_iterator();
        for (auto i = wordsBegin;i!= wordsEnd;i++){
            std::string word = (*i).str();
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
  
};

class WriteCSV
{
public:
    static void inputDataToCSV(const std::string &outputFileName, const std::map<std::string, int>& sortedData, int wordCount)
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


    WriteCSV::inputDataToCSV(outputFileName, data.mapReturn(), data.wordCountReturn());

    return 0;
}
