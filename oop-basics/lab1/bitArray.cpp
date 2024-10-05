#include "bitArray.h"

BitArray::BitArray() : numBits(0) {};
BitArray::~BitArray() = default;
BitArray::BitArray(const BitArray &b) : array(b.array), numBits(b.numBits) {}

BitArray::BitArray(int num_bits, unsigned long value) : numBits(num_bits)
{
  int size = (num_bits + BYTE_SIZE - 1) / BYTE_SIZE;
  array.resize(size, 0);
  for (int i = 0; i < num_bits && i < sizeof(value) * 8; ++i)
  {
    if (value & (0x80 >> i))
    {
      int byteIndex = (size * 8 - 1 - i) / BYTE_SIZE;
      int bitIndex = i % BYTE_SIZE;
      array[byteIndex] |= (0x80 >> bitIndex);
    }
  }
}

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

void BitArray::resize(int num_bits, bool value)
{
  if (num_bits < 0)
  {
    throw std::invalid_argument("num_bits must be a positive value");
  }
  std::size_t newSize = (num_bits + BYTE_SIZE - 1) / BYTE_SIZE;
  numBits = num_bits;
  array.resize(newSize, value ? 0xFF : 0x00);
}

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

BitArray &BitArray::operator|=(const BitArray &b)
{
  if (&b == this)
    return *this;

  if (numBits != b.numBits)
  {
    throw std::invalid_argument("Array sizes do not match");
  }

  for (std::size_t i = 0; i < numBits; ++i)
  {
    array[i] |= b.array[i];
  }

  return *this;
}

BitArray &BitArray::operator^=(const BitArray &b)
{
  if (&b == this)
    return *this;

  if (numBits != b.numBits)
  {
    throw std::invalid_argument("Array sizes do not match");
  }

  for (std::size_t i = 0; i < numBits; ++i)
  {
    array[i] ^= b.array[i];
  }

  return *this;
}

BitArray &BitArray::operator<<=(int n)
{
  int shiftBytes = n / BYTE_SIZE;
  int shiftBits = n % BYTE_SIZE;
  int numBytes = (numBits + BYTE_SIZE - 1) / BYTE_SIZE;
  if (n >= numBits)
  {
    reset();
    return *this;
  }
  if (shiftBits == 0)
  {
    std::move(array.begin() + shiftBytes, array.end(), array.begin());
    std::fill(array.end() - shiftBytes, array.end(), 0);
  }
  else
  {

    std::move(array.begin() + shiftBytes, array.end(), array.begin());
    std::fill(array.end() - shiftBytes, array.end(), 0);
    char overflow = 0;
    for (std::size_t i = 0; i < numBytes; ++i)
    {
      char current = array[i];
      array[i] = (current << shiftBits) | overflow;
      overflow = (current >> (BYTE_SIZE - shiftBits));
    }
  }
  if (numBits % BYTE_SIZE != 0)
  {
    char mask = (1 << (numBits % BYTE_SIZE)) - 1;
    array[numBytes - 1] &= mask;
  }
  return *this;
}

BitArray &BitArray::operator>>=(int n)
{
  int shiftBytes = n / BYTE_SIZE;
  int shiftBits = n % BYTE_SIZE;
  int numBytes = (numBits + BYTE_SIZE - 1) / BYTE_SIZE;

  if (n >= numBits)
  {
    reset();
    return *this;
  }

  if (shiftBits == 0)
  {
    std::move(array.begin() + shiftBytes, array.end(), array.begin());
    std::fill(array.end() - shiftBytes, array.end(), 0);
  }
  else
  {
    std::move(array.begin() + shiftBytes, array.end(), array.begin());
    std::fill(array.end() - shiftBytes, array.end(), 0);

    char overflow = 0;
    for (int i = numBytes - 1; i >= 0; --i)
    {
      char current = array[i];
      array[i] = (current >> shiftBits) | overflow;
      overflow = (current << (BYTE_SIZE - shiftBits));
    }
  }

  if (numBits % BYTE_SIZE != 0)
  {
    char mask = (1 << (numBits % BYTE_SIZE)) - 1;
    array[numBytes - 1] &= mask;
  }

  return *this;
}

BitArray BitArray::operator<<(int n) const
{
  BitArray res = (*this);
  return res << n;
}

BitArray BitArray::operator>>(int n) const
{
  BitArray res = (*this);
  return res >> n;
}

BitArray &BitArray::set(int n, bool val)
{
  int byteIndex = n / BYTE_SIZE;
  int bitIndex = n % BYTE_SIZE;
  if (val)
  {
    array[byteIndex] = (1 << bitIndex);
  }
  else
  {
    array[byteIndex] = ~(1 << bitIndex);
  }
  return *this;
}

BitArray &BitArray::set()
{
  std::fill(array.begin(), array.end(), 1);
  return *this;
}

BitArray &BitArray::reset(int n)
{
  int byteIndex = n / BYTE_SIZE;
  int bitIndex = n % BYTE_SIZE;
  array[byteIndex] = (0 << bitIndex);
  return *this;
}

BitArray &BitArray::reset()
{
  std::fill(array.begin(), array.end(), 0);
  return *this;
}

bool BitArray::any() const
{
  for (const char &byte : array)
  {
    if (byte != 0)
    {
      return true;
    }
  }
  return false;
}

bool BitArray::none() const
{
  return !any();
}

BitArray BitArray::operator~() const
{
  BitArray res = *this;
  for (char &byte : res.array)
  {
    byte = ~byte;
  }
  return res;
}

int BitArray::count() const
{
  int countOneBits = 0;
  for (std::size_t i = 0; i < numBits; ++i)
  {
    int byte = i / BYTE_SIZE;
    int bitIndex = i % BYTE_SIZE;
    if (array[byte] & (1 << bitIndex))
    {
      countOneBits++;
    }
  }
  return countOneBits;
}

bool BitArray::operator[](int i) const
{
  if (i < 0 || i >= numBits)
  {
    throw std::out_of_range("Index out of range");
  }
  int byteIndex = i / BYTE_SIZE;                  
  int bitIndex = i % BYTE_SIZE;                   
  return (array[byteIndex] & (0x80 >> bitIndex)); 
}

int BitArray::size() const
{
  return numBits;
}

bool BitArray::empty() const
{
  return numBits == 0;
}
std::string BitArray::to_string() const
{
  std::string res;
  for (std::size_t i = 0; i < numBits; ++i)
  {
    int byte = i / BYTE_SIZE;
    int bitIndex = i % BYTE_SIZE;
    res.push_back(array[byte] & (1 << bitIndex));
  }
  return res;
}

bool operator==(const BitArray &a, const BitArray &b)
{
  if (a.size() != b.size())
  {
    return false;
  }

  for (std::size_t i = 0; i < a.size(); ++i)
  {
    if (a.operator[](i) != b.operator[](i))
    {
      return false;
    }
  }
  return true;
}

bool operator!=(const BitArray &a, const BitArray &b)
{
  return !(operator==(a, b));
}

BitArray operator&(const BitArray &b1, const BitArray &b2)
{
  if (b1.size() != b1.size())
  {
    throw std::invalid_argument("Array sizes do not match");
  }
  BitArray result = b1;
  return result.operator&=(b2);
}

BitArray operator|(const BitArray &b1, const BitArray &b2)
{
  if (b1.size() != b1.size())
  {
    throw std::invalid_argument("Array sizes do not match");
  }
  BitArray result = b1;
  return result.operator|=(b2);
}

BitArray operator^(const BitArray &b1, const BitArray &b2)
{
  if (b1.size() != b1.size())
  {
    throw std::invalid_argument("Array sizes do not match");
  }
  BitArray result = b1;
  return result.operator^=(b2);
}
