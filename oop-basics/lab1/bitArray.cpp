#include "bitArray.h"

// В этой задаче для простоты не требуется делать контейнер шаблонным,
// но это вполне допускается по желанию студента.
BitArray::BitArray() : numBits(0) {};
BitArray::~BitArray() = default;
// Конструирует массив, хранящий заданное количество бит.
// Первые sizeof(long) бит можно инициализровать с помощью параметра value.
explicit BitArray::BitArray(int num_bits, unsigned long value = 0) : numBits(num_bits)
{
  array.resize((num_bits + BYTE_SIZE - 1) / BYTE_SIZE, 0);
  if (num_bits != 0 && !array.empty())
    array[0] = value;
}

BitArray::BitArray(const BitArray &b) : array(b.array), numBits(b.numBits) {}
// ebal OOP NAHUY SUkaaaaa
//  Обменивает фзначения двух битовых массивов.
void BitArray::swap(BitArray &b)
{
  std::swap(array, b.array);
  std::swap(numBits, b.numBits);
}

BitArray &BitArray::operator=(const BitArray &b)
{
  if (&b == this)
  {
    return *this;
  }
  array = b.array;
  numBits = b.numBits;
  return *this;
}

void BitArray::resize(int num_bits, bool value = false)
{
  array.resize((num_bits + BYTE_SIZE - 1) / BYTE_SIZE);
}
// Очищает массив.
void BitArray::clear()
{
  array.clear();
  numBits = 0;
}

void BitArray::push_back(bool bit)
{
  array.resize(numBits + 1);
  array[numBits] = bit;
}

BitArray &BitArray::operator&=(const BitArray &b)
{
  if (&b == this)
    return *this;

  if (numBits != b.numBits)
  {
    throw std::invalid_argument("Array sizes do not match");
  }

  for (std::size_t i = 0; i < numBits; ++i)
  {
    array[i] &= b.array[i];
  }

  return *this;
}
