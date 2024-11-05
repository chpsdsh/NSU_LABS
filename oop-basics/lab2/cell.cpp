#include "cell.h"

Cell::Cell() : state(false) {};
void Cell::setState(bool state){
    (*this).state = state;
}