cmake_minimum_required(VERSION 3.5.0)
project(lab0a VERSION 0.1.0 LANGUAGES C CXX)

add_executable(lab1 main.cpp bitArray.cpp bitArray.h)

include(CTest)
enable_testing()

find_package(GTest REQUIRED)
add_executable(tests bitArrayTest.cpp bitArray.cpp)
target_link_libraries(tests GTest::GTest GTest::Main pthread)
target_link_libraries(lab1)