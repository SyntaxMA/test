package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GoldenAlien {

    Texture texture = new Texture("goldenalien.png");
    float x, y, w, h, vx,vy;
    Temporizador cambioVelocidad = new Temporizador(80);

    GoldenAlien(){
        x = 300;
        y = 300;
        w = 50;
        h = 70;
        vx = 2;
        vy = -1;
    }

    public void update() {
        y -= vy;
        x += vx;

        if (cambioVelocidad.suena()) {
            vy = Utils.random.nextInt(6) - 3;
            vx = -(Utils.random.nextInt(3)+1);
        }
    }

    void render(SpriteBatch batch){ batch.draw(texture, x, y, w, h); }

}


