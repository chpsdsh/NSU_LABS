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
    bitArr.resize(16, true);
    ASSERT_EQ(bitArr.size(), 16);
    ASSERT_EQ(bitArr.to_string(), "1010101011111111");
}

TEST(BitArrayTests, ClearTest)
{
    BitArray bitArr(16, 170);
    bitArr.clear();
    ASSERT_EQ(bitArr.size(), 0);
    ASSERT_EQ(bitArr.to_string(), "");
}

TEST(BitArrayTests, PushBackTest)
{
    BitArray bitArr(16, 170);
    bitArr.push_back(1);
    ASSERT_EQ(bitArr.to_string(), "00000000101010101");
}

TEST(BitArrayTests, OperatorConEquelTest)
{
    BitArray bitArr(8, 170);
    bitArr &= bitArr;
    ASSERT_EQ(bitArr.to_string(), "10101010");
    BitArray bitArr1(8, 170);
    BitArray bitArr2(16, 170);
    ASSERT_THROW(bitArr1 &= bitArr2, std::invalid_argument);
    BitArray bitArr3(8, 170);
    BitArray bitArr4(8, 15);
    bitArr3 &= bitArr4;
    ASSERT_EQ(bitArr3.to_string(), "00001010");
}

TEST(BitArrayTests, OperatorDisEquelTest)
{
    BitArray bitArr(8, 170);
    bitArr |= bitArr;
    ASSERT_EQ(bitArr.to_string(), "10101010");
    BitArray bitArr1(8, 170);
    BitArray bitArr2(16, 170);
    ASSERT_THROW(bitArr1 |= bitArr2, std::invalid_argument);
    BitArray bitArr3(8, 170);
    BitArray bitArr4(8, 85);
    bitArr3 |= bitArr4;
    ASSERT_EQ(bitArr3.to_string(), "11111111");
}

TEST(BitArrayTests, OperatorXORTest)
{
    BitArray bitArr1(8, 170);
    BitArray bitArr2(16, 170);
    ASSERT_THROW(bitArr1 ^= bitArr2, std::invalid_argument);
    BitArray bitArr3(8, 170);
    BitArray bitArr4(8, 85);
    bitArr3 ^= bitArr4;
    ASSERT_EQ(bitArr3.to_string(), "11111111");
}

TEST(BitArrayTests, OperatorLShiftEqTest)
{
    BitArray bitArr1(8, 5);
    bitArr1<<=9;
    ASSERT_EQ(bitArr1.to_string(), "00000000");

    BitArray bitArr2(16, 170);
    bitArr2<<=8;
    ASSERT_EQ(bitArr2.to_string(), "1010101000000000");
     BitArray bitArr3(16, 170);
    bitArr3<<=3;
    ASSERT_EQ(bitArr3.to_string(), "0000010101010000");
}

TEST(BitArrayTests, OperatorRShiftEqTest)
{
    BitArray bitArr1(8, 5);
    bitArr1>>=9;
    ASSERT_EQ(bitArr1.to_string(), "00000000");

    BitArray bitArr2(16, 43520);
    ASSERT_EQ(bitArr2.to_string(), "1010101000000000");
    bitArr2>>=8;
    ASSERT_EQ(bitArr2.to_string(), "0000000010101010");
    // BitArray bitArr3(16, 43520);
    //bitArr3>>=3;
    //ASSERT_EQ(bitArr3.to_string(), "0001010101000000");
}