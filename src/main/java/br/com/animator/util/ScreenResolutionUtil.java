package br.com.animator.util;

import java.util.ArrayList;
import java.util.List;
import br.com.animator.window.WindowMode;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.DisplayMode;

public class ScreenResolutionUtil {
    
    public List<WindowMode> getAvailableResolutionList() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();       
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode[] modes = gd.getDisplayModes();
        List<WindowMode> resolutionList = new ArrayList<>();

        for (DisplayMode mode : modes) {
            int width = mode.getWidth();
            int height = mode.getHeight();
            int refreshRate = mode.getRefreshRate();
            int bitDepth = mode.getBitDepth();
            resolutionList.add(new WindowMode(width, height, refreshRate, bitDepth));
        }

        return resolutionList;
    }
}
