package com.example.sarah.cmsc434doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.sql.Time;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Sarah on 11/2/2016.
 */

public class DoodleView extends View {

    private Stack<Doodle> _doodleStack;
    private Paint _paintDoodle = new Paint();
    private Path _path = new Path();


    public DoodleView(Context context) {
        super(context);
        initConstructor(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs){
        super(context, attrs);
        initConstructor(attrs,0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initConstructor(attrs, defStyle);
    }

    private void initConstructor(AttributeSet attrs, int defStyle){
        _doodleStack = new Stack<Doodle>();
        _paintDoodle.setColor(Color.BLACK);
        _paintDoodle.setAntiAlias(true);
        _paintDoodle.setStyle(Paint.Style.STROKE);
        _paintDoodle.setAlpha(255);
        _paintDoodle.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Iterator<Doodle> iter = _doodleStack.iterator();
        Doodle doodle = null;
        while(iter.hasNext()){
            doodle = iter.next();
            canvas.drawPath(doodle.getPath(),doodle.getPaint());
        }
        canvas.drawPath(_path, _paintDoodle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                _path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                _path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }

    public void clear() {
        this._doodleStack = new Stack<Doodle>();
        this._path = new Path();
        invalidate();
    }

    public int getOpacity(){
        return (int)(_paintDoodle.getAlpha()/2.55);
    }

    public void changeOpacity(int value){
        Paint newPaint = new Paint(_paintDoodle);
        _doodleStack.push(new Doodle(_path,_paintDoodle));
        _paintDoodle = newPaint;
        _paintDoodle.setAlpha((int)(value*2.55));
        _path = new Path();
    }

    public int getBrushSize(){
        return (int)_paintDoodle.getStrokeWidth();
    }

    public void changeBrushSize(int value){
        Paint newPaint = new Paint(_paintDoodle);
        _doodleStack.push(new Doodle(_path,_paintDoodle));
        _paintDoodle = newPaint;
        _paintDoodle.setStrokeWidth(value);
        _path = new Path();
    }

    public float[] getColor(){
        int color = _paintDoodle.getColor();
        float[] to_return = {0,0,0};
        ColorUtils.colorToHSL(color,to_return);
        return to_return;
    }

    public void changeColor(float[] hsl){
        Paint newPaint = new Paint(_paintDoodle);
        _doodleStack.push(new Doodle(_path,_paintDoodle));
        _paintDoodle = newPaint;
        _paintDoodle.setColor(ColorUtils.HSLToColor(hsl));
        _path = new Path();
    }

    public void set_doodleStack (Stack<Doodle> dstack){
        _doodleStack = dstack;
    }

    public Stack<Doodle> get_doodleStack(){
        return _doodleStack;
    }

    public Path get_path(){
        return _path;
    }

    public Paint get_paint(){
        return _paintDoodle;
    }

    public void set_paintDoodle(Paint p){
        _paintDoodle = p;
    }

    public void set_path(Path p){
        _path = p;
    }
}
