package minesweeper.gui.view;

import minesweeper.gui.controller.GameController;
import minesweeper.gui.controller.MainMenuController;
import minesweeper.gui.view.frames.GameFrame;
import minesweeper.gui.view.frames.MainMenuFrame;
import minesweeper.model.GameModel;


public class GUIView {
    private MainMenuFrame mainMenuFrame;
    private GameFrame gameFrame;
    private final GameModel model;

    public GUIView(GameModel model) {
        this.model = model;
        initializeGuiView();
    }

    public void initializeGuiView() {
            if (mainMenuFrame != null) {
                mainMenuFrame.dispose();
                mainMenuFrame = null;
            }
            if (gameFrame != null) {
                gameFrame.dispose();
                gameFrame = null;
            }
            mainMenuFrame = new MainMenuFrame(model);
            new MainMenuController(mainMenuFrame, this);
            mainMenuFrame.showMenu();
    }

    public void startGame() {
            mainMenuFrame.startGame();
            gameFrame = new GameFrame(model);
            new GameController(gameFrame, this);
    }

    public void restartGame() {
            if (gameFrame != null) {
                gameFrame.dispose();
                gameFrame = null;
            }
            mainMenuFrame.showGameSettings();
            mainMenuFrame.startGame();
    }
}
