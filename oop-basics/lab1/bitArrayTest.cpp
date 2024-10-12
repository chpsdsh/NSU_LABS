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
    bitArr1 <<= 9;
    ASSERT_EQ(bitArr1.to_string(), "00000000");

    BitArray bitArr2(16, 170);
    bitArr2 <<= 8;
    ASSERT_EQ(bitArr2.to_string(), "1010101000000000");
    BitArray bitArr3(16, 170);
    bitArr3 <<= 3;
    ASSERT_EQ(bitArr3.to_string(), "0000010101010000");
}

TEST(BitArrayTests, OperatorRShiftEqTest)
{
    BitArray bitArr1(8, 5);
    bitArr1 >>= 9;
    ASSERT_EQ(bitArr1.to_string(), "00000000");

    BitArray bitArr2(16, 43520);
    ASSERT_EQ(bitArr2.to_string(), "1010101000000000");
    bitArr2 >>= 8;
    ASSERT_EQ(bitArr2.to_string(), "0000000010101010");
    BitArray bitArr3(16, 43520);
    bitArr3 >>= 3;
    ASSERT_EQ(bitArr3.to_string(), "0001010101000000");
}

TEST(BitArrayTests, OperatorLShiftTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ((bitArr << 8).to_string(), "1010101000000000");
}

TEST(BitArrayTests, OperatorRShiftTest)
{
    BitArray bitArr(16, 43520);
    ASSERT_EQ((bitArr >> 8).to_string(), "0000000010101010");
}

TEST(BitArrayTests, SetWithValuesTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ((bitArr.set(0, true)).to_string(), "1000000010101010");
    ASSERT_EQ((bitArr.set(8, false)).to_string(), "1000000000101010");
}

TEST(BitArrayTests, SetTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.set().to_string(), "1111111111111111");
}

TEST(BitArrayTests, ResetWithValuesTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.reset(8).to_string(), "0000000000101010");
}

TEST(BitArrayTests, ResetTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.reset().to_string(), "0000000000000000");
}

TEST(BitArrayTests, AnyTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.any(), true);
    BitArray bitArr1(16, 0);
    ASSERT_EQ(bitArr1.any(), false);
}

TEST(BitArrayTests, NoneTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.none(), false);
    BitArray bitArr1(16, 0);
    ASSERT_EQ(bitArr1.none(), true);
}

TEST(BitArrayTests, OpetatorInversionTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ((~bitArr).to_string(), "1111111101010101");
}

TEST(BitArrayTests, CountTest)
{
    BitArray bitArr(16, 170);
    ASSERT_EQ(bitArr.count(), 4);
}

TEST(BitArrayTests, OperatorIndexer)
{
    BitArray bitArr(8, 170);
    ASSERT_THROW(bitArr[8], std::out_of_range);
    ASSERT_EQ(bitArr[0], 1);
    ASSERT_EQ(bitArr[1], 0);
    ASSERT_EQ(bitArr[2], 1);
    ASSERT_EQ(bitArr[3], 0);
    ASSERT_EQ(bitArr[4], 1);
    ASSERT_EQ(bitArr[5], 0);
    ASSERT_EQ(bitArr[6], 1);
    ASSERT_EQ(bitArr[7], 0);
}

TEST(BitArrayTests, SizeIndexer)
{
    BitArray bitArr(8, 170);
    ASSERT_EQ(bitArr.size(), 8);
}

TEST(BitArrayTests, EmptyIndexer)
{
    BitArray bitArr;
    ASSERT_EQ(bitArr.empty(), true);
}

TEST(BitArrayTests, OperatorDoubleEqual)
{
    BitArray bitArr1(16, 170);
    BitArray bitArr2(8, 170);
    ASSERT_THROW(bitArr1 == bitArr2, std::invalid_argument);
    BitArray bitArr3(16, 170);
    BitArray bitArr4(16, 171);
    ASSERT_EQ(bitArr1 == bitArr4, false);
    ASSERT_EQ(bitArr1 == bitArr3, true);
}

TEST(BitArrayTests, OperatorNotEqual)
{
    BitArray bitArr1(16, 170);
    BitArray bitArr2(8, 170);
    ASSERT_THROW(bitArr1 != bitArr2, std::invalid_argument);
    BitArray bitArr3(16, 170);
    BitArray bitArr4(16, 171);
    ASSERT_EQ(bitArr1 != bitArr4, true);
    ASSERT_EQ(bitArr1 != bitArr3, false);
}

TEST(BitArrayTests, OperatorConuction)
{
    BitArray bitArr1(16, 170);
    BitArray bitArr2(8, 170);
    ASSERT_THROW(bitArr1 & bitArr2, std::invalid_argument);
    BitArray bitArr3(16, 85);
    ASSERT_EQ((bitArr1 & bitArr3).to_string(), "0000000000000000");
}

TEST(BitArrayTests, OperatorDizuction)
{
    BitArray bitArr1(16, 170);
    BitArray bitArr2(8, 170);
    ASSERT_THROW(bitArr1 | bitArr2, std::invalid_argument);
    BitArray bitArr3(16, 85);
    ASSERT_EQ((bitArr1 | bitArr3).to_string(), "0000000011111111");
}

TEST(BitArrayTests, OperatorXOR)
{
    BitArray bitArr1(16, 170);
    BitArray bitArr2(8, 170);
    ASSERT_THROW(bitArr1 ^ bitArr2, std::invalid_argument);
    BitArray bitArr3(16, 85);
    ASSERT_EQ((bitArr1 ^ bitArr3).to_string(), "0000000011111111");
}

TEST(BitArrayIteratorTests, ConstructorWithValues)
{
    BitArray bitArr1(16, 170);
    BitArray::Iterator iter(&bitArr1, 8);
    ASSERT_TRUE(*iter);
    BitArray::Iterator iter2(&bitArr1, -1);
    ASSERT_THROW(*iter2, std::out_of_range);
    BitArray::Iterator iter3(&bitArr1, 17);
    ASSERT_THROW(*iter3, std::out_of_range);
}

TEST(BitArrayIteratorTests, OperatorIncremention)
{
    BitArray bitArr1(3, 8);
    BitArray::Iterator iterationBegin = bitArr1.begin();
    BitArray::Iterator iterationEnd = bitArr1.end();
    ++iterationBegin;
    ++iterationBegin;
    ASSERT_EQ(iterationBegin != iterationEnd, true);
    ++iterationBegin;
    ASSERT_EQ(iterationBegin == iterationEnd, true);
}

TEST(BitArrayIteratorTests, OperatorDecremention)
{
    BitArray bitArr1(3, 5);
    BitArray::Iterator iterationBegin = bitArr1.begin();
    BitArray::Iterator iterationEnd = bitArr1.end();
    --iterationEnd;
    --iterationEnd;
    ASSERT_EQ(iterationBegin != iterationEnd, true);
    --iterationEnd;
    ASSERT_EQ(iterationBegin == iterationEnd, true);
}
