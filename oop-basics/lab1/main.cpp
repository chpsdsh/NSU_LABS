#include <iostream>
#include "bitArray.h"

int main(){
    BitArray bitArr(16, 170);  
    bitArr.push_back(1);
    std::cout<<bitArr.to_string();
}