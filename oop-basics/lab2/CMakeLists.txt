cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab2 main.cpp commandParser.h commandParser.h game.cpp game.h)

include(CTest)
enable_testing()