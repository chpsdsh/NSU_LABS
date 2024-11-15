#include <iostream>
#include <vector>
#include <string>
#include <cstring>
#include <fstream>
#include <sstream>


struct Command
{
    std::string name;   
    std::vector<std::string> parameters;
};

class InputParser{
    public:
        InputParser(int argc, char **argv);
        bool parse();
        std::string getConfigFileName() const;

    private:
        int argc;
        char ** argv;
        std::string configFileName;
        std::string outputFileName;
        std::vector<std::string> inputFileNames;
        bool helpShown = false;
        
};

class ConfigParser
{
public:
    ConfigParser(const std::string& configFileName);
    std::vector<Command> Parse();

private:
    std::string configFileName;
};

