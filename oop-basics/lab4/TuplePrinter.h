#include <tuple>
#include <iostream>

template <typename Ch, typename Tr, size_t N, typename... Argc>
class TuplePrinter
{
public:
    static void print(std::basic_ostream<Ch, Tr> &outStream, const std::tuple<Argc...>  &tuple)
    {
        TuplePrinter<Ch, Tr, N - 1, Argc...>::print(outStream, tuple);
        outStream << " " << std::get<N>(tuple);
    }
};

template <typename Ch, typename Tr, typename... Argc>
class TuplePrinter<Ch, Tr, 0, Argc...>
{
public:
    static void print(std::basic_ostream<Ch, Tr> &outStream, const std::tuple<Argc...>  &tuple)
    {
        outStream << std::get<0>(tuple);
    }
};

template <typename Ch, typename Tr, typename... Argc>
std::basic_ostream<Ch, Tr> &operator<<(std::basic_ostream<Ch, Tr> &outStream, const std::tuple<Argc...> &tuple)
{
    TuplePrinter<Ch, Tr, sizeof...(Argc) - 1, Argc...>::print(outStream, tuple);
    return outStream;
}
