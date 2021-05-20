package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont font;
	Fondo fondo;
	Nave nave;

	//Enemigos
	List<Alien> aliens = new ArrayList<>();
	List<SuperAlien> superaliens = new ArrayList<>();
	List<GoldenAlien> goldenaliens = new ArrayList<>();

	//Balas
	List<Bala> balasAEliminar = new ArrayList<>();
	List<SuperBala> superBalasAEliminar = new ArrayList<>();
	List<Goldenbala> goldenbalasAEliminar = new ArrayList<>();

	//Enemigos eliminados
	List<Alien> aliensAEliminar = new ArrayList<>();
	List<SuperAlien> superaliensAEliminar = new ArrayList<>();
	List<GoldenAlien> goldenaliensAEliminar = new ArrayList<>();

	//Temporizadores
	Temporizador temporizadorNuevoAlien;
	Temporizador temporizadorSuperAlien;
	Temporizador temporizadorGoldenAlien;

	//Resultados y Gameover
	private ScoreBoard scoreboard;
	private boolean gameover;


	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(2f);

		fondo = new Fondo();
		nave = new Nave();
		scoreboard = new ScoreBoard();

		//Crear Temporizador
		temporizadorNuevoAlien = new Temporizador(100);
		temporizadorSuperAlien = new Temporizador(350);
		temporizadorGoldenAlien = new Temporizador(750);

	}

	public void update() {

		//Establecer Temporizador

		Temporizador.framesJuego += 1;

		if (temporizadorNuevoAlien.suena()) aliens.add(new Alien());
		if (temporizadorSuperAlien.suena()) superaliens.add(new SuperAlien());
		if (temporizadorGoldenAlien.suena()) goldenaliens.add(new GoldenAlien());

		//Gameover

		if (!gameover) {
			nave.update();
		}

		//Aliens normales, le afectan balas normales

		for (Alien alien : aliens) alien.update();              // enemigos.forEach(Enemigo::update);

		for (Alien alien : aliens) {
			for (Bala bala : nave.balas) {
				if (Utils.solapan(bala.x, bala.y, bala.w, bala.h, alien.x, alien.y, alien.w, alien.h)) {
					balasAEliminar.add(bala);
					aliensAEliminar.add(alien);
					nave.puntos++;
					break;
				}
			}

			if (!gameover && !nave.muerto && Utils.solapan(alien.x, alien.y, alien.w, alien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.morir();
				if (nave.vidas == 2) {
					gameover = true;
					scoreboard.guardarPuntuacion(nave.puntos);
				}
			}

			if (alien.x < -alien.w) aliensAEliminar.add(alien);
		}

		// Balas normales para matar aliens normales

		for (Bala bala : nave.balas)
			if (bala.x > 640)
				balasAEliminar.add(bala);

		for (Bala bala : balasAEliminar) nave.balas.remove(bala);       // disparosAEliminar.forEach(disparo -> jugador.disparos.remove(disparo));
		for (Alien alien : aliensAEliminar) aliens.remove(alien);               // enemigosAEliminar.forEach(enemigo -> enemigos.remove(enemigo));
		balasAEliminar.clear();
		aliensAEliminar.clear();


		//Super Alien No le afectan balas normales, solo super balas

		for (SuperAlien superAlien : superaliens) superAlien.update();

		for (SuperAlien superAlien : superaliens) {
			for (SuperBala superBala : nave.superbalas) {
				if (Utils.solapan(superBala.x, superBala.y, superBala.w, superBala.h, superAlien.x, superAlien.y, superAlien.w, superAlien.h)) {
					superBalasAEliminar.add(superBala);
					superaliensAEliminar.add(superAlien);
					nave.puntos += 3;
					break;
				}
			}

			if (!gameover && !nave.muerto && Utils.solapan(superAlien.x, superAlien.y, superAlien.w, superAlien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.morir();
				if (nave.vidas == 2) {
					gameover = true;
					scoreboard.guardarPuntuacion(nave.puntos);
				}
			}

			if (superAlien.x < -superAlien.w) superaliensAEliminar.add(superAlien);
		}

		//Super Balas para matar Super Aliens

		for (SuperBala superBala : nave.superbalas)
			if (superBala.x > 640)
				superBalasAEliminar.add(superBala);

		for (SuperBala superBala : superBalasAEliminar) nave.superbalas.remove(superBala);
		for (SuperAlien superAlien : superaliensAEliminar) superaliens.remove(superAlien);
		superBalasAEliminar.clear();
		superaliensAEliminar.clear();


		//Golden aliens, le afectan golden balas

		for (GoldenAlien goldenAlien : goldenaliens) goldenAlien.update();              // enemigos.forEach(Enemigo::update);

		for (GoldenAlien goldenAlien : goldenaliens) {
			for (Goldenbala goldenbala : nave.goldenbalas) {
				if (Utils.solapan(goldenbala.x, goldenbala.y, goldenbala.w, goldenbala.h, goldenAlien.x, goldenAlien.y, goldenAlien.w, goldenAlien.h)) {
					goldenbalasAEliminar.add(goldenbala);
					goldenaliensAEliminar.add(goldenAlien);
					nave.puntos++;
					break;
				}
			}

			if (!gameover && !nave.muerto && Utils.solapan(goldenAlien.x, goldenAlien.y, goldenAlien.w, goldenAlien.h, nave.x, nave.y, nave.w, nave.h)) {
				nave.morir();
				if (nave.vidas == 2) {
					gameover = true;
					scoreboard.guardarPuntuacion(nave.puntos);
				}
			}

			if (goldenAlien.x < -goldenAlien.w) goldenaliensAEliminar.add(goldenAlien);
		}

		// Golden balas para matar golden aliens

		for (Goldenbala goldenbala : nave.goldenbalas)
			if (goldenbala.x > 640)
				goldenbalasAEliminar.add(goldenbala);

		for (Goldenbala goldenbala : goldenbalasAEliminar) nave.goldenbalas.remove(goldenbala);       // disparosAEliminar.forEach(disparo -> jugador.disparos.remove(disparo));
		for (GoldenAlien goldenAlien : goldenaliensAEliminar) goldenaliens.remove(goldenAlien);               // enemigosAEliminar.forEach(enemigo -> enemigos.remove(enemigo));
		goldenbalasAEliminar.clear();
		goldenaliensAEliminar.clear();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();

		batch.begin();
		fondo.render(batch);
		nave.render(batch);

		for (Alien alien : aliens) alien.render(batch);  // enemigos.forEach(e -> e.render(batch));
		for (SuperAlien superAlien : superaliens) superAlien.render(batch);
		for (GoldenAlien goldenAlien : goldenaliens) goldenAlien.render(batch);

		font.draw(batch, "" + nave.vidas, 590, 440);
		font.draw(batch, "" + nave.puntos, 30, 440);

		if (gameover){

			scoreboard.render(batch, font);
		}
		batch.end();
	}
}


/*
create();
while(true) render();
 */