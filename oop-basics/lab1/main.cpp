#include <iostream>
#include "bitArray.h"

int main(){
    BitArray bitArr(16, 43520);  
    bitArr>>=3;
    std::cout<<bitArr.to_string();
}