package minesweeper.model;

import minesweeper.view.MainMenuView;

public final class GameModel {
    private MainMenuView mainMenuView;


    public  GameModel(MainMenuView mainMenuView){
        this.mainMenuView = mainMenuView;
    }

    public void startGame(){

    }
    public void showHighScore(){

    }
    public void showAbout (){
        mainMenuView.showAbout();
    }
    public void showMenu (){
        mainMenuView.showMenu();
    }
    public void exitGame(){
        System.exit(0);
    }

}
