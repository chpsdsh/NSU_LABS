#include <iostream>
#include "bitArray.h"

int main(){
    BitArray bitArr(16, 170);
    std::cout<<bitArr.to_string()<<std::endl;
    return 0;
}