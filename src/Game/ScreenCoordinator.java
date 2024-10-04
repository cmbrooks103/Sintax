package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Screen;
import Players.PlayerType; // Make sure to import PlayerType
import Players.prof;
import Screens.CreditsScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Screens.CharacterSelectScreen;

// Based on the current game state, this class determines which Screen should be shown
// There can only be one "currentScreen", although screens can have "nested" screens
public class ScreenCoordinator extends Screen {
    // currently shown Screen
    protected Screen currentScreen = new DefaultScreen();

    // keep track of gameState so ScreenCoordinator knows which Screen to show
    protected GameState gameState;
    protected GameState previousGameState;
    
    // Store the selected player type
    private PlayerType selectedPlayer;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void initialize() {
        // start game off with Menu Screen
        gameState = GameState.MENU;
        selectedPlayer = PlayerType.PROF; // Set the default player type
    }

    @Override
    public void update() {
        do {
            // if previousGameState does not equal gameState, it means there was a change in gameState
            // this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
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
                    case PLAYER_SELECT:  // New case for character selection
                        currentScreen = new CharacterSelectScreen(this);
                        break;
                }
                currentScreen.initialize();
            }
            previousGameState = gameState;

            // call the update method for the currentScreen
            currentScreen.update();
        } while (previousGameState != gameState);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // call the draw method for the currentScreen
        currentScreen.draw(graphicsHandler);
    }

    // Method to get the selected player type
    public PlayerType getSelectedPlayer() {
        return selectedPlayer;
    }

    // Method to set the selected player type
    public void setSelectedPlayer(PlayerType selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
}
