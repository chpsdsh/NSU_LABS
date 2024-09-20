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
    std::list<std::string> inputStrings;

public:
    void readInputData(const std::string &inputFileName);
    void createMap();
    const std::vector<const std::pair<const std::string, int> *> returnSortedPair() const;
    int getWordCounter() const;
    const std::map<std::string, int> &getInputData() const;
    const std::list<std::string> &getInputStrings() const;
};