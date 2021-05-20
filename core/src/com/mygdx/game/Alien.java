package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;


public class Alien {

    Texture texture = new Texture("alien.png");
    float x, y, w, h, vx,vy;
    Temporizador cambioVelocidad = new Temporizador(60);

    Alien(){
        x = 250;
        y = 300;
        w = 50;
        h = 70;
        vx = 2;
        vy = 1;
    }

    public void update() {
        y -= vy;
        x += vx;


        if (cambioVelocidad.suena()) {
            vy = Utils.random.nextInt(6) - 3;
            vx = -(Utils.random.nextInt(3)+1);
        }

        if (Utils.random.nextInt(10) == 0) {
            // balas.add
        }
    }

    void render(SpriteBatch batch){ batch.draw(texture, x, y, w, h); }
}