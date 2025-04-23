#include <gtest/gtest.h>
#include <sstream>
#include <fstream>
#include "CsvParser.h"

TEST(CSVParserTest, ParseCorrectData)
{
    std::ifstream file_stream("testing.csv");

    CSVParser<int, std::string, double> parser(file_stream, 0, ';', '"');

    size_t line_count = 0;
    for (auto tuple : parser)
    {
        line_count++;
        EXPECT_EQ(std::get<0>(tuple), line_count);
    }

    EXPECT_EQ(line_count, 3);
}

TEST(CSVParserTest, SkipLines)
{
    std::ifstream file_stream("testing.csv");

    CSVParser<int, std::string, double> parser(file_stream, 1, ';', '"');

    size_t line_count = 0;
    for (auto tuple : parser)
    {
        line_count++;
    }

    EXPECT_EQ(line_count, 2);
}

TEST(CSVParserTest, NotEnoughData)
{
    std::ifstream file_stream("testing.csv");

    CSVParser<int, std::string, double, int> parser(file_stream, 1, ';', '"');

    try
    {
        for (auto tuple : parser)
        {
            FAIL() << "Expected NotEnoughData exception";
        }
    }
    catch (const NotEnoughData &e)
    {
        EXPECT_EQ(e.getLineNumber(), 2);
    }
}

TEST(CSVParserTest, FailedToReadData)
{
    std::ifstream file_stream("readingFail.csv");

    CSVParser<int, std::string, double> parser(file_stream, 1, ';', '"');

    try
    {
        for (auto tuple : parser)
        {
            FAIL() << "Expected FailedToReadData exception";
        }
    }
    catch (const FailedToReadData &e)
    {
        EXPECT_EQ(e.getLineNumber(), 2);
        EXPECT_EQ(e.getColumnNumber(), 3);
    }
}

TEST(CSVParserTest, ParsingWithEscapedQuotes)
{
    std::ifstream file_stream{"escape.csv"};

    CSVParser<size_t, std::string, int, std::string, double> parser(file_stream, 0, ';', '"');

    for (auto tuple : parser)
    {
        std::string field = std::get<1>(tuple);
        EXPECT_TRUE(field.find('"') == std::string::npos);
    }
}
