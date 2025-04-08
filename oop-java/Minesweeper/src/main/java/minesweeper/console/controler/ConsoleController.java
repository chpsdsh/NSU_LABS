package minesweeper.console.controler;

import minesweeper.exceptions.CoordinateException;
import minesweeper.exceptions.ReadingGameParametersException;
import minesweeper.console.view.ConsoleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleController {
    private ConsoleView consoleView;

    public ConsoleController(ConsoleView consoleView){
        this.consoleView = consoleView;
//        gameModel.setConsoleController(this);
        initializeGameParameters();
    }

    public void initializeGameParameters(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String parametersLine = reader.readLine();
//            gameModel.createNewGameConsole(parametersLine);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }

    public void inputCells( boolean gameOver) {
        String coordinateLine;
        consoleView.printInputInfo();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((coordinateLine = reader.readLine()) != null && !gameOver) {
                consoleView.printInputInfo();
//                gameModel.executeConsoleInput(coordinateLine);
            }
        }catch (IOException e){
            throw new CoordinateException("Bad coordinate input",e);
        }
    }

    public void restartOrExit(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String parametersLine = reader.readLine();
//            gameModel.restartOrExit(parametersLine);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }

    public void writeWinnerName(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String winnerName = reader.readLine();
//                gameModel.confirmWinner(winnerName);
        }catch (IOException e){
            throw new ReadingGameParametersException("Bad game parameters",e);
        }
    }
}
