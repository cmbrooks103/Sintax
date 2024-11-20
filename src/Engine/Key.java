package Engine;

import java.util.HashMap;
import java.util.Map;

// Enum to define keys and manage dynamic keybinding
public enum Key {
    UP, 
    DOWN, 
    RIGHT, 
    LEFT,   
    ENTER,
    SHIFT,
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ZERO,
    SPACE,
    ESC;

    // A static map to store current keybindings by action
    private static Map<String, Key> keybinds = new HashMap<>();

    // Default keybindings (Arrow keys)
    static {
        resetDefaultKeybinds();
    }

    // Method to set custom keybindings for movement actions
    public static void setKeybinds(Key up, Key down, Key left, Key right) {
        keybinds.put("Move Up", up);
        keybinds.put("Move Down", down);
        keybinds.put("Move Left", left);
        keybinds.put("Move Right", right);
        System.out.println("Keybindings set to: " + up + ", " + down + ", " + left + ", " + right); // Debugging line
    }

    // Reset keybindings to the default arrow keys configuration
    public static void resetDefaultKeybinds() {
        keybinds.put("Move Up", Key.UP);
        keybinds.put("Move Down", Key.DOWN);
        keybinds.put("Move Left", Key.LEFT);
        keybinds.put("Move Right", Key.RIGHT);
    }

    // Get the key bound to a specific action (e.g., "Move Up")
    public static Key getKeyForAction(String action) {
        return keybinds.getOrDefault(action, null);
    }

    // Check if a specific action's key is currently pressed
    public static boolean isActionKeyDown(String action) {
        Key boundKey = getKeyForAction(action);
        return boundKey != null && Keyboard.isKeyDown(boundKey);
    }

    // Set keybinds to WASD keys
    public static void setWASDKeybinds() {
        setKeybinds(Key.W, Key.S, Key.A, Key.D); // Set movement to WASD
        System.out.println("WASD keybinds set: W (Up), A (Left), S (Down), D (Right)");
    }

    // Set keybinds to Arrow keys
    public static void setArrowKeyKeybinds() {
        setKeybinds(Key.UP, Key.DOWN, Key.LEFT, Key.RIGHT); // Set movement to Arrow keys
        System.out.println("Arrow keybinds set: ^, v, <, >");
    }

    // Debug current keybinds (Prints all current mappings for actions)
    public static void printKeybinds() {
        System.out.println("Current Keybinds:");
        for (Map.Entry<String, Key> entry : keybinds.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

}
