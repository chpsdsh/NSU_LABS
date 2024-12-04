#include <iostream>

template <typename... Args>
class CsvParser
{
public:
    CsvParser(std::fstream& file,size_t linesToSkip = 0, char separator = ';'): _file(file), _linesToSkip(linesToSkip), _separator(separator){
        skipLines();
        _startinogPosition = file.tellg();
    };
private:
    size_t _linesToSkip;
    std::ifstream _file;
    char _separator;
    std::streampos _startinogPosition;

    void skipLines()
    {
        for (size_t i = 0; i < _linesToSkip; ++i)
        {
            std::string skippedline;
            std::getline(_file, skippedline);
        }
    }

};
