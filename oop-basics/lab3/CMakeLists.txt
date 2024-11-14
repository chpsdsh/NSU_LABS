cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab3 main.cpp Parser.cpp Converters.cpp WavHandler.cpp)

include(CTest)
enable_testing()
