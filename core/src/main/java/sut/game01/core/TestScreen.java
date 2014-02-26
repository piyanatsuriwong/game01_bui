package sut.game01.core;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.*;
import playn.core.*;


import playn.core.util.CallbackList;

import playn.core.util.Clock;
import react.UnitSlot;

import sut.game01.core.Sprite.Sprite;
import sut.game01.core.Sprite.Zealot;

import tripleplay.game.Screen;

import tripleplay.game.ScreenStack;

import tripleplay.game.UIScreen;

import tripleplay.ui.*;

import tripleplay.ui.layout.AxisLayout;

import tripleplay.game.UIScreen;

import javax.swing.*;

import static playn.core.PlayN.*;

import static tripleplay.ui.Background.bordered;

/**

 * Created by all user on 21/1/2557.

 */


public class TestScreen extends UIScreen {
    
    public  static float M_PER_PIXEL=1/26.666667f;
    //size of world
    private static int width = 24;
    private static int  height = 18;
    private World world;
    private Sprite sprite;
    private DebugDrawBox2D debugDraw;




    private final ScreenStack ss;
    private Zealot z;
    private boolean showDebugDraw = true;
    private JLayeredPane body;

    public TestScreen(ScreenStack ss){

        this.ss=ss;

    }

    @Override

    public void wasAdded(){

        Vec2 gravity = new Vec2(0.0f,10f);
        world=new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        z = new Zealot(world,300f,300f);

        layer.add( z.layer());

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / TestScreen.M_PER_PIXEL),
                    (int) (height / TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit  |
                               DebugDraw.e_jointBit |
                               DebugDraw.e_aabbBit);

            debugDraw.setCamera(0,0,1f / TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }


        createBox();

        Image actionIm = assets().getImage("images/ac.png");
        ImageLayer actionLa = graphics().createImageLayer(actionIm);
        graphics().rootLayer().add(actionLa);
        layer.add(actionLa);
        actionLa.setTranslation(101, 101);

        actionLa.addListener(new Pointer.Adapter(){
           public void  onPointerEnd(Pointer.Event event){



           }
        });

    }

    private void createBox(){
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);
        Body body =world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);
        FixtureDef fd = new FixtureDef();
        fd.shape=shape;
        fd.density=0.1f;
        fd.friction=0.1f;
        fd.restitution=1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(13f, 15f), 0);
    }





    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
        world.step(0.033f, 10, 10);

    }

    @Override
    public void paint(Clock clock){
        super.paint(clock);
        z.paint(clock);

        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(4f,height-2),
                                new Vec2(width-4f,height-2));
        ground.createFixture(groundShape,0.0f);




    }
}