package minesweeper.console.controller;

import minesweeper.exceptions.CoordinateException;
import minesweeper.exceptions.ReadingGameParametersException;
import minesweeper.console.view.ConsoleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleController {
    private final ConsoleView consoleView;

    public ConsoleController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        consoleView.printInitializeParameters();
        initializeGameParameters();
    }

    private void initializeGameParameters() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parameters = splitLine(reader.readLine());
            if (parameters != null) {
                consoleView.createField(parameters);
                inputCells();
            } else {
                throw new ReadingGameParametersException("Parameters mus be not null");
            }
        } catch (IOException e) {
            throw new ReadingGameParametersException("Bad game parameters", e);
        }
    }

    private void inputCells() {
        String coordinateLine;
        String[] coordinateString;
        consoleView.printInputInfo();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((coordinateLine = reader.readLine()) != null && !consoleView.isGameOver()) {
                consoleView.printInputInfo();
                coordinateString = splitLine(coordinateLine);
                if (coordinateString.length == 2) {
                    consoleView.openCells(coordinateString);
                } else if (coordinateString.length == 3 && coordinateString[2].equals("F")) {
                    consoleView.drawFlag(coordinateString);
                }
                if (consoleView.isGameWin()) {
                    consoleView.gameWin();
                    writeWinnerName();
                } else if (consoleView.isGameOver()) {
                    consoleView.gameOver();
                    restartOrExit();
                }
            }
        } catch (IOException e) {
            throw new CoordinateException("Bad coordinate input", e);
        }
    }

    private void restartOrExit() {
        String parametersLine;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((parametersLine = reader.readLine()) != null) {
                consoleView.restartOrExit(parametersLine);
            }
        } catch (IOException e) {
            throw new ReadingGameParametersException("Bad game parameters", e);
        }
    }

    private void writeWinnerName() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String winnerName = reader.readLine();
            consoleView.confirmWinner(winnerName);
            consoleView.printRestartInfo();
            restartOrExit();
        } catch (IOException e) {
            throw new ReadingGameParametersException("Bad game parameters", e);
        }
    }

    private String[] splitLine(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        return line.split(" ");
    }
}
