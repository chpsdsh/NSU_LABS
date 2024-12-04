#include <tuple>
#include <iostream>

template <typename Ch, typename Tr, size_t N, typename... Argc>
class PrintTuple
{
public:
    static void print(std::basic_ostream<Ch, Tr> &outStream, std::tuple<Argc...> const &tuple)
    {
        PrintTuple<Ch, Tr, N - 1, Argc...>::print(outStream, tuple);
        outStream << " " << std::get<N - 1>(tuple); 
    }
};

template <typename Ch, typename Tr, typename... Argc>
class PrintTuple<Ch, Tr, 0, Argc...>
{
public:
    static void print(std::basic_ostream<Ch, Tr> &outStream, std::tuple<Argc...> const &tuple)
    {
        outStream << std::get<0>(tuple);
    }
};

template <typename Ch, typename Tr, typename... Argc>
std::basic_ofstream<Ch, Tr> &operator<<(std::basic_ofstream<Ch, Tr> &outStream, const std::tuple<Argc...> &tuple)
{
    PrintTuple<Ch, Tr, sizeof...(Argc) - 1, Argc...>::print(outStream, tuple);
    return outStream;
}
