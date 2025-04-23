#include <iostream>
#include <fstream>
#include <vector>

class WriteCSV
{
public:
   static void inputDataToCSV(const std::string &outputFileName, const std::vector<const std::pair<const std::string, int> *> &sortedData, int wordCount);
};