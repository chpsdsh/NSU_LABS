#include "gtest/gtest.h"
#include "Parser.h"
#include "Converters.h"
#include "SoundProcessor.h"
#include <fstream>
#include <sstream>

TEST(InputParserTests, ParseValidArguments) {
    const char* argv[] = { "./sound_processor", "-c", "config.txt", "output.wav", "input1.wav", "input2.wav" };
    int argc = 6;

    InputParser parser(argc, const_cast<char**>(argv));

    ASSERT_TRUE(parser.parse());
    EXPECT_EQ(parser.getConfigFileName(), "config.txt");
    EXPECT_EQ(parser.getOutputFileName(), "output.wav");
    EXPECT_EQ(parser.getInputFileNames().size(), 2);
    EXPECT_EQ(parser.getInputFileNames()[0], "input1.wav");
    EXPECT_EQ(parser.getInputFileNames()[1], "input2.wav");
}

TEST(InputParserTests, ParseMissingArguments) {
    const char* argv[] = { "./sound_processor" };
    int argc = 1;

    InputParser parser(argc, const_cast<char**>(argv));

    ASSERT_FALSE(parser.parse());
}

TEST(InputParserTests, ParseHelpOption) {
    const char* argv[] = { "./sound_processor", "-h" };
    int argc = 2;

    InputParser parser(argc, const_cast<char**>(argv));

    ASSERT_FALSE(parser.parse());
    EXPECT_TRUE(parser.getHelp());
}

TEST(InputParserTests, ParseInvalidArgumentsForCOption) {
    const char* argv[] = { "./sound_processor", "-c" };
    int argc = 2;

    InputParser parser(argc, const_cast<char**>(argv));

    ASSERT_FALSE(parser.parse());
}

TEST(SoundProcessorTest, InitializationTest)
{
    int argc = 5;
    char* argv[] = { "./sound_processor", "-c", "test_config.txt", "output.wav", "f.wav"  };

    InputParser parser(argc, argv);
    ASSERT_TRUE(parser.parse());

    SoundProcessor processor(parser);
    EXPECT_NO_THROW(processor.run());
}

TEST(InputParserTest, EmptyConfigFile)
{
    int argc = 5;
    char* argv[] = { "./sound_processor", "-c", "empty.txt", "output.wav", "input.wav" };
    InputParser parser(argc, argv);
    ASSERT_TRUE(parser.parse());
    ConfigParser confParser(parser);
    const auto& commands = confParser.getAudioConverters();
    EXPECT_TRUE(commands.empty());
}

TEST(InputParserTest, NonexistentConfigFile)
{
    int argc = 5;
    char* argv[] = { "./sound_processor", "-c", "nonexistent_config.txt", "output.wav", "input.wav" };
    InputParser parser(argc, argv);
    EXPECT_NO_THROW(parser.parse());
    ConfigParser confParser(parser);
    EXPECT_THROW(confParser.parse(), ConfigParserError);
}

TEST(RunConvertersTests, RunTest)
{
    int argc = 6;
    char* argv[] = { "./sound_processor", "-c", "config.txt", "f.wav", "funkorama.wav","district_four.wav" };
    InputParser parser(argc, argv);
    EXPECT_NO_THROW(parser.parse());
    ConfigParser confParser(parser);
    EXPECT_NO_THROW(confParser.parse());
    SoundProcessor proc(parser);
    EXPECT_NO_THROW(proc.run());
}

TEST(WavFileTests, District_fourTest) 
{
    WavHandler wav("district_four.wav");
    EXPECT_TRUE(wav.wavLoad());
    EXPECT_EQ(wav.getSampleRate(), 44100);
    EXPECT_EQ(wav.getSamples().size(),10947454);
    EXPECT_EQ(wav.getNumChannels(), 1);
    EXPECT_TRUE(wav.getValidFormat());
}




