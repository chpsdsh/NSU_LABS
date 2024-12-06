#include <iostream>
#include <fstream>
#include <sstream>
#include <tuple>
#include "CsvParser.h"
#include "TuplePrinter.h"

int main()
{
   std::ifstream file("test.csv");
   CsvParser<int, std::string> parser(file, 0 /*skip first lines count*/);
   for (auto tuple : parser) {
       std::cout<<tuple<<std::endl;
   }
}
