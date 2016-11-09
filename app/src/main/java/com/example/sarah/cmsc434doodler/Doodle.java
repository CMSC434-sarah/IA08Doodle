package com.example.sarah.cmsc434doodler;

import android.graphics.Paint;
import android.graphics.Path;

import java.sql.Time;

/**
 * Created by Sarah on 11/7/2016.
 */

public class Doodle {

    private Paint _paint = new Paint();
    private Path _path = new Path();

    public Doodle(Path path, Paint paintDoodle){
        _paint = paintDoodle;
        _path = path;
    }

    public Path getPath(){
        return _path;
    }

    public Paint getPaint(){
        return _paint;
    }
}
