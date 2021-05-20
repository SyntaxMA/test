package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Goldenbala {

    static Texture texture = new Texture("goldenbill(1).png");

    float x, y, w, h, v;

    Goldenbala(float xNave, float yNave){
        w = 15;
        h = 35;
        x = xNave-w/2;
        y = yNave;
        v = 4;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, w, h);
    }

    void update(){
        y += 10;
    }
}
