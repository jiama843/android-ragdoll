package com.cs349.john.a5;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.snatik.polygon.Line;
import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Component {

    // int part
    /*
     * 0 = TORSO
     * 1 = HEAD
     * 2 = UPPER_ARM
     * 3 = LOWER_ARM
     * 4 = UPPER_LEG
     * 5 = LOWER_LEG
     * 6 = HAND
     * 7 = FEET
     *
     */

    private String part;
    private Point originalAnchor;
    private Point anchor;

    private Bitmap image;
    private float imageX;
    private float imageY;

    private Paint paint;

    private float startX;
    private float startY;
    private Polygon shape;

    // This is the min/max "displacement" of angle from primary axis (NOT THE ABSOLUTE ANGLE)
    private float curr_degree;
    private float max_degree;
    private float min_degree;

    private List <Component> children = new ArrayList<>();

    //private AffineTransform transform = new AffineTransform(); // Our transformation matrix
    private Matrix transform;

    public Component(String p, Polygon poly){
        part = p;
        shape = poly;
        paint = new Paint();
        transform = new Matrix();

        curr_degree = 0;
        //setBitmap();
    }


    public Component(String p, Polygon poly, float sX, float sY){
        part = p;
        shape = poly;
        startX = sX;
        startY = sY;

        paint = new Paint();

        curr_degree = 0;

        transform = new Matrix();
        transform.postTranslate(startX, startY);
        //setBitmap();
    }


    public Component(String p, Polygon poly, float sX, float sY, float aX, float aY){
        part = p;
        shape = poly;
        startX = sX;
        startY = sY;

        anchor = new Point(aX, aY);
        originalAnchor = new Point(aX, aY);

        paint = new Paint();

        curr_degree = 0;

        transform = new Matrix();
        transform.postTranslate(startX, startY);
        //setBitmap();
    }


    public Component(String p, Polygon poly, float sX, float sY, float aX, float aY, float low, float high){
        part = p;
        shape = poly;
        startX = sX;
        startY = sY;

        anchor = new Point(aX, aY);
        originalAnchor = new Point(aX, aY);

        paint = new Paint();

        curr_degree = 0;

        max_degree = high;
        min_degree = low;

        transform = new Matrix();
        transform.postTranslate(startX, startY);
        //setBitmap();
    }


    public void setBitmap(Bitmap b, float x, float y){
        imageX = x;
        imageY = y;
        image = b;
    }


    public Polygon getShape(){
        return shape;
    }



    public void processChildren(){

    }


    public String getPart(){
        return part;
    }


    public void reset(){
        curr_degree = 0;
        transform = new Matrix();
        transform.postTranslate(startX, startY);

        if(!part.equalsIgnoreCase("TORSO")) {
            anchor = new Point(originalAnchor.x, originalAnchor.y);
        }

        for (int i = 0; i < children.size(); i++) {
            children.get(i).reset();
        }
    }


    public void addChild(Component c){
        children.add(c);
    }


    public void setAnchor(Point p){
        anchor = p;
    }


    public Point getAnchor(){ return anchor; }


    public Matrix getAffine(){
        return transform;
    }


    private List<Point> getPoints(Polygon p){

        List<Line> sides = p.getSides();
        List<Point> points = new ArrayList<>();

        for(int i = 0; i < sides.size(); i++){
            points.add(sides.get(i).getStart());
        }
        return points;
    }


    // Basic hit detection for now
    public boolean contains(float x, float y){
        if (x >= startX && x < (startX + image.getWidth()) && y >= startY && y < (startY + image.getHeight())) {
            return true;
        }
        return false;
    }


    // Draw head relative to an anchor and axis point
    public void drawComponent(Canvas c, Point anchor, Point axis){
        if(image != null) {
            //Bitmap transformedImage = Bitmap.createBitmap(
            //        image, (int) imageX, (int) imageY, image.getWidth(), image.getHeight(), transform, false);
            //c.drawBitmap(transformedImage, (float) imageX, (float) imageY, paint);
            c.drawBitmap(image, transform, paint);
        }
        drawPolygon(c);
        for(int i = 0; i < children.size(); i++){
            children.get(i).drawComponent(c, null, null);
        }
    }


    public Polygon transformAffine(Polygon s){
        List<Point> pts = getPoints(s);

        float[] fpts = new float[pts.size() * 2];
        for(int i = 0; i < pts.size(); i++){
            fpts[i * 2] = (float) pts.get(i).x;
            fpts[i * 2 + 1] = (float) pts.get(i).y;
        }

        transform.mapPoints(fpts);

        List<Point> tpts = new ArrayList<>();
        for(int i = 0; i < pts.size(); i++){
            tpts.add(new Point(fpts[i * 2], fpts[i * 2 + 1]));
        }

        Polygon.Builder pbuilder = new Polygon.Builder();
        for(int i = 0; i < tpts.size(); i++){
            pbuilder.addVertex(tpts.get(i));
        }

        Polygon p = pbuilder.build();
        return p;
    }


    public void applyScale(float scaleX, float scaleY){
        transform.postTranslate((float) -anchor.x, (float) -anchor.y);
        transform.postScale(scaleX, scaleY);
        transform.postTranslate((float) anchor.x, (float) anchor.y);

        for(int i = 0; i < children.size(); i++){
            children.get(i).scaleChild(transform, startX, startY);
        }
    }


    public void applyTranslation(float x, float y, float prevX, float prevY){

        transform.postTranslate(x - prevX, y - prevY);

        if(part != "TORSO") {
            anchor.x += x - prevX;
            anchor.y += y - prevY;
        }

        for(int i = 0; i < children.size(); i++){
            children.get(i).applyTranslation(x, y, prevX, prevY);
        }
    }


    public void applyRotation(float x, float y, float prevX, float prevY){

        double offsetX = x - anchor.x;
        double offsetY = y - anchor.y;

        double offsetPrevX = prevX - anchor.x;
        double offsetPrevY = prevY - anchor.y;

        double a1 = Math.atan2(offsetY, offsetX);
        double a2 = Math.atan2(offsetPrevY, offsetPrevX);

        double a_res = a1 - a2;
        a_res = Math.toDegrees(a_res);

        curr_degree += a_res;

        //System.out.println("CURR_DEGREE: "+curr_degree);

        transform.postTranslate((float) -anchor.x, (float) -anchor.y);
        transform.postRotate((float) a_res);
        transform.postTranslate((float) anchor.x, (float) anchor.y);

        for(int i = 0; i < children.size(); i++){
            children.get(i).rotate_child(anchor, (float) a_res); //.applyTransformation("rotate_child", x, y, prevX, prevY);
        }
    }


    public void scaleChild(Matrix trans, float prevStartX, float prevStartY){

        float[] fpts = new float[2];
        fpts[0] = (float) originalAnchor.x - prevStartX;
        fpts[1] = (float) originalAnchor.y - prevStartY;

        trans.mapPoints(fpts);

        float offsetX = fpts[0] - (float) anchor.x;
        float offsetY = fpts[1] - (float) anchor.y;

        transform.postTranslate(offsetX, offsetY);

        anchor.x = fpts[0];
        anchor.y = fpts[1];

        for(int i = 0; i < children.size(); i++){
            children.get(i).scaleTranslateChild(offsetX, offsetY);
        }
    }


    public void scaleTranslateChild(float offsetX, float offsetY){
        transform.postTranslate(offsetX, offsetY);

        anchor.x += offsetX;
        anchor.y += offsetY;

        for(int i = 0; i < children.size(); i++){
            children.get(i).scaleTranslateChild(offsetX, offsetY);
        }
    }


    public boolean rotateInRange(float x, float y, float prevX, float prevY){

        double offsetX = x - anchor.x;
        double offsetY = y - anchor.y;

        double offsetPrevX = prevX - anchor.x;
        double offsetPrevY = prevY - anchor.y;

        double a1 = Math.atan2(offsetY, offsetX);
        double a2 = Math.atan2(offsetPrevY, offsetPrevX);

        double a_res = a1 - a2;
        a_res = Math.toDegrees(a_res);

        if(part != "UPPER_ARM"){
            if(curr_degree + a_res <= min_degree) {
                return false;
            }
            else if(curr_degree + a_res >= max_degree){
                return false;
            }
        }
        return true;
    }


    // Helper to rotate children
    public void rotate_child(Point anc, float angle){
        transform.postTranslate((float) -anc.x, (float) -anc.y);
        transform.postRotate(angle);
        transform.postTranslate((float) anc.x, (float) anc.y);

        // Translate/rotate/translate anchor
        Matrix update_anchor = new Matrix();
        update_anchor.postTranslate((float) -anc.x, (float) -anc.y);
        update_anchor.postRotate(angle);
        update_anchor.postTranslate((float) anc.x, (float) anc.y);

        float[] fpts = new float[2];
        fpts[0] = (float) anchor.x;
        fpts[1] = (float) anchor.y;

        update_anchor.mapPoints(fpts);

        anchor.x = fpts[0];
        anchor.y = fpts[1];

        for(int i = 0; i < children.size(); i++){
            children.get(i).rotate_child(anc, angle);
        }
    }


    public boolean abs_contains(float x, float y){
        //System.out.println("Transform, preset: "+transform);

        Matrix inversetransform = new Matrix();
        inversetransform.set(transform);
        inversetransform.invert(inversetransform);

        float[] clickPos = {x, y};
        inversetransform.mapPoints(clickPos);

        if(shape.contains(new Point(clickPos[0], clickPos[1]))){
            return true;
        }
        else{
            return false;
        }
    }


    public void drawPolygon(Canvas c){
        Polygon p = transformAffine(shape);
        List<Line> lines = p.getSides();

        for(int i = 0; i < lines.size(); i++){
            c.drawLine((float) lines.get(i).getStart().x,
                    (float) lines.get(i).getStart().y,
                    (float) lines.get(i).getEnd().x,
                    (float) lines.get(i).getEnd().y,
                    paint);
        }
    }
}
