#include "bitArray.h"
#include "gtest/gtest.h"
#include <iostream>

TEST(BitArrayTests, BasicConstructor){
    BitArray bitArr;
    ASSERT_EQ(bitArr.size(),0);
    ASSERT_TRUE(bitArr.empty());
}



TEST(BitArrayTests, ConstructorWithValues) {
    BitArray bitArr(16, 170);  
    ASSERT_EQ(bitArr.size(), 16);
    ASSERT_EQ(bitArr[0], 0);  
    ASSERT_EQ(bitArr[1], 0);
    ASSERT_EQ(bitArr[2], 0);
    ASSERT_EQ(bitArr[3], 0);
    ASSERT_EQ(bitArr[4], 0);
    ASSERT_EQ(bitArr[5], 0);
    ASSERT_EQ(bitArr[6], 0);
    ASSERT_EQ(bitArr[7], 0);
    ASSERT_EQ(bitArr[8], 1);  
    ASSERT_EQ(bitArr[9], 0);
    ASSERT_EQ(bitArr[10], 1);
    ASSERT_EQ(bitArr[11], 0);
    ASSERT_EQ(bitArr[12], 1);
    ASSERT_EQ(bitArr[13], 0);
    ASSERT_EQ(bitArr[14], 1);
    ASSERT_EQ(bitArr[15], 0);  
}

TEST(BitArrayTests, CopyConstructor) {
    BitArray bitArr(16, 170); 
    BitArray b(bitArr); 
    ASSERT_EQ(bitArr.size(), b.size());
    ASSERT_EQ(bitArr.to_string(),b.to_string());//Проверь to_string
}