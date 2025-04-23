#pragma once

#include <stdexcept>
#include <string>

class SoundProcessorError : public std::runtime_error
{
public:
    explicit SoundProcessorError(const std::string& error) : std::runtime_error(error) {}
};

class SoundProcessorRunError : public SoundProcessorError
{
public:
    explicit SoundProcessorRunError(const std::string& error) : SoundProcessorError(error) {}
};

class FileReadError : public SoundProcessorError
{
public:
    explicit FileReadError(const std::string& error) : SoundProcessorError("File Read Error: " + error) {}
};


class UnsupportedFormatError : public SoundProcessorError
{
public:
    explicit UnsupportedFormatError(const std::string& error) : SoundProcessorError("Unsupported Format Error: " + error) {}
};


class ConfigParserError : public SoundProcessorError
{
public:
    explicit ConfigParserError(const std::string& error) : SoundProcessorError(error) {}
};


class InvalidCommandError : public ConfigParserError
{
public:
    explicit InvalidCommandError(const std::string& error) : ConfigParserError("Invalid Command Error: " + error) {}
};

class UnknownFormatError : public ConfigParserError
{
public:
    explicit UnknownFormatError (const std::string& error) : ConfigParserError("Unknown Format Error: " + error) {}
};

class WavHandlerError : public SoundProcessorError
{
public:
    explicit WavHandlerError(const std::string& error) : SoundProcessorError("Wav Handler Error: " + error) {}
};

class NoFileExeption : public WavHandlerError
{
public:
    explicit NoFileExeption() : WavHandlerError("No file for processing: " ) {}
};