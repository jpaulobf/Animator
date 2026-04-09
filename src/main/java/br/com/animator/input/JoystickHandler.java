package br.com.animator.input;

import org.lwjgl.glfw.GLFW;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * JoystickDetector - Responsible for detecting the presence of controllers
 * and identifying their types to facilitate future remapping.
 */
public class JoystickHandler {

    private static final float AXIS_THRESHOLD = 0.15f;

    // Persistent state for change detection (state transitions)
    private final boolean[][] buttonHistory = new boolean[GLFW.GLFW_JOYSTICK_LAST + 1][64];
    private final float[][] axisHistory = new float[GLFW.GLFW_JOYSTICK_LAST + 1][64];
    private final byte[][] hatHistory = new byte[GLFW.GLFW_JOYSTICK_LAST + 1][64];
    private JoystickListener listener;

    /**
     * Interface to listen for Joystick button press events.
     */
    public interface JoystickListener {
        void onButtonPressed(int joystickId, int buttonId);
    }

    public void setJoystickListener(JoystickListener listener) {
        this.listener = listener;
    }

    /**
     * Inner class to store details of the detected controller.
     */
    public static class ControllerInfo {
        private final int id;
        private final String name;
        private final String guid;
        private final boolean isGamepad; // Whether it has a standard GLFW/SDL mapping

        public ControllerInfo(int id, String name, String guid, boolean isGamepad) {
            this.id = id;
            this.name = name;
            this.guid = guid;
            this.isGamepad = isGamepad;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGuid() {
            return guid;
        }

        public boolean isGamepad() {
            return isGamepad;
        }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | GUID: %s | Gamepad: %b", id, name, guid, isGamepad);
        }
    }

    /**
     * Checks if at least one joystick is connected to the system.
     * 
     * @return true if a device is present.
     */
    public boolean isAnyJoystickConnected() {
        for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_16; i++) {
            if (GLFW.glfwJoystickPresent(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are joysticks connected to the system.
     * If none are found, prints a message to the console and returns false.
     * 
     * @return true if at least one device is detected.
     */
    public boolean checkJoystickAvailability() {
        GLFW.glfwPollEvents();
        if (!isAnyJoystickConnected()) {
            System.out.println("No joysticks found.");
            return false;
        }
        return true;
    }

    /**
     * Scans all ports and returns a list of information about the connected
     * controllers.
     * Useful for identifying your 8BitDo specifically.
     */
    public List<ControllerInfo> getConnectedControllers() {
        List<ControllerInfo> controllers = new ArrayList<>();

        for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_16; i++) {
            if (GLFW.glfwJoystickPresent(i)) {
                String name = GLFW.glfwGetJoystickName(i);
                String guid = GLFW.glfwGetJoystickGUID(i);
                boolean isGamepad = GLFW.glfwJoystickIsGamepad(i);

                controllers.add(new ControllerInfo(
                        i,
                        name != null ? name : "Unknown Controller",
                        guid != null ? guid : "N/A",
                        isGamepad));
            }
        }

        return controllers;
    }

    /**
     * Initializes the GLFW input subsystem.
     * 
     * @return true if successfully initialized.
     */
    public boolean initialize() {
        if (!GLFW.glfwInit()) {
            System.err.println("Error: Failed to initialize GLFW.");
            return false;
        }
        return true;
    }

    /**
     * Updates the state of all connected joysticks and detects changes (Buttons,
     * Axes, and Hats).
     */
    public void update() {
        GLFW.glfwPollEvents();
        List<ControllerInfo> controllers = getConnectedControllers();

        for (ControllerInfo controller : controllers) {
            processButtons(controller);
            processAxes(controller);
            processHats(controller);
        }
    }

    private void processButtons(ControllerInfo controller) {
        ByteBuffer buttons = GLFW.glfwGetJoystickButtons(controller.getId());
        if (buttons == null)
            return;

        int jid = controller.getId();
        for (int b = 0; b < buttons.limit() && b < 64; b++) {
            boolean currentlyPressed = buttons.get(b) == GLFW.GLFW_PRESS;
            if (currentlyPressed && !buttonHistory[jid][b]) {
                // Notify the listener instead of just printing
                if (listener != null) {
                    listener.onButtonPressed(jid, b);
                }
            }
            buttonHistory[jid][b] = currentlyPressed;
        }
    }

    private void processAxes(ControllerInfo controller) {
        FloatBuffer axes = GLFW.glfwGetJoystickAxes(controller.getId());
        if (axes == null)
            return;

        int jid = controller.getId();
        for (int a = 0; a < axes.limit() && a < 64; a++) {
            float currentValue = axes.get(a);
            if (Math.abs(currentValue - axisHistory[jid][a]) > AXIS_THRESHOLD) {
                System.out.printf("[%s] Axis Moved: %d | Value: %.2f%n", controller.getName(), a, currentValue);
                axisHistory[jid][a] = currentValue;
            }
        }
    }

    private void processHats(ControllerInfo controller) {
        ByteBuffer hats = GLFW.glfwGetJoystickHats(controller.getId());
        if (hats == null)
            return;

        int jid = controller.getId();
        for (int h = 0; h < hats.limit() && h < 64; h++) {
            byte currentHat = hats.get(h);
            if (currentHat != hatHistory[jid][h]) {
                System.out.printf("[%s] DPAD/Hat Changed: %d | State: %d%n", controller.getName(), h, currentHat);
                hatHistory[jid][h] = currentHat;
            }
        }
    }
}
