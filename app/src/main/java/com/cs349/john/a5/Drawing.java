package com.cs349.john.a5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.snatik.polygon.Line;
import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;


import java.util.*;
import java.util.jar.Attributes;

public class Drawing extends View {

    private Paint paint;
    private Bitmap test;

    private Polygon head;
    private Polygon torso;

    // Left arm/hand
    private Polygon handleft;
    private Polygon upperarmleft;
    private Polygon lowerarmleft;

    // Right arm/hand
    private Polygon handright;
    private Polygon upperarmright;
    private Polygon lowerarmright;

    // Left leg/foot
    private Polygon footleft;
    private Polygon upperlegleft;
    private Polygon lowerlegleft;

    // Right leg/foot
    private Polygon footright;
    private Polygon upperlegright;
    private Polygon lowerlegright;


    // Components
    private Component headC;
    private Component torsoC;

    // Left arm/hand
    private Component handleftC;
    private Component upperarmleftC;
    private Component lowerarmleftC;

    // Right arm/hand
    private Component handrightC;
    private Component upperarmrightC;
    private Component lowerarmrightC;

    // Left leg/foot
    private Component footleftC;
    private Component upperlegleftC;
    private Component lowerlegleftC;

    // Right leg/foot
    private Component footrightC;
    private Component upperlegrightC;
    private Component lowerlegrightC;


    List<Polygon> componentPoly = new ArrayList<>();
    List<Component> components = new ArrayList<>();

    Component curr_component = null;
    Component curr_component2 = null;

    float prevX = 0;
    float prevY = 0;

    private ScaleGestureDetector scaleGestureDetector;

    public Drawing(Context c){
        super(c);

        initDrawing();
    }

    public Drawing(Context c, AttributeSet attr){
        super(c, attr);

        initDrawing();
    }

    public Drawing(Context c, AttributeSet attr, int styleAttr){
        super(c, attr, styleAttr);

        initDrawing();
    }

    public void initDrawing(){
        paint = new Paint();

        // Initialize Polygons
        torso = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(375, 0))
                .addVertex(new Point(375, 450))
                .addVertex(new Point(0, 450)).build();

        head = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(150, 0))
                .addVertex(new Point(150, 150))
                .addVertex(new Point(0, 150)).build();

        upperarmright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(200, 0))
                .addVertex(new Point(200, 200))
                .addVertex(new Point(0, 200)).build();

        upperarmleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(200, 0))
                .addVertex(new Point(200, 200))
                .addVertex(new Point(0, 200)).build();

        upperlegleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(50, 0))
                .addVertex(new Point(50, 125))
                .addVertex(new Point(0, 125)).build();

        upperlegright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(50, 0))
                .addVertex(new Point(50, 125))
                .addVertex(new Point(0, 125)).build();

        lowerarmright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(200, 0))
                .addVertex(new Point(200, 150))
                .addVertex(new Point(0, 150)).build();

        lowerarmleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(200, 0))
                .addVertex(new Point(200, 150))
                .addVertex(new Point(0, 150)).build();

        lowerlegleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(50, 0))
                .addVertex(new Point(50, 100))
                .addVertex(new Point(0, 100)).build();

        lowerlegright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(50, 0))
                .addVertex(new Point(50, 100))
                .addVertex(new Point(0, 100)).build();

        handright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(100, 0))
                .addVertex(new Point(100, 100))
                .addVertex(new Point(0, 100)).build();

        handleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(100, 0))
                .addVertex(new Point(100, 100))
                .addVertex(new Point(0, 100)).build();

        footright = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(100, 0))
                .addVertex(new Point(100, 100))
                .addVertex(new Point(0, 100)).build();

        footleft = Polygon.Builder()
                .addVertex(new Point(0, 0))
                .addVertex(new Point(100, 0))
                .addVertex(new Point(100, 100))
                .addVertex(new Point(0, 100)).build();

        // Initialize Components
        torsoC = new Component("TORSO", torso, 375, 400);
        headC = new Component("HEAD", head, 525, 275, 600, 350, -25, 25);

        upperarmrightC = new Component("UPPER_ARM", upperarmright, 600, 400, 600, 550);
        upperarmleftC = new Component("UPPER_ARM", upperarmleft, 325, 400, 525, 550);

        lowerarmrightC = new Component("LOWER_ARM", lowerarmright, 725, 400, 725, 400, -135, 0);
        lowerarmleftC = new Component("LOWER_ARM", lowerarmleft, 200, 400, 400, 400, 0, 135);

        upperlegrightC = new Component("UPPER_LEG", upperlegright, 600, 825, 625, 825, -90, 0);
        upperlegleftC = new Component("UPPER_LEG", upperlegleft, 500, 825, 525, 825, 0,  90);

        lowerlegrightC = new Component("LOWER_LEG", lowerlegright, 600, 950, 625, 950, -90, 0);
        lowerlegleftC = new Component("LOWER_LEG", lowerlegleft, 500, 950, 525, 950, 0, 90);

        handrightC = new Component("HAND", handright, 900, 350, 900, 450, 0, 30);
        handleftC = new Component("HAND", handleft,125, 350, 225, 450, -30, 0);

        footrightC = new Component("FOOT", footright,575, 1050, 625, 1050, -35, 0);
        footleftC = new Component("FOOT", footleft, 475, 1050, 525, 1050, 0, 35);

        // Add component Polygons in a layered order for hit detection
        componentPoly.add(torso);

        componentPoly.add(head);
        componentPoly.add(upperarmright);
        componentPoly.add(upperarmleft);
        componentPoly.add(upperlegright);
        componentPoly.add(upperlegleft);

        componentPoly.add(lowerarmright);
        componentPoly.add(lowerarmleft);
        componentPoly.add(lowerlegright);
        componentPoly.add(lowerlegleft);

        componentPoly.add(handright);
        componentPoly.add(handleft);
        componentPoly.add(footright);
        componentPoly.add(footleft);

        // Add components
        components.add(torsoC);

        components.add(headC);
        components.add(upperarmrightC);
        components.add(upperarmleftC);
        components.add(upperlegrightC);
        components.add(upperlegleftC);

        components.add(lowerarmrightC);
        components.add(lowerarmleftC);
        components.add(lowerlegrightC);
        components.add(lowerlegleftC);

        components.add(handrightC);
        components.add(handleftC);
        components.add(footrightC);
        components.add(footleftC);


        // Configure children
        torsoC.addChild(headC);
        torsoC.addChild(upperarmrightC);
        torsoC.addChild(upperarmleftC);
        torsoC.addChild(upperlegrightC);
        torsoC.addChild(upperlegleftC);

        upperarmrightC.addChild(lowerarmrightC);
        upperarmleftC.addChild(lowerarmleftC);
        upperlegrightC.addChild(lowerlegrightC);
        upperlegleftC.addChild(lowerlegleftC);

        lowerarmrightC.addChild(handrightC);
        lowerarmleftC.addChild(handleftC);
        lowerlegrightC.addChild(footrightC);
        lowerlegleftC.addChild(footleftC);

        // Set translations according to torso
        //upperarmrightC.getAffine().postTranslate()
        //torsoC.getAffine().postTranslate(30,30);
        setComponentBitmaps();

        // Set scale gesture detector
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        // Reset torso position
        torsoC.reset();

        // set listener
        configureTouch(this);
        invalidate();
    }


    private List<Point> getPoints(Polygon p){

        List<Line> sides = p.getSides();
        List<Point> points = new ArrayList<Point>();

        for(int i = 0; i < sides.size(); i++){
            points.add(sides.get(i).getStart());
        }
        return points;
    }


    public void reset(){

        // Reset torso position
        torsoC.reset();
        invalidate();
    }


    public void setComponentBitmaps(){
        Bitmap torsoB = BitmapFactory.decodeResource(getResources(), R.drawable.torso);
        torsoB = Bitmap.createScaledBitmap(torsoB, 400, 450, false);

        Bitmap headB = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        headB = Bitmap.createScaledBitmap(headB, 150, 150, false);

        // Arms
        Bitmap upperarmrightB = BitmapFactory.decodeResource(getResources(), R.drawable.upperrightarm);
        upperarmrightB = Bitmap.createScaledBitmap(upperarmrightB, 200, 200, false);

        Bitmap upperarmleftB = BitmapFactory.decodeResource(getResources(), R.drawable.upperleftarm);
        upperarmleftB = Bitmap.createScaledBitmap(upperarmleftB, 200, 200, false);

        Bitmap lowerarmrightB = BitmapFactory.decodeResource(getResources(), R.drawable.lowerrightarm);
        lowerarmrightB = Bitmap.createScaledBitmap(lowerarmrightB, 200, 150, false);

        Bitmap lowerarmleftB = BitmapFactory.decodeResource(getResources(), R.drawable.lowerleftarm);
        lowerarmleftB = Bitmap.createScaledBitmap(lowerarmleftB, 200, 150, false);

        Bitmap handrightB = BitmapFactory.decodeResource(getResources(), R.drawable.righthand);
        handrightB = Bitmap.createScaledBitmap(handrightB, 100, 100, false);

        Bitmap handleftB = BitmapFactory.decodeResource(getResources(), R.drawable.lefthand);
        handleftB = Bitmap.createScaledBitmap(handleftB, 100, 100, false);


        //Legs
        Bitmap upperlegrightB = BitmapFactory.decodeResource(getResources(), R.drawable.upperrightleg);
        upperlegrightB = Bitmap.createScaledBitmap(upperlegrightB, 50, 150, false);

        Bitmap upperlegleftB = BitmapFactory.decodeResource(getResources(), R.drawable.upperleftleg);
        upperlegleftB = Bitmap.createScaledBitmap(upperlegleftB, 50, 150, false);

        Bitmap lowerlegrightB = BitmapFactory.decodeResource(getResources(), R.drawable.lowerrightleg);
        lowerlegrightB = Bitmap.createScaledBitmap(lowerlegrightB, 50, 150, false);

        Bitmap lowerlegleftB = BitmapFactory.decodeResource(getResources(), R.drawable.lowerleftleg);
        lowerlegleftB = Bitmap.createScaledBitmap(lowerlegleftB, 50, 150, false);

        Bitmap footrightB = BitmapFactory.decodeResource(getResources(), R.drawable.rightfoot);
        footrightB = Bitmap.createScaledBitmap(footrightB, 100, 100, false);

        Bitmap footleftB = BitmapFactory.decodeResource(getResources(), R.drawable.leftfoot);
        footleftB = Bitmap.createScaledBitmap(footleftB, 100, 100, false);

        // Add to torso
        torsoC.setBitmap(torsoB, 375, 400);
        headC.setBitmap(headB, 525, 275);

        upperarmrightC.setBitmap(upperarmrightB, 600, 400);
        upperarmleftC.setBitmap(upperarmleftB, 325, 400);
        lowerarmrightC.setBitmap(lowerarmrightB, 725, 400);
        lowerarmleftC.setBitmap(lowerarmleftB, 200, 400);

        upperlegrightC.setBitmap(upperlegrightB, 600, 825);
        upperlegleftC.setBitmap(upperlegleftB, 500, 825);
        lowerlegrightC.setBitmap(lowerlegrightB, 600, 950);
        lowerlegleftC.setBitmap(lowerlegleftB, 500, 950);

        handrightC.setBitmap(handrightB, 900, 350);
        handleftC.setBitmap(handleftB, 125, 350);
        footrightC.setBitmap(footrightB, 575, 1050);
        footleftC.setBitmap(footleftB, 475, 1050);

        // = .getDrawable(R.drawable.garbage, null);
        //test.setBounds(left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //canvas.drawText("HI BOIIIIIIII", 100, 100, paint);
        //canvas.drawBitmap(test, 130,130, paint);

        // Define the end points
        //canvas.drawText("HI BOIIIIIIII", 900, 1450, paint);

        for(int i = 0; i < components.size(); i++){
            components.get(i).drawComponent(canvas, null, null);
        }
    }


    public void configureTouch(final View curr){

        curr.setOnTouchListener(new OnTouchListener() {

            // Documentation: https://developer.android.com/training/graphics/opengl/touch#java
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                // Handle multiple touches later

                float x = e.getX();
                float y = e.getY();

                //System.out.println("X:"+x+", Y:"+y);
                //System.out.println("PrevX:"+prevX+", PrevY:"+prevY);
                //System.out.println("Component clicked");
                //System.out.println(e.getAction());

                switch (e.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        //System.out.println("Component ACTION_DOWN");

                        for(int i = components.size() - 1; i >= 0; i--){
                            if(components.get(i).abs_contains(x, y)){
                                curr_component = components.get(i);
                                break;
                            }
                        }

                        if(curr_component == null){
                            System.out.println("Component not detected");
                            return true;
                        }

                        prevX = x;
                        prevY = y;

                        break;

                    case MotionEvent.ACTION_MOVE:

                        if (curr_component == null) {
                            System.out.println("Component not detected");
                            return true;
                        }

                        //System.out.println("Component ACTION_MOVE");
                        if (curr_component.getPart().equalsIgnoreCase("TORSO")) {

                            curr_component.applyTranslation(x, y, prevX, prevY);

                        } else if (curr_component.getPart().equalsIgnoreCase("UPPER_ARM")
                                || curr_component.getPart().equalsIgnoreCase("LOWER_ARM")
                                || curr_component.getPart().equalsIgnoreCase("HAND")
                                || curr_component.getPart().equalsIgnoreCase("FOOT")) {

                            if (curr_component.rotateInRange(x, y, prevX, prevY)) {
                                curr_component.applyRotation(x, y, prevX, prevY);
                            }
                        } else { // UPPER / LOWER LEGS

                            // Check if we aren't dealing with scaling
                            if (curr_component.rotateInRange(x, y, prevX, prevY)) {
                                curr_component.applyRotation(x, y, prevX, prevY);
                            }
                        }

                        invalidate();
                        prevX = x;
                        prevY = y;

                        break;

                    case MotionEvent.ACTION_UP:
                        //System.out.println("ACTION_UP CALLED");
                        curr_component = null;

                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:

                        for(int i = components.size() - 1; i >= 0; i--){
                            if(components.get(i).abs_contains(x, y)){
                                curr_component2 = components.get(i);
                                break;
                            }
                        }

                        if(curr_component2 == null){
                            System.out.println("Component2 not detected");
                            return true;
                        }

                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        curr_component2 = null;

                        break;
                }

                if(curr_component != null && curr_component2 != null && curr_component.equals(curr_component2)){
                    scaleGestureDetector.onTouchEvent(e);
                }
                return true;
            }

        });
    }

    // With help from the following tutorial: http://android-er.blogspot.com/2016/05/implement-pinch-to-zoom-with.html
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        float scaleX;
        float scaleY;

        public ScaleListener() {
            super();
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            scaleX = 1;
            scaleY = 1;

            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scaleX = detector.getScaleFactor(); //detector.getCurrentSpanX();
            scaleY = detector.getScaleFactor(); //detector.getCurrentSpanY();
            //System.out.println("Scale X: "+ scaleX+", Scale Y: "+scaleY);

            if(curr_component.getPart().equalsIgnoreCase("UPPER_LEG")
                || curr_component.getPart().equalsIgnoreCase("LOWER_LEG")){
                curr_component.applyScale(scaleX, scaleY);
            }

            invalidate();

            return true;
        }
    }
}