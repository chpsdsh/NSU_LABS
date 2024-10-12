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
    if (value & (1UL << i))
    {
      int byteIndex = size - 1 - (i / BYTE_SIZE);
      int bitIndex = i % BYTE_SIZE;
      array[byteIndex] |= (1 << bitIndex);
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
  array.resize(newSize, value ? 255 : 0);
}

void BitArray::clear()
{
  array.clear();
  numBits = 0;
}

void BitArray::push_back(bool bit)
{
  std::cout << numBits << " ";
  (*this).resize(numBits + 1);
  std::cout << numBits << " ";
  array[numBits / BYTE_SIZE] |= 0x80 >> ((numBits - 1) % BYTE_SIZE);
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
  std::cout << numBytes;
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
    for (int i = numBytes - 1; i >= 0; i--)
    {
      char current = array[i];
      array[i] = (current << shiftBits) | overflow;
      overflow = (current >> (BYTE_SIZE - shiftBits)) & (255 >> (BYTE_SIZE - shiftBits));
    }
  }
  return *this;
}

BitArray &BitArray::operator>>=(int n)
{
  int shiftBytes = n / BYTE_SIZE;
  int shiftBits = n % BYTE_SIZE;
  int numBytes = (numBits + BYTE_SIZE - 1) / BYTE_SIZE;
  std::cout << numBytes << std::endl;

  if (n >= numBits)
  {
    reset();
    return *this;
  }

  if (shiftBits == 0)
  {
    std::move(array.begin(), array.end() - shiftBytes, array.begin() + shiftBytes);
    std::fill(array.begin(), array.begin() + shiftBytes, 0);
  }
  else
  {
    std::move(array.begin(), array.end() - shiftBytes, array.begin() + shiftBytes);
    std::fill(array.begin(), array.begin() + shiftBytes, 0);

    char overflow = 0;
    for (int i = 0; i < numBytes; i++)
    {
      std::cout << i << (int)overflow << std::endl;
      char current = array[i];
      array[i] = ((current >> shiftBits) & (255 >> (shiftBits)) | overflow);
      overflow = (current << (BYTE_SIZE - shiftBits));
    }
  }

  return *this;
}

BitArray BitArray::operator<<(int n) const
{
  BitArray res = (*this);
  return res <<= n;
}

BitArray BitArray::operator>>(int n) const
{
  BitArray res = (*this);
  return res >>= n;
}

BitArray &BitArray::set(int n, bool val)
{
  int byteIndex = n / BYTE_SIZE;
  int bitIndex = n % BYTE_SIZE;
  if (val)
  {
    array[byteIndex] |= (0x80 >> bitIndex);
  }
  else
  {
    array[byteIndex] &= ~(0x80 >> bitIndex);
  }
  return *this;
}

BitArray &BitArray::set()
{
  std::fill(array.begin(), array.end(), 255);
  return *this;
}

BitArray &BitArray::reset(int n)
{
  int byteIndex = n / BYTE_SIZE;
  int bitIndex = n % BYTE_SIZE;
  array[byteIndex] &= ~(0x80 >> bitIndex);
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
    res.push_back((*this)[i] ? '1' : '0');
  }

  return res;
}

bool operator==(const BitArray &a, const BitArray &b)
{
  if (a.size() != b.size())
  {
    throw std::invalid_argument("Array sizes do not match");
  }

  for (std::size_t i = 0; i < a.size(); ++i)
  {
    if (a[i] != b[i])
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
  if (b1.size() != b2.size())
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

BitArray::Iterator::Iterator(const BitArray *bArr, int idx) : bitArr(bArr), index(idx) {}

BitArray::Iterator::~Iterator() = default;

bool BitArray::Iterator::operator*() const
{
  if (index < 0 || index > (*bitArr).size())
  {
    throw std::out_of_range("Out of range");
  }

  return (*bitArr)[index];
}

BitArray::Iterator &BitArray::Iterator::operator++()
{
  index++;
  return *this;
}

BitArray::Iterator &BitArray::Iterator::operator--()
{
  index--;
  return *this;
}

bool BitArray::Iterator::operator==(const Iterator &iterator) const
{
  return index == iterator.index;
}

bool BitArray::Iterator::operator!=(const Iterator &iterator) const
{
  return index != iterator.index;
}

BitArray::Iterator BitArray::begin() { return Iterator(this, 0); }

BitArray::Iterator BitArray::end() { return Iterator(this, numBits); };