package mmaximus.ez.l_system;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;

public class DrawActivity extends AppCompatActivity {
    float angle;
    int coordX, coordY;
    int lineLength = 10;
    String axiom;
    String q;
    boolean firstDraw = true;
    int coordXMax, coordYMax,coordYMin,coordXMin;
    boolean translate = false;
    float endXCoord = 0, endYCoord = 0, startXCoord = 0, startYCoord = 0;
    int x = 0,y = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));

        Intent intent = getIntent();

        axiom = intent.getStringExtra("axiom");
        q = intent.getStringExtra("q");

    }

    class DrawView extends View {
        Paint p;
        DrawView d2d;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            d2d = this;
        }

        @Override
        protected void onDraw(final Canvas canvas)
        {
            canvas.translate(x + startXCoord-endXCoord,y + startYCoord-endYCoord);
            x = (int)(x - (startXCoord-endXCoord));
            y = (int)(y - (startYCoord-endYCoord));
            startXCoord = endXCoord;
            startYCoord = endYCoord;

            coordX = canvas.getWidth()/2;
            coordY = canvas.getHeight()/2;
            angle = 0;
            Stack angleSt = new Stack();
            Stack coordXSt = new Stack();
            Stack coordYSt = new Stack();
            if(firstDraw == true)
            {
                coordXMin = coordX;
                coordXMax = coordXMin;
                coordYMin = coordY;
                coordYMax = coordYMin;
            }
            for (int j = 0; j < axiom.length(); j++) {
                if (axiom.charAt(j) == 'F') {
                    p.setColor(Color.BLACK);
                    canvas.drawLine(coordX, coordY, (int) (coordX + lineLength * Math.cos(Math.toRadians(angle))),
                            (int) (coordY + lineLength * Math.sin(Math.toRadians(angle))), p);

                    coordX = (int) (coordX + lineLength * Math.cos(Math.toRadians(angle)));
                    coordY = (int) (coordY + lineLength * Math.sin(Math.toRadians(angle)));
                    if(coordX > coordXMax) coordXMax = coordX;
                    if(coordY > coordYMax) coordYMax = coordY;
                    if(coordX < coordXMin) coordXMin = coordX;
                    if(coordY < coordYMin) coordYMin = coordY;
                }
                if (axiom.charAt(j) == '[') {
                    angleSt.push(angle);
                    coordXSt.push(coordX);
                    coordYSt.push(coordY);
                }
                if (axiom.charAt(j) == ']') {
                    coordX = (int)coordXSt.pop();
                    coordY = (int)coordYSt.pop();
                    angle = (float)angleSt.pop();
                }
                if (axiom.charAt(j) == '+') {
                    angle = (angle + Float.parseFloat(q));
                }
                if (axiom.charAt(j) == '-') {
                    angle = (angle - Float.parseFloat(q));
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                startXCoord = event.getX();
                startYCoord = event.getY();
                translate = true;
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                endXCoord = event.getX();
                endYCoord = event.getY();
                d2d.invalidate();
            }
            return true;
        }
    }
}
