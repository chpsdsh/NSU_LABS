cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab0b main.cpp fileData.cpp writeCSV.cpp fileData.h writeCSV.h)

include(CTest)
enable_testing()

