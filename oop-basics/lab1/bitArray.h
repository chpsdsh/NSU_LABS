#include <iostream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <string>

const int BYTE_SIZE = 8;

/**
 * A class representing a dynamic array of bits.
 */
class BitArray
{
private:
    std::vector<char> array; // Vector to hold the bits (each char represents a byte).
    int numBits; // Total number of bits in the BitArray.

public:
    /**
     * A class representing a reference to a specific bit in the BitArray.
     */
    class BitReference
    {
    private:
        int bitPosition; // The position of the bit in the BitArray.
        char &reference; // Reference to the byte that contains the bit.

    public:
        /**
         * Constructor for creating a BitReference.
         * 
         * @param reference Reference to the byte containing the bit.
         * @param bitPosition Position of the bit within the byte.
         */
        BitReference(const char &reference, int bitPosition);

        /**
         * Assignment operator to set the bit value.
         * 
         * @param value The boolean value to set the bit to.
         * @return Reference to the current BitReference object.
         */
        BitReference &operator=(bool value);

        /**
         * Conversion operator to get the boolean value of the referenced bit.
         * 
         * @return The boolean value of the bit.
         */
        operator bool() const;
    };

    /**
     * Indexing operator to access a bit at a specific position.
     * 
     * @param i The index of the bit to access.
     * @return A BitReference object for the specified bit.
     */
    BitReference operator[](int i);

    /**
     * Default constructor initializing an empty BitArray.
     */
    BitArray();

    /**
     * Destructor to clean up the BitArray.
     */
    ~BitArray();

    /**
     * Constructor that initializes a BitArray with a specified number of bits.
     * 
     * @param num_bits The number of bits to initialize.
     * @param value The initial value to set for the bits (default is 0).
     */
    explicit BitArray(int num_bits, unsigned long value = 0);

    /**
     * Copy constructor to create a BitArray from another BitArray.
     * 
     * @param b The BitArray to copy from.
     */
    BitArray(const BitArray &b);

    /**
     * Swap the contents of this BitArray with another BitArray.
     * 
     * @param b The BitArray to swap with.
     */
    void swap(BitArray &b);

    /**
     * Assignment operator to assign one BitArray to another.
     * 
     * @param b The BitArray to assign from.
     * @return Reference to this BitArray.
     */
    BitArray &operator=(const BitArray &b);

    /**
     * Resize the BitArray to a new size and optionally initialize the new bits.
     * 
     * @param num_bits The new size for the BitArray.
     * @param value The value to initialize new bits (default is false).
     */
    void resize(int num_bits, bool value = false);

    /**
     * Clear all bits in the BitArray, setting them to false.
     */
    void clear();

    /**
     * Append a new bit to the end of the BitArray.
     * 
     * @param bit The bit value to append (true or false).
     */
    void push_back(bool bit);

    /**
     * Bitwise AND assignment operator.
     * 
     * @param b The BitArray to AND with.
     * @return Reference to this BitArray.
     */
    BitArray &operator&=(const BitArray &b);

    /**
     * Bitwise OR assignment operator.
     * 
     * @param b The BitArray to OR with.
     * @return Reference to this BitArray.
     */
    BitArray &operator|=(const BitArray &b);

    /**
     * Bitwise XOR assignment operator.
     * 
     * @param b The BitArray to XOR with.
     * @return Reference to this BitArray.
     */
    BitArray &operator^=(const BitArray &b);

    /**
     * Left shift assignment operator.
     * 
     * @param n The number of positions to shift left.
     * @return Reference to this BitArray.
     */
    BitArray &operator<<=(int n);

    /**
     * Right shift assignment operator.
     * 
     * @param n The number of positions to shift right.
     * @return Reference to this BitArray.
     */
    BitArray &operator>>=(int n);

    /**
     * Left shift operator that returns a new BitArray.
     * 
     * @param n The number of positions to shift left.
     * @return A new BitArray that is the result of the left shift.
     */
    BitArray operator<<(int n) const;

    /**
     * Right shift operator that returns a new BitArray.
     * 
     * @param n The number of positions to shift right.
     * @return A new BitArray that is the result of the right shift.
     */
    BitArray operator>>(int n) const;

    /**
     * Set the value of a specific bit.
     * 
     * @param n The position of the bit to set.
     * @param val The value to set the bit to (default is true).
     * @return Reference to this BitArray.
     */
    BitArray &set(int n, bool val = true);

    /**
     * Set all bits in the BitArray to true.
     * 
     * @return Reference to this BitArray.
     */
    BitArray &set();

    /**
     * Reset (clear) a specific bit.
     * 
     * @param n The position of the bit to reset.
     * @return Reference to this BitArray.
     */
    BitArray &reset(int n);

    /**
     * Reset (clear) all bits in the BitArray.
     * 
     * @return Reference to this BitArray.
     */
    BitArray &reset();

    /**
     * Check if any bits in the BitArray are set to true.
     * 
     * @return True if any bits are set, false otherwise.
     */
    bool any() const;

    /**
     * Check if none of the bits in the BitArray are set to true.
     * 
     * @return True if no bits are set, false otherwise.
     */
    bool none() const;

    /**
     * Bitwise NOT operator, returning a new BitArray with all bits inverted.
     * 
     * @return A new BitArray with inverted bit values.
     */
    BitArray operator~() const;

    /**
     * Count the number of bits set to true.
     * 
     * @return The number of true bits in the BitArray.
     */
    int count() const;

    /**
     * Access a specific bit in the BitArray as a constant.
     * 
     * @param i The index of the bit to access.
     * @return The boolean value of the specified bit.
     */
    bool operator[](int i) const;

    /**
     * Get the size of the BitArray.
     * 
     * @return The number of bits in the BitArray.
     */
    int size() const;

    /**
     * Check if the BitArray is empty.
     * 
     * @return True if the BitArray has no bits, false otherwise.
     */
    bool empty() const;

    /**
     * Convert the BitArray to a string representation.
     * 
     * @return A string representing the bit values (e.g., "10101").
     */
    std::string to_string() const;

    /**
     * An iterator class for iterating over the bits in the BitArray.
     */
    class Iterator
    {
    private:
        int index; // Current index in the BitArray.
        const BitArray *bitArr; // Pointer to the BitArray being iterated.

    public:
        /**
         * Constructor for creating an Iterator.
         * 
         * @param bArr Pointer to the BitArray to iterate over.
         * @param idx The starting index for the iterator.
         */
        Iterator(const BitArray *bArr, int idx);

        /**
         * Destructor for the Iterator.
         */
        ~Iterator();

        /**
         * Dereference operator to access the current bit.
         * 
         * @return A BitReference for the current bit.
         */
        BitReference operator*() const;

        /**
         * Prefix increment operator to move to the next bit.
         * 
         * @return Reference to the updated Iterator.
         */
        Iterator &operator++();

        /**
         * Prefix decrement operator to move to the previous bit.
         * 
         * @return Reference to the updated Iterator.
         */
        Iterator &operator--();

        /**
         * Inequality operator to compare two Iterators.
         * 
         * @param iterator The Iterator to compare against.
         * @return True if the Iterators are not equal, false otherwise.
         */
        bool operator!=(const Iterator &iterator) const;

        /**
         * Equality operator to compare two Iterators.
         * 
         * @param iterator The Iterator to compare against.
         * @return True if the Iterators are equal, false otherwise.
         */
        bool operator==(const Iterator &iterator) const;
    };

    /**
     * Get an iterator pointing to the first bit in the BitArray.
     * 
     * @return An Iterator set to the beginning of the BitArray.
     */
    Iterator begin();

    /**
     * Get an iterator pointing to the end of the BitArray (one past the last bit).
     * 
     * @return An Iterator set to the end of the BitArray.
     */
    Iterator end();
};

/**
 * Equality operator to compare two BitArray objects.
 * 
 * @param a The first BitArray to compare.
 * @param b The second BitArray to compare.
 * @return True if both BitArray objects are equal, false otherwise.
 */
bool operator==(const BitArray &a, const BitArray &b);

/**
 * Inequality operator to compare two BitArray objects.
 * 
 * @param a The first BitArray to compare.
 * @param b The second BitArray to compare.
 * @return True if both BitArray objects are not equal, false otherwise.
 */
bool operator!=(const BitArray &a, const BitArray &b);

/**
 * Bitwise AND operator for two BitArray objects.
 * 
 * @param b1 The first BitArray to AND.
 * @param b2 The second BitArray to AND.
 * @return A new BitArray that is the result of the bitwise AND operation.
 */
BitArray operator&(const BitArray &b1, const BitArray &b2);

/**
 * Bitwise OR operator for two BitArray objects.
 * 
 * @param b1 The first BitArray to OR.
 * @param b2 The second BitArray to OR.
 * @return A new BitArray that is the result of the bitwise OR operation.
 */
BitArray operator|(const BitArray &b1, const BitArray &b2);

/**
 * Bitwise XOR operator for two BitArray objects.
 * 
 * @param b1 The first BitArray to XOR.
 * @param b2 The second BitArray to XOR.
 * @return A new BitArray that is the result of the bitwise XOR operation.
 */
BitArray operator^(const BitArray &b1, const BitArray &b2);
