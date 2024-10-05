#include "bitArray.h"
#include "gtest/gtest.h"
#include <iostream>

TEST(BitArrayTests, BasicConstructor)
{
    BitArray bitArr;
    ASSERT_EQ(bitArr.size(), 0);
    ASSERT_TRUE(bitArr.empty());
}

TEST(BitArrayTests, ConstructorWithValues)
{
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

TEST(BitArrayTests, to_stringTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.to_string(), "0000000010101010");
}

TEST(BitArrayTests, CopyConstructor)
{
    BitArray bitArr(16, 170);
    BitArray b(bitArr);
    ASSERT_EQ(bitArr.size(), b.size());
    ASSERT_EQ(bitArr.to_string(), b.to_string());
}

TEST(BitArrayTests, Swap)
{
    BitArray bitArr(16, 170);
    BitArray b(8, 128);
    bitArr.swap(b);
    ASSERT_EQ(bitArr.to_string(), "10000000");
    ASSERT_EQ(b.to_string(), "0000000010101010");
}

TEST(BitArrayTests, OperatorEQ)
{
    BitArray bitArr(16, 170);
    BitArray b(8, 128);
    bitArr = b;
    b = bitArr;
    ASSERT_EQ(bitArr.to_string(), "10000000");
    ASSERT_EQ(b.to_string(), "10000000");
}

TEST(BitArrayTests, ResizeTest)
{
    BitArray bitArray; 
    ASSERT_THROW(bitArray.resize(-5, false), std::invalid_argument);
    
    BitArray bitArr(8, 170);  
    bitArray.resize(16, true);  
    ASSERT_EQ(bitArr.size(), 16);
    ASSERT_EQ(bitArr.to_string(), "1010101011111111");
}