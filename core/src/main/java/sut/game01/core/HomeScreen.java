package sut.game01.core;

import playn.core.Font;

import playn.core.Image;

import playn.core.ImageLayer;

import playn.core.PlayN;

import playn.core.util.CallbackList;

import react.UnitSlot;

import tripleplay.game.ScreenStack;

import tripleplay.game.UIScreen;

import tripleplay.ui.*;

import tripleplay.ui.layout.AxisLayout;

import static playn.core.PlayN.assets;

import static playn.core.PlayN.graphics;

import static tripleplay.ui.Background.bordered;

public class HomeScreen extends UIScreen {


    public static final Font TITLE_FONT =

            PlayN.graphics().createFont("Helvetica",Font.Style.BOLD,24

            );

    private final ScreenStack ss;

    private Root root;

    public HomeScreen(ScreenStack ss){

        this.ss=ss;

    }

    @Override

    public void wasShown(){

        super.wasShown();

        Image bgImage = assets().getImage("images/bg2.png");

        bgImage.addCallback(new CallbackList<Image>(){

            @Override

            public void onSuccess(Image result){}

            @Override

            public void onFailure(Throwable cause){}

        });

        ImageLayer bgLayer = graphics().createImageLayer(bgImage);

        layer.add(bgLayer);

        root =iface.createRoot(AxisLayout.vertical().gap(15),

                SimpleStyles.newSheet(),layer);

        root.setSize(width(),height());



        root.add(new Label("Photo hunt game").addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));

                root.add(new Button("Start Play").onClick(new UnitSlot(){

                    public void onEmit(){

                        ss.push(new TestScreen(ss));

                    }

                }));
        root.add(new Button("Start Play").onClick(new UnitSlot(){

            public void onEmit(){

                ss.push(new TestScreen(ss));

            }

        }));

    }

}