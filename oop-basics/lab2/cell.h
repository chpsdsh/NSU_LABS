#pragma once

class Cell
{
public:
    Cell();
    bool isAlive() const;
    void setState(bool state);

private:
    bool state;
};
