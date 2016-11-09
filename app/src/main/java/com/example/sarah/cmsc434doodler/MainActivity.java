package com.example.sarah.cmsc434doodler;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;

import java.util.Stack;


public class MainActivity extends AppCompatActivity {

    private AlertDialog opacityDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clearDoodle(View v){
        Button button = (Button)findViewById(R.id.buttonClear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleView curDoodle = (DoodleView)findViewById(R.id.doodleView);
                curDoodle.clear();
            }
        });
    }

    public void opacitySelect(View v) {
        Button setOpacity = (Button)findViewById(R.id.buttonOpacity);
        setOpacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View opacityView = layoutInflater.inflate(R.layout.alert_layout, null);

                final SeekBar opacityBar = (SeekBar) opacityView.findViewById(R.id.seekBar);
                opacityBar.setMax(100);

                final DoodleView curDoodle = (DoodleView)findViewById(R.id.doodleView);
                opacityBar.setProgress(curDoodle.getOpacity());

                ((TextView)opacityView.findViewById(R.id.textViewMessage)).setText("Set Opacity to: ");

                final TextView opacityDialogValue = (TextView) opacityView.findViewById(R.id.textViewValue);
                opacityDialogValue.setText(Integer.toString(opacityBar.getProgress())+"%");

                opacityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        opacityDialogValue.setText(Integer.toString(progress)+"%");
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(opacityView);
                alertDialogBuilder.setNegativeButton("DONE",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save and everything here as well
                        curDoodle.changeOpacity(opacityBar.getProgress());
                        Toast.makeText(MainActivity.this,"Opacity set to "+ opacityBar.getProgress()+"%.",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void brushSizeSelect(View v) {
        Button setBrushWidth = (Button)findViewById(R.id.buttonSize);
        setBrushWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View brushSizeView = layoutInflater.inflate(R.layout.alert_layout, null);

                final SeekBar widthBar = (SeekBar) brushSizeView.findViewById(R.id.seekBar);
                widthBar.setMax(200);

                final DoodleView curDoodle = (DoodleView)findViewById(R.id.doodleView);
                widthBar.setProgress(curDoodle.getBrushSize());

                ((TextView)brushSizeView.findViewById(R.id.textViewMessage)).setText("Set Brush Size to: ");

                final TextView brushSizeDialogValue = (TextView) brushSizeView.findViewById(R.id.textViewValue);
                brushSizeDialogValue.setText(Integer.toString(widthBar.getProgress())+"px");

                widthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        brushSizeDialogValue.setText(Integer.toString(progress)+"px");
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(brushSizeView);
                alertDialogBuilder.setNegativeButton("DONE",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save and everything here as well
                        curDoodle.changeBrushSize(widthBar.getProgress());
                        Toast.makeText(MainActivity.this,"Brush Size set to "+ widthBar.getProgress()+"px.",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void colorSelect(View v) { ///just copy-pasted from above, need to change..all of it pretty much
        Button setColor = (Button)findViewById(R.id.buttonColor);
        setColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View colorView = layoutInflater.inflate(R.layout.color_alert_layout, null);

                final DoodleView curDoodle = (DoodleView)findViewById(R.id.doodleView);
                float[] colorAttr = curDoodle.getColor();
                int intAttr = (int)(colorAttr[0]);

                final SeekBar hueBar = (SeekBar) colorView.findViewById(R.id.seekBarHue);
                hueBar.setMax(360);
                hueBar.setProgress(intAttr);
                final TextView hueValue = (TextView) colorView.findViewById(R.id.textViewHueValue);
                hueValue.setText(Integer.toString(intAttr));

                intAttr = (int)(colorAttr[1]*100f);
                final SeekBar satBar = (SeekBar) colorView.findViewById(R.id.seekBarSat);
                satBar.setMax(100);
                satBar.setProgress(intAttr);
                final TextView satValue = (TextView) colorView.findViewById(R.id.textViewSatValue);
                satValue.setText(Integer.toString(intAttr));

                intAttr = (int)(colorAttr[2]*100f);
                final SeekBar lumBar = (SeekBar) colorView.findViewById(R.id.seekBarLum);
                lumBar.setMax(100);
                lumBar.setProgress(intAttr);
                final TextView lumValue = (TextView) colorView.findViewById(R.id.textViewLumValue);
                lumValue.setText(Integer.toString(intAttr));


                hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        hueValue.setText(Integer.toString(progress)+"/360");
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                satBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        satValue.setText(Double.toString(progress/100.0)+"/1.00"); //format later
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                lumBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        lumValue.setText(Double.toString(progress/100.0)+"/1.00"); //format later
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Color Settings");
                alertDialogBuilder.setView(colorView);
                alertDialogBuilder.setNegativeButton("DONE",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save and everything here as well
                        float[] hsl = {(float)hueBar.getProgress(),(float)satBar.getProgress()/100f,(float)lumBar.getProgress()/100f};
                        curDoodle.changeColor(hsl);
                        Toast.makeText(MainActivity.this,"Brush HSL Color set to "+hsl[0]+","+hsl[1]+","+hsl[2]+".",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void replay(View v) {

        Button replayButton = (Button) findViewById(R.id.buttonReplay);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                final DoodleView curDoodle = (DoodleView)findViewById(R.id.doodleView);
                final Stack<Doodle> doodles = curDoodle.get_doodleStack();
                Stack<Doodle> temp = new Stack<Doodle>();
                Paint tempPaint = curDoodle.get_paint();
                curDoodle.set_path(new Path());
                Path tempPath = curDoodle.get_path();
                int i =1;
                while(!doodles.isEmpty()){
                    temp.push(doodles.pop());
                }
                curDoodle.clear();
                while(!temp.isEmpty()){
                    doodles.push(temp.pop());
                    handler.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            curDoodle.set_doodleStack(doodles);
                            curDoodle.invalidate();
                        }
                    },(500* i));
                    i+=2;
                }
                curDoodle.set_paintDoodle(tempPaint);
                curDoodle.set_path(tempPath);
            }
        });
    }





//    public void replay( ){
//
//        super.draw(curCanvas);
//        Iterator<Doodle> iter = _doodleStack.iterator();
//        Doodle doodle = null;
//        int i = 1;
//        while(iter.hasNext()){
//            doodle = iter.next();
//            final Doodle finalDoodle = doodle;
//            _handler.postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    curCanvas.drawPath(finalDoodle.getPath(), finalDoodle.getPaint());
//                    invalidate();
//                }
//            },1000*i );
//        }
//        curCanvas.drawPath(_path, _paintDoodle);
//        invalidate();
//    }
}
