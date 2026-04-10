package br.com.animator.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps physical inputs (Keyboard/Joystick) to logical GameActions.
 */
public class ButtonMapper {

    private static final Map<Integer, GameAction> keyboardMap = new HashMap<>();
    private static final Map<Integer, GameAction> joystickMap = new HashMap<>();
    private static final Map<Byte, GameAction> hatMap = new HashMap<>();

    static {
        // Default Keyboard Mapping
        keyboardMap.put(KeyEvent.VK_UP, GameAction.UP);
        keyboardMap.put(KeyEvent.VK_DOWN, GameAction.DOWN);
        keyboardMap.put(KeyEvent.VK_LEFT, GameAction.LEFT);
        keyboardMap.put(KeyEvent.VK_RIGHT, GameAction.RIGHT);
        keyboardMap.put(KeyEvent.VK_ENTER, GameAction.START);
        keyboardMap.put(KeyEvent.VK_ESCAPE, GameAction.SELECT);
        keyboardMap.put(KeyEvent.VK_Z, GameAction.BUTTON_1);
        keyboardMap.put(KeyEvent.VK_X, GameAction.BUTTON_2);
        keyboardMap.put(KeyEvent.VK_C, GameAction.BUTTON_3);
        keyboardMap.put(KeyEvent.VK_V, GameAction.BUTTON_4);
        // Add more keys as needed

        // Default Joystick Mapping (example indices)
        joystickMap.put(0, GameAction.BUTTON_1); // A
        joystickMap.put(1, GameAction.BUTTON_2); // B
        joystickMap.put(7, GameAction.START);    // Start
        joystickMap.put(6, GameAction.SELECT);   // Select

        // Default Hat/DPAD Mapping (GLFW Values)
        hatMap.put((byte) 1, GameAction.UP);    // GLFW_HAT_UP
        hatMap.put((byte) 2, GameAction.RIGHT); // GLFW_HAT_RIGHT
        hatMap.put((byte) 4, GameAction.DOWN);  // GLFW_HAT_DOWN
        hatMap.put((byte) 8, GameAction.LEFT);  // GLFW_HAT_LEFT
    }

    public static GameAction getKeyboardAction(int keyCode) {
        return keyboardMap.get(keyCode);
    }

    public static GameAction getJoystickAction(int buttonCode) {
        return joystickMap.get(buttonCode);
    }

    public static GameAction getHatAction(byte state) {
        return hatMap.get(state);
    }

    public static void remapKeyboard(int keyCode, GameAction action) {
        keyboardMap.put(keyCode, action);
    }

    public static void remapJoystick(int buttonCode, GameAction action) {
        joystickMap.put(buttonCode, action);
    }
}