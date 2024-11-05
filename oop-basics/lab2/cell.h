#pragma once

class Cell{
    private:
        bool state;
    public:
    Cell();
    bool isAlive() const;
    void setState(bool state);
};
