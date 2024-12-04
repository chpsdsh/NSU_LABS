#include <iostream>
#include <fstream>
#include <sstream>
#include <tuple>
#include "CsvParser.h"

int main()
{
   std::ifstream file("test.csv");
   CSVParser<int, string> parser(file, 0 /*skip first lines count*/);
   for (tuple<int, string> rs : parser) {
       cout<<rs<<endl;
   }
}
