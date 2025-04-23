#include <iostream>
#include <fstream>
#include <map>
#include <list>
#include <string>
#include <vector>
#include <algorithm>
#include <iterator>
#include <regex>

class FileData
{
private:
    int wordCount = 0;
    std::map<std::string, int> inputData;
public:
    void createMap(const std::string &inputFileName);
    const std::vector<const std::pair<const std::string, int> *> returnSortedPair() const;
    const int getWordCounter() const;
    const std::map<std::string, int> &getInputData() const;
};