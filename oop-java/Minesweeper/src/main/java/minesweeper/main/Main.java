package minesweeper.main;
import minesweeper.controller.MainMenuController;
import minesweeper.model.GameModel;
import minesweeper.view.MainMenuView;


public class Main {
        public static void main(String[] args){
            MainMenuView mainMenuView = new MainMenuView();
            GameModel model = new GameModel(mainMenuView);
            MainMenuController controller = new MainMenuController(mainMenuView,model);
            mainMenuView.setVisible(true);

        }
}