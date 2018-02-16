package com.example.quacheronies.cs301ahw2facemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.SurfaceView;

import java.util.Random;

/**
 * This class allows for the drawing of the face on the Surface View of the app
 *
 * @author Michael Quach
 * @created February 13, 2018
 *
 */

public class Face extends SurfaceView {
    //set the default color for the skin, eye, and hair to black. Set the hair style to default style at 0.
    private static int skinColor = Color.BLACK;
    private static int eyeColor = Color.BLACK;
    private static int hairColor = Color.BLACK;
    private static int hairStyle = 0;
    private Random gen = new Random(); //create a random value generator

    /**
     * Constructor for Face class
     *
     * @param context
     */
    public Face(Context context) { //constructor that calls on randomizer method
        super(context);
        general_init();
    }

    private void general_init()  {
        randomize(); //initializes the randomize method
    }


    public void setSkinColor(int red, int green, int blue) {
        skinColor = Color.rgb(red, green, blue); //set the skin color to a specific rgb color
        invalidate();

        /**
         External Citation
         Date: 14 February 2018
         Problem: Canvas not refreshing whenever I open the app up again
         Resource:
         https://stackoverflow.com/questions/10647558/when-its-necessary-to-execute-invalidate-on-a-view
         Solution: Calling invalidate() calls the OnDraw method whenever the previous drawing needs to be changed
         */
    }

    public void setEyeColor(int red, int green, int blue) {
        eyeColor = Color.rgb(red, green, blue); //set the eye color to a specific rgb color
        invalidate();
    }

    public void setHairColor(int red, int green, int blue) {
        hairColor = Color.rgb(red, green, blue); //set the hair color to a specific rgb color
        invalidate();
    }

    public void setHairStyle(int hair) {
        hairStyle = hair; //change the style of the hair based on an inputted value of 0-2
        invalidate();
    }

    public int getHairStyle() { //return and print the new hairstyle
        return hairStyle;
    }

    public int[] getColor(int faceColor) { //Retrieves color used whenever current color is changed
        int currentColor; //current color value

        switch (faceColor) {
            default: //hair color as default
                currentColor = hairColor;
                break;
            case 0: //eye color
                currentColor = eyeColor;
                break;
            case 1: //skin color
                currentColor = skinColor;
                break;
        }

        return new int[]{getRed(currentColor), getGreen(currentColor), getBlue(currentColor)}; //returns new color based on new rgb values
    }

    public int getRed(int color) { //allows for the retrival of the red component
        return color & 0x00ff0000; //initializes red
    }

    public int getGreen(int color) { //allows for the retrival of the green component
        return color & 0x0000ff00; //initializes green
    }

    public int getBlue(int color) { //allows for the retrival of the blue component
        return color & 0x000000ff; //initializes blue
    }

    public Paint paint(int color) { //paint method to use different colors
        Paint newColor = new Paint();
        newColor.setColor(color);
        return newColor;
    }

    public void randomize() { //initializes all the variables to randomly selected valid values
        //generate a random rgb value with a range of 0 to 255
        int red = gen.nextInt(255);
        int green = gen.nextInt(255);
        int blue = gen.nextInt(255);
        setSkinColor(red, green, blue); //set this random color to the skin color

        switch (gen.nextInt(3)) { //set the eye color to either a shade of red, blue, or brown
            case 0: //red eyes
                red = 200 + gen.nextInt(50);
                green = 0;
                blue = 0;
                break; //end the case
            case 1: //blue eyes
                red = 0;
                green = gen.nextInt(100);
                blue = 200 + gen.nextInt(50);
                break;
            default: //brown eyes (default)
                red = 50;
                green = 25;
                blue = 0;
                break;
            /**
             External Citation
             Date: 12 February 2018
             Problem: Needed a way to choose between multiple options
             Resource:
             https://developer.android.com/reference/android/widget/Switch.html
             Solution: Use Switch with cases allows me to set multiple options to choose from
             */
        }
        setEyeColor(red, green, blue); //set the eye color to the chosen value

        setHairColor(red, green, blue); //set the hair color to a random color

        setHairStyle(gen.nextInt(3)); //generate a random hair style between 3 options

        invalidate(); //call the onDraw method and redraw face
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onDraw(Canvas canvas) { //draws the face on the Surface View
        //gather dimensions of the Surface View
        int width = canvas.getWidth();
        int height = canvas.getHeight();


        switch (hairStyle) { //set the hair style to either of 3 options
            case 0: //draw a flat top on top of the face
                canvas.drawRect(Math.round((float) width/3), height/4 - 50, Math.round((float) width/3 * 2), height/4 + 50, paint(hairColor));
                break;
            case 1: //draw a bowl cut on top of the face
                canvas.drawRect(width/2 - 500, height/2, width/2 + 500, height/2 + 500, paint(hairColor));
                break;
            case 2: //draw a mini afro on top of the face
                canvas.drawCircle(width/2 - 500, height/2, 300, paint(hairColor));
                break;
        }

        // draw a general head to draw on
        canvas.drawOval(Math.round((float) width/4), Math.round((float) height/4), Math.round((float) width/4 * 3), height - 75, paint(skinColor));

        //draw a pair of eyes using the drawEye helper method
        drawEye(canvas, 800, 900, width/2 - 240, width/2 - 120);
        drawEye(canvas, 800, 900, width/2 + 120, width/2 + 240);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawEye(Canvas canvas, int top, int bottom, int left, int right) { //helper method the assist in drawing eye
        int diameter = right - left; //calculate diameter of eye

        //dimension the pupil of eye with size and shift
        int pupilSize = Math.round(diameter/2);
        int pupilShift = Math.round(diameter/2 - pupilSize/2);

        canvas.drawOval(left, top, right, bottom, paint(Color.WHITE)); //draw eye

        canvas.drawOval(left + pupilShift, top + pupilShift, right - pupilShift, bottom - pupilShift, paint(Color.BLACK)); //draw pupil of eye

    }
}
