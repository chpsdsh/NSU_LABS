#include "writeCSV.h"

void WriteCSV::inputDataToCSV(const std::string &outputFileName, const std::vector<std::pair<std::string, int>> &sortedData, int wordCount)
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