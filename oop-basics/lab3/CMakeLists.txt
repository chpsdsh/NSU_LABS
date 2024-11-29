cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab3 main.cpp Parser.cpp Parser.h Converters.cpp Converters.h WavHandler.cpp SoundProcessor.cpp Exceptions.h)

include(CTest)
enable_testing()

find_package(GTest REQUIRED)
add_executable(tests processorTests.cpp Parser.cpp Converters.cpp WavHandler.cpp SoundProcessor.cpp Exceptions.h)

target_link_libraries(tests GTest::GTest GTest::Main pthread)
target_link_libraries(lab3)