package sut.game01.core.Sprite;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sun.security.acl.WorldGroupImpl;
import sut.game01.core.TestScreen;

import javax.swing.*;

/**
 * Created by all user on 28/1/2557.
 */
public class Zealot {

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;






    public enum State {
        IDLER, RUNR, ATTKR,IDLEL,RUNL,ATTKL
    }

    private State state = State.IDLER;
    private float x=24.0f;
    private float y=3.0f;

    private  int e = 0;
    private  int offset = 0;

    public Zealot(final World world ,final float x, final float y){
        sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                int spriteIndex = 0;
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                body = initPhysicsBody(world,TestScreen.M_PER_PIXEL*x,TestScreen.M_PER_PIXEL*y);
                hasLoaded=true;

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }

            });
            PlayN.keyboard().setListener(new Keyboard.Listener() {
                @Override
                public void onKeyDown(Keyboard.Event event) {
                    if (event.key() == Key.Z) {

                        if (state == State.IDLER) {
                            state = State.ATTKR;
                            spriteIndex = -1;
                            e = 0;
                        }
                        if (state == State.IDLEL) {
                            state = State.ATTKL;
                            spriteIndex = 0;
                            e = 0;
                        }
                    }
                    if (event.key() == Key.RIGHT) {
                        state = State.RUNR;
                        spriteIndex = 1;
                        e = 0;
                    }
                    if (event.key() == Key.LEFT) {
                        state = State.RUNL;
                        spriteIndex = 1;
                        e = 0;
                    }



                }

                @Override
                public void onKeyTyped(Keyboard.TypedEvent event) {

                }

                @Override
                public void onKeyUp(Keyboard.Event event) {
                    if (event.key() == Key.RIGHT) {
                        state = State.IDLER;
                        spriteIndex = 0;
                        e = 0;
                        body.applyForce(new Vec2(-200f,-800f),body.getPosition());


                    }
                    if (event.key() == Key.LEFT) {
                        state = State.IDLEL;
                        spriteIndex = 0;
                        e = 0;
                        body.applyForce(new Vec2(200f,-800f),body.getPosition());

                    }

                }
            });

        }
            public Layer layer(){
                return sprite.layer();
            }

            public void update(int delta) {
                //update(delta);
                if (!hasLoaded) return;
                e += delta;

                if(e > 170){
                    switch (state){
                        case IDLER: offset = 0;
                            break;

                        case ATTKR: offset = 4;
                            if(spriteIndex == 3){
                                state = State.IDLER;
                            }
                            break;

                    }
                    spriteIndex = offset + ((spriteIndex +1) % 4);
                    sprite.setSprite(spriteIndex);
                    e = 0;
                }





            }



    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(96 * TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.density=0.4f;
        fixtureDef.friction=0.1f;

        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public void paint(Clock clock){
        if (!hasLoaded) return;
        sprite.layer().setTranslation(

                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);


    }

}


