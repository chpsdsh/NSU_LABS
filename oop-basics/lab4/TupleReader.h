#pragma once
#include <iostream>
#include <tuple>

template <typename Ch, typename Tr, typename Head, typename... Tail>
class TupleReader
{
public:
    static std::tuple<Head, Tail...> read(std::basic_istream<Ch, Tr>& inputStream, size_t lineNumber, size_t columnNumber = 1)
    {
        Head data;
        inputStream >> data;
        return std::tuple_cat(std::make_tuple(data), TupleReader<Ch, Tr, Tail...>::read(inputStream, lineNumber, columnNumber + 1));
    }
};

template <typename Ch, typename Tr, typename Head>
class TupleReader<Ch, Tr, Head>
{
public:
    static std::tuple<Head> read(std::basic_istream<Ch, Tr>& inputStream, size_t lineNumber, size_t columnNumber = 1)
    {
        Head data;
        inputStream >> data;
        return std::make_tuple(data);
    }
};
