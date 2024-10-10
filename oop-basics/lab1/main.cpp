#include <iostream>
#include "bitArray.h"

int main(){
    BitArray bitArr(16, 170);  
    bitArr<<=3;
    std::cout<<bitArr.to_string();
}