#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>

const int BYTE_SIZE = 8;

class BitArray
{
private:
    std::vector<char> array;
    int numBits;

public:
    // Default constructor.
    // Constructs an empty BitArray.
    BitArray();

    // Destructor.
    // Cleans up any resources used by the BitArray.
    ~BitArray();

    // Constructs a BitArray capable of holding 'num_bits' bits.
    // If 'value' is provided, the first 'sizeof(long)' bits of the array
    // are initialized using the binary representation of 'value'.
    explicit BitArray(int num_bits, unsigned long value = 0);

    // Copy constructor.
    // Creates a copy of the given BitArray 'b'.
    BitArray(const BitArray &b);

    // Swaps the contents of this BitArray with another BitArray 'b'.
    void swap(BitArray &b);

    // Assignment operator.
    // Assigns the contents of another BitArray 'b' to this BitArray.
    BitArray &operator=(const BitArray &b);

    // Resizes the BitArray to hold 'num_bits' bits.
    // If the size is increased, new bits are initialized to 'value' (false by default).
    void resize(int num_bits, bool value = false);

    // Clears all the bits in the BitArray, making it empty.
    void clear();

    // Adds a new bit at the end of the BitArray.
    // The array is resized if necessary.
    void push_back(bool bit);

    // Bitwise AND operation with another BitArray 'b'.
    // Both arrays must have the same size.
    // Throws an exception if the sizes do not match.
    BitArray &operator&=(const BitArray &b);

    // Bitwise OR operation with another BitArray 'b'.
    // Both arrays must have the same size.
    // Throws an exception if the sizes do not match.
    BitArray &operator|=(const BitArray &b);

    // Bitwise XOR operation with another BitArray 'b'.
    // Both arrays must have the same size.
    // Throws an exception if the sizes do not match.
    BitArray &operator^=(const BitArray &b);

    // Left shift operation by 'n' positions.
    // Bits shifted out are replaced with 0s.
    BitArray &operator<<=(int n);

    // Right shift operation by 'n' positions.
    // Bits shifted out are replaced with 0s.
    BitArray &operator>>=(int n);

    // Returns a copy of the BitArray, shifted left by 'n' positions.
    BitArray operator<<(int n) const;

    // Returns a copy of the BitArray, shifted right by 'n' positions.
    BitArray operator>>(int n) const;

    // Sets the bit at index 'n' to 'val' (true by default).
    // Throws an exception if 'n' is out of bounds.
    BitArray &set(int n, bool val = true);

    // Sets all bits in the BitArray to true.
    BitArray &set();

    // Resets the bit at index 'n' to false.
    // Throws an exception if 'n' is out of bounds.
    BitArray &reset(int n);

    // Resets all bits in the BitArray to false.
    BitArray &reset();

    // Returns true if at least one bit in the BitArray is true.
    bool any() const;

    // Returns true if all bits in the BitArray are false.
    bool none() const;

    // Bitwise NOT operation.
    // Returns a new BitArray where each bit is inverted.
    BitArray operator~() const;

    // Counts the number of bits that are set to true.
    int count() const;

    // Returns the value of the bit at index 'i'.
    // Throws an exception if 'i' is out of bounds.
    bool operator[](int i) const;

    // Returns the number of bits in the BitArray.
    int size() const;

    // Returns true if the BitArray contains no bits.
    bool empty() const;

    // Returns a string representation of the BitArray, where each bit
    // is represented by '1' or '0'.
    std::string to_string() const;

    // Iterator class for iterating through the bits of the BitArray.
    class Iterator
    {
    private:
        int index;
        const BitArray *bitArr;
    public:
        // Constructs an iterator for the BitArray starting at index 'idx'.
        Iterator(const BitArray *bArr, int idx);

        // Destructor.
        ~Iterator();

        // Dereferences the iterator to access the value of the current bit.
        bool operator*() const;

        // Pre-increment operator. Moves the iterator to the next bit.
        Iterator &operator++();

        // Pre-decremention operator. Moves the iterator to the next bit.
        Iterator &operator--();

        // Compares two iterators for inequality.
        bool operator!=(const Iterator &iterator) const;

        // Compares two iterators for equality.
        bool operator==(const Iterator &iterator) const;
    };

    // Returns an iterator to the beginning of the BitArray.
    Iterator begin();

    // Returns an iterator to the end of the BitArray (one past the last element).
    Iterator end();
};

// Compares two BitArrays for equality. Returns true if they have the same size
// and all corresponding bits are equal.
bool operator==(const BitArray &a, const BitArray &b);

// Compares two BitArrays for inequality. Returns true if they are not equal.
bool operator!=(const BitArray &a, const BitArray &b);

// Performs a bitwise AND operation between two BitArrays and returns the result.
// Throws an exception if the sizes do not match.
BitArray operator&(const BitArray &b1, const BitArray &b2);

// Performs a bitwise OR operation between two BitArrays and returns the result.
// Throws an exception if the sizes do not match.
BitArray operator|(const BitArray &b1, const BitArray &b2);

// Performs a bitwise XOR operation between two BitArrays and returns the result.
// Throws an exception if the sizes do not match.
BitArray operator^(const BitArray &b1, const BitArray &b2);
