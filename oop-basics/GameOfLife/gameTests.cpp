#include "commandParser.h"
#include "cell.h"
#include "game.h"
#include "gtest/gtest.h"


TEST(CellTest, BasicCellConstructor)
{
    Cell cell;
    EXPECT_FALSE(cell.isAlive());
}

TEST(CellTest, SetStateTest)
{
    Cell cell;
    EXPECT_FALSE(cell.isAlive());
    cell.setState(true);
    EXPECT_TRUE(cell.isAlive());
    cell.setState(false);
    EXPECT_FALSE(cell.isAlive());
}

TEST(CommandParserTest, ParseInputFileTest)
{
    const char *argv[] = {"program", "--file", "input.txt"};
    CommandParser parser;
    EXPECT_TRUE(parser.parseCommand(3, const_cast<char **>(argv)));
    EXPECT_EQ(parser.getInputFile(), "input.txt");
}

TEST(CommandParserTest, ParseOutputFileTest)
{
    const char *argv[] = {"program", "--output", "output.txt"};
    CommandParser parser;
    EXPECT_TRUE(parser.parseCommand(3, const_cast<char **>(argv)));
    EXPECT_EQ(parser.getOutputFile(), "output.txt");
}

TEST(CommandParserTest, ParseModeTest)
{
    const char *argv[] = {"program", "--mode", "offline"};
    CommandParser parser;
    EXPECT_TRUE(parser.parseCommand(3, const_cast<char **>(argv)));
    EXPECT_TRUE(parser.offlineMode());
    const char *argv2[] = {"program", "--mode", "online"};
    CommandParser parser2;
    EXPECT_TRUE(parser2.parseCommand(3, const_cast<char **>(argv2)));
    EXPECT_FALSE(parser2.offlineMode());
}

TEST(CommandParserTest, ParserHelpTest)
{
    const char *argv[] = {"program", "--help"};
    CommandParser parser;
    parser.parseCommand(2, const_cast<char **>(argv));
    EXPECT_TRUE(parser.helpRequested());
}

TEST(ConsoleParserTest, ParserIterationsTest)
{
    const char *argv[] = {"program", "--iterations", "52"};
    CommandParser parser;
    parser.parseCommand(3, const_cast<char **>(argv));
    EXPECT_EQ(parser.getIterationsNumber(), 52);
}

TEST(GameTest, GameConstructor)
{
    Universe universe(25);
    EXPECT_EQ(universe.getFieldSize(), 25);
    EXPECT_EQ(universe.getGameField()[0].size(), 25);
    EXPECT_EQ(universe.getGameField().size(), 25);
    EXPECT_EQ(universe.getUnverseName(), "");
    EXPECT_EQ(universe.getRule(), "");
}

TEST(GameTest, PrepareGameOfflineMode)
{
    Universe universe(25);
    Game game(universe);
    CommandParser parser;
    const char *argv[] = {"program", "--file", "input.txt", "--output", "output.txt", "--iterations", "3", "--mode", "offline"};
    ASSERT_TRUE(parser.parseCommand(9, const_cast<char **>(argv)));
    EXPECT_TRUE(game.gamePreparation(parser));
    EXPECT_EQ(universe.getUnverseName(), "Blinker Oscillator");
    EXPECT_EQ(universe.getRule(), "B3/S23");
}

TEST(GameTest, GenerateRandomUniverse)
{
    Universe universe(20);
    universe.randomUniverse();

    int aliveCount = 0;
    for (int x = 0; x < 20; ++x)
    {
        for (int y = 0; y < 20; ++y)
        {
            if (universe.getGameField()[x][y].isAlive())
            {
                ++aliveCount;
            }
        }
    }
    EXPECT_GT(aliveCount, 0);
    EXPECT_LT(aliveCount, 400);
}

TEST(GameTest, IsCorrectRuleTest)
{
    Universe universe(20);
    EXPECT_TRUE(universe.isCorrectRule("B3/S23"));
    EXPECT_TRUE(universe.isCorrectRule("B36/S125"));
    EXPECT_FALSE(universe.isCorrectRule("B3S23"));
    EXPECT_FALSE(universe.isCorrectRule("B9/S23"));
}

TEST(GameTest, AliveNeighboursTest){
    Universe universe(5);
    universe.getGameField()[1][1].setState(true);
    universe.getGameField()[1][2].setState(true);
    universe.getGameField()[2][1].setState(true);
    EXPECT_EQ(universe.aliveNeighbours(2, 2), 3);
    EXPECT_EQ(universe.aliveNeighbours(0, 0), 1);
}

TEST(GameTest, PrepareGameTest)
{
    std::ofstream file("test.txt");
    file << "#Life 1.06\n";
    file << "#N Test Universe\n";
    file << "#R B3/S23\n";
    file << "1 1\n2 2\n3 3\n";
    file.close();

    Universe universe(20);
    Game game(universe);
    CommandParser parser;
    const char* argv[] = {"program", "--file", "test.txt","--output", "output.txt", "--mode", "offline", "--iterations", "10"};
    ASSERT_TRUE(parser.parseCommand(9, const_cast<char**>(argv)));

    EXPECT_TRUE(game.gamePreparation(parser));
    EXPECT_EQ(universe.getUnverseName(), "Test Universe");
    EXPECT_EQ(universe.getRule(), "B3/S23");

    std::remove("test_universe.txt");
}