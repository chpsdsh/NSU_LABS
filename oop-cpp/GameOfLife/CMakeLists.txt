cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab2 main.cpp commandParser.h commandParser.cpp game.cpp game.h cell.h cell.cpp)

include(CTest)
enable_testing()

find_package(GTest REQUIRED)
add_executable(tests gameTests.cpp commandParser.h commandParser.cpp game.cpp game.h cell.h cell.cpp)

target_link_libraries(tests GTest::GTest GTest::Main pthread)
target_link_libraries(lab2)