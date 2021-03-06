package com.xqs.mypaoku.actor.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.xqs.mypaoku.MyPaokuGame;
import com.xqs.mypaoku.actor.SoundHelper;
import com.xqs.mypaoku.actor.base.BaseImageActor;
import com.xqs.mypaoku.app.Prefs;
import com.xqs.mypaoku.res.Res;
import com.xqs.mypaoku.util.Util;

/**
 * Created by Administrator on 2017/4/16 0016.
 */

public class LevelIcon extends BaseImageActor implements BaseImageActor.TimerListener {
    public static final String TAG = "LevelIcon";

    public static final String BTN_LOCK_ON="images/btn_lock_on.png";
    public static final String BTN_LOCK_OFF="images/btn_lock_off.png";

    private Texture onTexture;
    private Texture offTexture;

    private TextureRegion onRegion;
    private TextureRegion offRegion;

    private BitmapFont bitmapFont;
    private float textWidth;
    private float textHeight;

    private int level;

    private int passLevelNum=0;


    public LevelIcon(MyPaokuGame mainGame, final int fromLevel) {
        super(mainGame);

        this.level = fromLevel;

        onTexture = new Texture(Gdx.files.internal(BTN_LOCK_ON));
        offTexture = new Texture(Gdx.files.internal(BTN_LOCK_OFF));

        onRegion=new TextureRegion(onTexture);
        offRegion=new TextureRegion(offTexture);

        setTimerListener(this);

        initFont();

        updateIcon();

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(level<=(passLevelNum+1)) {
                    SoundHelper.playButtonSound();
                    getMainGame().showGameScreen(level-1);
                }
            }
        });
    }


    public void initFont(){
        bitmapFont = getMainGame().getFpsBitmapFont();
        bitmapFont.getData().setScale(0.5f);
    }

    public void updateIcon(){
        passLevelNum= Prefs.getPrefs().getPassedLevel();
        if(level<=(passLevelNum+1)) {
            setRegion(onRegion);
        }else{
            setRegion(offRegion);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(level<=(passLevelNum+1)) {
            GlyphLayout layout = new GlyphLayout();
            layout.setText(bitmapFont, "" + level);
            textWidth = layout.width;
            textHeight = layout.height;
            bitmapFont.setColor(Color.valueOf(Res.Color.FONT_LEVEL));
            bitmapFont.getData().setScale(0.4f);
            bitmapFont.draw(batch, "" + level, this.getX() + (getWidth() / 2 - textWidth / 2), this.getY() + this.getHeight() - (getHeight() / 2 - textHeight / 2));
        }
    }

    @Override
    public void orderAct(float delta, int counter) {
        if(counter%4==0) {
            updateIcon();
        }
    }
}
