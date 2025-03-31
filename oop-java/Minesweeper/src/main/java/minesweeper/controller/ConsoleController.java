package minesweeper.controller;

import minesweeper.exceptions.CoordinateException;
import minesweeper.exceptions.ReadingGameParametersException;
import minesweeper.model.GameModel;
import minesweeper.view.ConsoleView;
import java.io.*;

public class ConsoleController {
    private GameModel gameModel;;
    private ConsoleView consoleView;

    public ConsoleController(GameModel gameModel, ConsoleView consoleView){
        this.consoleView = consoleView;
        this.gameModel = gameModel;
        gameModel.setConsoleController(this);
        initializeGameParameters();
    }

    public void initializeGameParameters(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String parametersLine = reader.readLine();
            gameModel.createNewGameConsole(parametersLine);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }

    public void inputCells() {
        String coordinateLine;
        consoleView.printInputInfo();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((coordinateLine = reader.readLine()) != null) {
                consoleView.printInputInfo();
                gameModel.executeConsoleInput(coordinateLine);
            }
        }catch (IOException e){
            throw new CoordinateException("Bad coordinate input",e);
        }
    }

    public void restartOrExit(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String parametersLine = reader.readLine();
            gameModel.restartOrExit(parametersLine);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }

    public void writeWinnerName(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String winnerName = reader.readLine();
            gameModel.restartOrExit(parametersLine);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }
}
