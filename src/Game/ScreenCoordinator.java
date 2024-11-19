package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Screen;
import Players.PlayerType;
import Screens.CreditsScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Screens.CharacterSelectScreen;
import Screens.KeyBindsScreen;

public class ScreenCoordinator extends Screen {
    protected Screen currentScreen = new DefaultScreen();
    protected GameState gameState;
    protected GameState previousGameState;
    private PlayerType selectedPlayer;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void initialize() {
        gameState = GameState.MENU;
        selectedPlayer = PlayerType.PROF;
    }

    @Override
    public void update() {
        do {
            if (previousGameState != gameState) {
                switch (gameState) {
                    case MENU:
                        currentScreen = new MenuScreen(this);
                        break;
                    case LEVEL:
                        currentScreen = new PlayLevelScreen(this);
                        break;
                    case CREDITS:
                        currentScreen = new CreditsScreen(this);
                        break;
                    case PLAYER_SELECT:
                        currentScreen = new CharacterSelectScreen(this);
                        break;
                    case KEYBINDS: 
                        currentScreen = new KeyBindsScreen(this);
                        break;
                }
                currentScreen.initialize();
            }
            previousGameState = gameState;

            currentScreen.update();
        } while (previousGameState != gameState);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        currentScreen.draw(graphicsHandler);
    }

    public PlayerType getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(PlayerType selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
}
