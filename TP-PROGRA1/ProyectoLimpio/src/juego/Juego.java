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
	private int maxMurcielagosEnPantalla = 20;
	private Image fondo;
	private Image botonera;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1400, 1000);
		this.gondolf = new Gondolf(700, 500); // centro de la pantalla
		this.fondo = Herramientas.cargarImagen("Imagenes TP Programacion 1/Fondo.jpg").getScaledInstance(1100, 1000, Image.SCALE_SMOOTH);
		this.botonera = Herramientas.cargarImagen("Imagenes TP Programacion 1/Botonera.jpeg").getScaledInstance(300, 1000, Image.SCALE_SMOOTH);
		murcielagos = new Murcielago[totalMurcielagos];
		int i = 0;
		for (int j = 0; j < 50; j++) {
			murcielagos[i++] = new Murcielago(-20, Math.random() * entorno.alto(), 30, 30, "izquierda");
			murcielagos[i++] = new Murcielago(entorno.ancho() + 20, Math.random() * entorno.alto(), 30, 30, "derecha");
			murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho(), -20, 30, 30, "arriba");
			murcielagos[i++] = new Murcielago(Math.random() * entorno.ancho(), entorno.alto() + 20, 30, 30, "abajo");
		}
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
		
		
		
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		//fondo blanco
		
		entorno.dibujarImagen(fondo ,550, 500, 0);
		entorno.dibujarImagen(botonera,1250,500,0);
		
		// Procesamiento de un instante de tiempo
		// ...
		
		//mover y dibujar a gondolf
		gondolf.mover(entorno);
		gondolf.dibujar(entorno);
		
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
