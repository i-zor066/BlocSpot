package com.izor066.android.blocspot.ui;

import android.graphics.Color;

import java.util.Random;

import static android.graphics.Color.*;

/**
 * Created by igor on 4/10/15.
 */
public class UIUtils {

    public static int generateRandomColor(int baseColor) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        int baseRed = (baseColor & 0xFF0000) >> 16;
        int baseGreen = (baseColor & 0xFF00) >> 8;
        int baseBlue = baseColor & 0xFF;

        red = (red + baseRed) / 2;
        green = (green + baseGreen) / 2;
        blue = (blue + baseBlue) / 2;
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    public static float getHueFromRGB(int color) {
        int r = red(color);
        int g = green(color);
        int b = blue(color);
        float[] hsv = new float[3];
        Color.RGBToHSV(r,g,b,hsv);
        float hue = hsv[0];
        return hue;
    }
}
