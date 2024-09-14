#include <iostream>
#include "writeCSV.h"
#include "fileData.h"

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
    WriteCSV::inputDataToCSV(outputFileName, data.returnSortedPair(), data.getWordCounter());

    return 0;
}