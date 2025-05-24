package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Gondolf gondolf;
	private Murcielago[] murcielagos;
	private int totalMurcielagos = 200;
	private int murcielagosActivos = 0;
	private int maxMurcielagosEnPantalla = 2;
	boolean murcielagoEliminado = false;
	private Image fondo, botonera, menu, gameOver, botoneraFuego;
	private Botonera explosion1;
	private Botonera explosion2;
	private Botonera explosion3;
	private int radioExplosion;
	private int tipoExplosionSeleccionada = 0; // 0 = ninguna, 1 = fuego, 2 = agua, 3 = veneno
	private int tipoExplosionActiva= 0;
	private double explosionX = -100;
	private double explosionY = -100;
	private boolean explosionMostrada = false;
	private int duracionExplosion = 0;
	private static final int duracionMaximaExplosion =30; // 30 ticks = aprox. 0.5 segundos
	private Obstaculos[] rocas;
	private boolean juegoIniciado = false;
	private boolean termino;
	private int mouseX;
	private int mouseY;
	private boolean puedeLanzar = false;

	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 700, 500);
		this.gondolf = new Gondolf(350, 250); // centro de la pantalla
		this.fondo = Herramientas.cargarImagen("Imagenes TP Programacion 1/Fondo.jpg").getScaledInstance(550, 500, Image.SCALE_SMOOTH);
		this.botonera = Herramientas.cargarImagen("Imagenes TP Programacion 1/Botonera.jpg").getScaledInstance(180, 500, Image.SCALE_SMOOTH);
		this.botoneraFuego = Herramientas.cargarImagen("Imagenes TP Programacion 1/BotoneraFuego.gif").getScaledInstance(180, 500, Image.SCALE_SMOOTH);
		//menu//
		this.menu = Herramientas.cargarImagen("Imagenes TP Programacion 1/menu.jpg");
		this.gameOver = Herramientas.cargarImagen("Imagenes TP Programacion 1/gameOver.jpg").getScaledInstance(700, 500, Image.SCALE_SMOOTH);
	    this.mouseX = entorno.mouseX();
	    this.mouseY = entorno.mouseY();
		// Inicializar explosiones (ANTES del for)
		this.explosion1 = new Botonera(0, 0);
		this.explosion2 = new Botonera(0, 0);
		this.explosion3 = new Botonera(0, 0);
		//murcielagos//
		murcielagos = new Murcielago[totalMurcielagos];
		int i = 0;
		for (int j = 0; j < 50; j++) {
			murcielagos[i++] = new Murcielago(0, Math.random() * entorno.alto(), 30, 30, "izquierda");
			murcielagos[i++] = new Murcielago(468, Math.random() * entorno.alto(), 30, 30, "derecha");
			murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho()-200, 0, 30, 30, "arriba");
			murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho()-200, entorno.alto(), 30, 30, "abajo");
		}

			
		//rocas	
			rocas = new Obstaculos[15]; // cantidad de islas
			double xObstaculos;
			double yObstaculos = entorno.alto() - 120;
			int[] cantidadRocasPorNivel = { 1, 3, 2, 2 };
			double[] desplazamientoXPorNivel = { 100, 200, 400, 250 };
			int cantRocas = 0;
			for (int nivel = 0; nivel < cantidadRocasPorNivel.length; nivel++) {
				xObstaculos = 275 - ((cantidadRocasPorNivel[nivel] - 1) * desplazamientoXPorNivel[nivel]) / 2;
				for (int o = 0; o < cantidadRocasPorNivel[nivel]; o++) {
					rocas[cantRocas] = new Obstaculos(xObstaculos, yObstaculos - (nivel * 100));
					xObstaculos += desplazamientoXPorNivel[nivel]; 
					cantRocas++; 
		}
			}
		this.entorno.iniciar();
	}

	public void tick()
	{		
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
		    System.out.println("x="+entorno.mouseX()+"y= "+ entorno.mouseY());
		}
		
		if (!juegoIniciado) {
		entorno.dibujarImagen(menu, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
		if (entorno.mouseX() >= 416 && entorno.mouseX() <= 616 && entorno.mouseY() >= 324
				&& entorno.mouseY() <= 392)// si el mouse esta ubicado en el boton
			if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {// si el click del mouse fue apretado
				juegoIniciado = true; // Inicia el juego
			}
		return;
	}
		if (gondolf == null) {
			entorno.dibujarImagen(gameOver, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
			if (entorno.mouseX() >= 219 && entorno.mouseX() <= 397 && entorno.mouseY() >= 369
					&& entorno.mouseY() <= 409)
				if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
					// REINICIALIZAR TODO
					gondolf = new Gondolf(350, 250);
					
					murcielagos = new Murcielago[totalMurcielagos];
					murcielagosActivos = 0;
					int i = 0;
					for (int j = 0; j < 50; j++) {
						murcielagos[i++] = new Murcielago(-20, Math.random() * entorno.alto(), 30, 30, "izquierda");
						murcielagos[i++] = new Murcielago(486, Math.random() * entorno.alto(), 30, 30, "derecha");
						murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho(), -20, 30, 30, "arriba");
						murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho(), entorno.alto() + 20, 30, 30, "abajo");
					}

					rocas = new Obstaculos[15];
					double yObstaculos = entorno.alto() - 120;
					int[] cantidadRocasPorNivel = { 1, 3, 2, 2 };
					double[] desplazamientoXPorNivel = { 100, 200, 400, 250 };
					int cantRocas = 0;

					for (int nivel = 0; nivel < cantidadRocasPorNivel.length; nivel++) {
						double xObstaculos = 275 - ((cantidadRocasPorNivel[nivel] - 1) * desplazamientoXPorNivel[nivel]) / 2;
						for (int o = 0; o < cantidadRocasPorNivel[nivel]; o++) {
							rocas[cantRocas] = new Obstaculos(xObstaculos, yObstaculos - (nivel * 100));
							xObstaculos += desplazamientoXPorNivel[nivel];
							cantRocas++;
						}
					}

					tipoExplosionSeleccionada = 0;
					explosionX = -100;
					explosionY = -100;
					explosionMostrada = false;
					duracionExplosion = 0;
					totalMurcielagos = 200;
					
					juegoIniciado = true;
					termino = false;
				}
			return;
		}

//////////                     INTERFAZ                  /////////////////////////////////////////////////////////
		entorno.dibujarImagen(fondo ,275, 250, 0);
		entorno.dibujarImagen(botonera,618,250,0);
		
		entorno.cambiarFont("Arial", 12, Color.WHITE);
		entorno.escribirTexto(""+totalMurcielagos, 614, 155);
		
		entorno.cambiarFont("ARCADE", 12, Color.WHITE);
		entorno.escribirTexto(""+gondolf.getVida(), 642, 239);
		
		entorno.cambiarFont("ARCADE", 12, Color.WHITE);
		entorno.escribirTexto(""+gondolf.getMana(), 642, 325);
		// Dibujar las Rocas
				for (int i = 0; i < rocas.length; i++) {
					if (rocas[i] != null) {
						rocas[i].dibujarRoca(entorno);
					}
				}	
				
///////////////////Gondolf////////////////////////////////////////////////////////////
		gondolf.mover(entorno,rocas);
		gondolf.dibujar(entorno);	
		if (gondolf.colisionaConRoca(rocas,gondolf.getX(),gondolf.getY())) {
		    //System.out.println("¡Gondolf colisionó con una roca!");
		}
		if (gondolf.colisionaEnemigo(murcielagos)) {
			murcielagosActivos--;
		    totalMurcielagos--;
			//System.out.println("¡Gondolf fue golpeado por un murciélago!");
		}
		if(gondolf.getVida()==0) {
			gondolf=null;			
		}
		
		//System.out.println("Vida de Gondolf: " + gondolf.getVida());


//////////////////////////////Botonera///////////////////////////////		
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			int mouseX = entorno.mouseX();
			int mouseY = entorno.mouseY();
			System.out.println("x=" + mouseX + " y=" + mouseY);

// Seleccionar tipo de explosión según botón
			if (mouseX >= 562 && mouseX <= 675 && mouseY >= 169 && mouseY <= 212) {
				tipoExplosionSeleccionada = 1;
			} else if (mouseX >= 562 && mouseX <= 675 && mouseY >= 255 && mouseY <= 299) {
				tipoExplosionSeleccionada = 2;
			} else if (mouseX >= 562 && mouseX <= 675 && mouseY >= 338 && mouseY <= 383) {
				tipoExplosionSeleccionada = 3;
			} else {

				 puedeLanzar = true;

				if (tipoExplosionSeleccionada == 2 && gondolf.getMana() < 10) {
					puedeLanzar = false;
					entorno.cambiarFont("Arial", 12, Color.WHITE);
					entorno.escribirTexto("MANA INSUFICIENTE", entorno.ancho()/2, entorno.alto()/2);
				} else if (tipoExplosionSeleccionada == 3 && gondolf.getMana() < 20) {
					puedeLanzar = false;
					entorno.cambiarFont("Arial", 12, Color.WHITE);
					entorno.escribirTexto("",entorno.ancho()/2, entorno.alto()/2);
				}

				if (puedeLanzar && tipoExplosionSeleccionada != 0) {
					explosionX = mouseX;
					explosionY = mouseY;
					duracionExplosion = duracionMaximaExplosion;
					if (tipoExplosionSeleccionada == 1) {
						//entorno.dibujarImagen(botoneraFuego, 618,250,0);				
					}else if (tipoExplosionSeleccionada == 2) {
						gondolf.restarMana(10);
					} else if (tipoExplosionSeleccionada == 3) {
						gondolf.restarMana(20);
					}
				}
			}

			explosionMostrada = false;
		}

// DIBUJAR EXPLOSION (si está activa y en tiempo)
		if (tipoExplosionSeleccionada != 0 && duracionExplosion > 0) {
			if (tipoExplosionSeleccionada == 1) {
				explosion1.setX(explosionX);
				explosion1.setY(explosionY);
				explosion1.dibujarExplosion1(entorno);
			} else if (tipoExplosionSeleccionada == 2) {
				explosion2.setX(explosionX);
				explosion2.setY(explosionY);
				explosion2.dibujarExplosion2(entorno);
			} else if (tipoExplosionSeleccionada == 3) {
				explosion3.setX(explosionX);
				explosion3.setY(explosionY);
				explosion3.dibujarExplosion3(entorno);
			}

			duracionExplosion--;
		}
		
// Eliminar murciélagos cerca de la explosión
// Daño por explosión si está activa
		if (duracionExplosion > 0 && tipoExplosionSeleccionada != 0) {
			for (int i = 0; i < murcielagos.length; i++) {
				if (murcielagos[i] != null && murcielagos[i].estaActivo()) {
					double dx = murcielagos[i].getX() - explosionX;
					double dy = murcielagos[i].getY() - explosionY;
					double distancia = Math.sqrt(dx * dx + dy * dy);
					if (distancia < 80) { // radio de daño
						murcielagos[i] = null;
						murcielagosActivos--;
						totalMurcielagos--;
						gondolf.sumarMana(5);
					}
				}
			}
		}

///////////////murcielagos////////////////////////////////////////////////////
	// Activar hasta 20
	for (int i = 0; i < murcielagos.length && murcielagosActivos < maxMurcielagosEnPantalla; i++) {
		if (murcielagos[i] != null && !murcielagos[i].estaActivo()) {
			murcielagos[i].activar();
			murcielagosActivos++;
		}
	}
	
	for (int i = 0; i < murcielagos.length; i++) {
		if (murcielagos[i] != null && murcielagos[i].estaActivo()) {
			murcielagos[i].moverHacia(gondolf.getX(), gondolf.getY() );
			murcielagos[i].dibujar(entorno);
		}
	}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
