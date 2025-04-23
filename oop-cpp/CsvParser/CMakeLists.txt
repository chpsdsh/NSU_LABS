cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab4 main.cpp CsvParser.h TuplePrinter.h TupleReader.h)

include(CTest)
enable_testing()

find_package(GTest REQUIRED)
add_executable(tests CsvParserTests.cpp CsvParser.h TuplePrinter.h TupleReader.h)
target_link_libraries(tests GTest::GTest GTest::Main pthread)
target_link_libraries(lab4)