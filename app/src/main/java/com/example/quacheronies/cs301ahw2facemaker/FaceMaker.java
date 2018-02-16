package com.example.quacheronies.cs301ahw2facemaker;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 *
 * This was tested on a Nexus 9 tablet with Android Studio version 2.3.3
 * This provides the listeners for the Face class
 *
 * @author Michael Quach
 * @created February 13, 2018
 *
 */

public class FaceMaker extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    Face faceCanvas;
    SeekBar redSeek, greenSeek, blueSeek;
    TextView redLabel, greenLabel, blueLabel;
    Button randomFace;
    RadioGroup faceRadio;
    Spinner hairChange;

    /**
     External Citation
     Date: 14 February 2018
     Problem: I was not sure how to call on the Radio Buttons in app
     Resource:
     https://stackoverflow.com/questions/8323778/how-to-set-onclicklistener-on-a-radiobutton-in-android
     Solution: I was suggested to use a Radio Group instead for ease of use and less code
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) { //initializes and associates widgets with ID's
        //initializes the game state and layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_maker);

        //associates widgets with their ID's
        faceCanvas = (Face) findViewById(R.id.canvas);
        randomFace = (Button) findViewById(R.id.randomize);
        redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);
        redLabel = (TextView) findViewById(R.id.redText);
        greenLabel = (TextView) findViewById(R.id.greenText);
        blueLabel = (TextView) findViewById(R.id.blueText);
        faceRadio = (RadioGroup) findViewById(R.id.radioOptions);
        hairChange = (Spinner) findViewById(R.id.hairSelect);

        //link each widget to the event handler class
        randomFace.setOnClickListener(this);
        redSeek.setOnSeekBarChangeListener(this);
        greenSeek.setOnSeekBarChangeListener(this);
        blueSeek.setOnSeekBarChangeListener(this);
        faceRadio.setOnCheckedChangeListener(this);
        hairChange.setOnItemSelectedListener(this);
        
        //update buttons and seekbar with new change
        updateFace();

    }

    private void updateFace() {
        hairChange.setSelection(faceCanvas.getHairStyle()); //allows spinner to display list of options

        int currentColor[] = {0, 0, 0}; // initializes current color array to nothing

        switch (faceRadio.getCheckedRadioButtonId()) { //updates face based on radio buttons
            case R.id.hairButton:
                currentColor = faceCanvas.getColor(0);
                break;
            case R.id.eyeButton:
                currentColor = faceCanvas.getColor(1);
                break;
            case R.id.skinButton:
                currentColor = faceCanvas.getColor(2);
                break;
        }

        //updates seekbars and text with progress
        redSeek.setProgress(currentColor[0]);
        redLabel.setText("" + redSeek.getProgress());
        greenSeek.setProgress(currentColor[1]);
        greenLabel.setText("" + greenSeek.getProgress());
        blueSeek.setProgress(currentColor[2]);
        blueLabel.setText("" + blueSeek.getProgress());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) { //randomizes face in surface view after button pressed
            case R.id.randomize: //randomize face button
            randomFace.setClickable(false); //button is unclickable while randomizing

            faceCanvas.randomize(); //randomizes face

            updateFace(); //update all functions in app
            randomFace.setClickable(true); //allow for a random face to be produced after random face button is pressed
            break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //adjust the seekbars based on progress change or radio buttons
        switch (faceRadio.getCheckedRadioButtonId()) {
            case R.id.hairButton:
                faceCanvas.setSkinColor(redSeek.getProgress(), greenSeek.getProgress(), blueSeek.getProgress());
                break;
            case R.id.eyeButton:
                faceCanvas.setEyeColor(redSeek.getProgress(), greenSeek.getProgress(), blueSeek.getProgress());
                break;
            case R.id.skinButton:
                faceCanvas.setHairColor(redSeek.getProgress(), greenSeek.getProgress(), blueSeek.getProgress());
                break;
        }


        switch (seekBar.getId()) {
            case R.id.redSeekBar:
                redLabel.setText("" + progress);
                break;
            case R.id.greenSeekBar:
                greenLabel.setText("" + progress);
                break;
            case R.id.blueSeekBar:
                blueLabel.setText("" + progress);
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { //touch tracking not important

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { //touch tracking not important

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { //pointer capture not important

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //allows the hair to change based on spinner selection
        faceCanvas.setHairStyle(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { //do nothing when nothing is selected

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) { //update the face based on the radio button selection
        if (group.getId() == R.id.radioOptions) {
            this.updateFace();
        }
    }
}
