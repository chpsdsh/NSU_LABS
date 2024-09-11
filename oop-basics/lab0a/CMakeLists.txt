cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES CXX)

add_executable(lab0a main.cpp module1.cpp module2.cpp module3.cpp module1.h module2.h module3.h)

include(CTest)
enable_testing()

