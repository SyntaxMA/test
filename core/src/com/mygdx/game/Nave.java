package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    Animacion animacion = new Animacion(16,
            new Texture("nave.png"),
            new Texture("nave1.png"),
            new Texture("nave2.png"),
            new Texture("nave3.png")
    );

    float x, y, w, h, v;
    List<Bala> balas = new ArrayList<>();
    List<SuperBala> superbalas = new ArrayList<>();
    List<Goldenbala> goldenbalas = new ArrayList<>();
    int vidas = 3;
    int puntos = 0;
    boolean muerto = false;

    Temporizador temporizadorFireRate = new Temporizador(20);
    Temporizador temporizadorRespawn = new Temporizador(120, false);

    Nave(){
        x = 280;
        y = 50;
        w = 50;
        h = 100;
    }

    void update(){
        for (Bala bala: balas) bala.update();
        for (SuperBala superbala: superbalas) superbala.update();
        for (Goldenbala goldenbala: goldenbalas) goldenbala.update();

        if(Gdx.input.isKeyPressed(Input.Keys.D)) x += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) x -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) y += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) y -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) y += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= 5;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            balas.add(new Bala(x+w/2, y+h));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            superbalas.add(new SuperBala(x+w/2, y+h));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            goldenbalas.add(new Goldenbala(x+w/2, y+h));
        }
        if(x < 0) x = 0;

        if (temporizadorRespawn.suena()) {
            muerto = false;
        }
    }

    void render(SpriteBatch batch){
        batch.draw(animacion.obtenerFrame(), x, y,w, h);
        for (Bala bala: balas) {
            bala.render(batch);
        }
        for (SuperBala superbala: superbalas) {
            superbala.render(batch);
        }
        for (Goldenbala goldenbala: goldenbalas) {
            goldenbala.render(batch);
        }
    }

    public void morir() {
        vidas--;
        muerto = true;
        temporizadorRespawn.activar();
    }

}