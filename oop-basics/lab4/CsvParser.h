#pragma once
#include <iostream>
#include <fstream>
#include <sstream>
#include "TupleReader.h"

template <typename... Args>
class CsvParser
{
public:
    CsvParser(std::ifstream &file, size_t linesToSkip = 0, char separator = ',') 
        : _file(file), _linesToSkip(linesToSkip), _separator(separator)
    {
        skipLines();
        _startingPosition = file.tellg();
    }

    class Iterator
    {
    public:
        Iterator(CsvParser &parser, bool isEnd) 
            : _parser(parser), _isEnd(isEnd), _lineNumber(parser._linesToSkip), _currentPosition(parser._startingPosition)
        {
            if (!isEnd)
            {
                readTuple();
            }
        }

        std::tuple<Args...> operator*()
        {
            return _currentTuple;
        }

        Iterator& operator++()
        {
            readTuple();
            return *this;
        }

        bool operator!=(const Iterator& other) const
        {
            return _isEnd != other._isEnd;
        }

    private:
        CsvParser& _parser;
        bool _isEnd;
        size_t _lineNumber;
        std::streampos _currentPosition;
        std::tuple<Args...> _currentTuple;

        void readTuple() {
    if (_parser._file.eof()) {
        _isEnd = true;
        return;
    }
    _parser._file.seekg(_currentPosition);
    std::string line;
    std::getline(_parser._file, line);
    _currentPosition = _parser._file.tellg();

    std::istringstream parsedLine(line);  

    _currentTuple = TupleReader<char, std::char_traits<char>, Args...>::read(parsedLine, _lineNumber);
    _lineNumber++;
}
    };

    Iterator begin()
    {
        return Iterator(*this, false);
    }

    Iterator end()
    {
        return Iterator(*this, true);  
    }

private:
    size_t _linesToSkip;
    std::ifstream& _file;
    char _separator;
    std::streampos _startingPosition;

    void skipLines()
    {
        for (size_t i = 0; i < _linesToSkip; ++i)
        {
            std::string skippedline;
            std::getline(_file, skippedline); 
        }
    }
};
