#include <iostream>
#include <fstream>
#include <vector>

class WriteCSV
{
public:
   static void inputDataToCSV(const std::string &outputFileName, const std::vector<std::pair<std::string, int>> &sortedData, int wordCount);
};