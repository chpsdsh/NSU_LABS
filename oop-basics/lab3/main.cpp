#include <iostream>
#include "Parser.h"

int main(int argc, char *argv[]){
    InputParser parser(argc,argv);
    if(parser.parse()){
        ConfigParser configParser(parser.getConfigFileName());
        
    }
    
}