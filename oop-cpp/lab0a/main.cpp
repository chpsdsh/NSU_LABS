#include "module1.h"
#include "module2.h"
#include "module3.h"
#include <iostream>

//using namespace std; чтобы не писать std

int main(int argc, char **argv)
{
	std::cout << "Hello world!" << "\n";

	std::cout << Module1::getMyName() << "\n";
	std::cout << Module2::getMyName() << "\n";

	using namespace Module1;
	std::cout << getMyName() << "\n"; // (A) John
	std::cout << Module2::getMyName() << "\n";

	// using namespace Module2; // (B)
	// std::cout << getMyName() << "\n"; // COMPILATION ERROR (C) здесь происходит перегрузка функций(function overloading)
	 //Чтобы не было ошибки эти функции должны иметь или разное количество параметров или параметры разных типов

	using Module2::getMyName;
	std::cout << getMyName() << "\n"; // (D)James

	std::cout << Module3::getMyName() << "\n"; // (5)Peter
}
