package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Gondolf {
	private double x;
	private double y;
	private int ancho; //ancho imagen mago
	private int alto; //alto imagen mago
	private double velocidad;
	private Image imagenAbajo;
	private Image imagenArriba;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private Image imagenActual;
	public static final int ANCHO_PANTALLA = 700; //estas tres variantes las ajustamos segun la resolucion
	public static final int ALTO_PANTALLA = 500;
	public static final int ANCHO_MENU = 150; //esto es el ancho de la botonera
	
	public Gondolf(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 3;
		this.ancho =  40;
		this.alto = 40;
		this.imagenDerecha = Herramientas.cargarImagen("Imagenes TP Programacion 1/Mago Derecha Opcion 2.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		this.imagenIzquierda = Herramientas.cargarImagen("Imagenes TP Programacion 1/Mago Izquierda Opcion 2.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		this.imagenAbajo = Herramientas.cargarImagen("Imagenes TP Programacion 1/Mago Abajo Opcion 2.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		this.imagenArriba = Herramientas.cargarImagen("Imagenes TP Programacion 1/Mago Arriba Opcion 2.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		//empezamos con la imagen a la derecha
		this.imagenActual = imagenDerecha;
	
	}
	public void mover(Entorno entorno, Obstaculos[] rocas) {
	    double nuevaX = this.x;
	    double nuevaY = this.y;

	    if(entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	    	if(y - velocidad > alto / 2) {
	    	nuevaY -= velocidad;
	        imagenActual = imagenArriba;
	    }
	    }
	    if(entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
	    	if(y + velocidad < ALTO_PANTALLA - alto/2 ) {
	    	nuevaY += velocidad;
	        imagenActual = imagenAbajo;
	    }
	    }
	    if(entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	    	if(x - velocidad > ancho / 2) {
	        nuevaX -= velocidad;
	        imagenActual = imagenIzquierda;
	    }
	    }
	   if(entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	    	if(x + velocidad < ANCHO_PANTALLA - ANCHO_MENU - ancho / 2) {
	        nuevaX += velocidad;    	
	        imagenActual = imagenDerecha;
	    }
	   }
	    // Solo mover si no colisiona
	    if (!colisionaConRoca(rocas, nuevaX, nuevaY)) {
	        this.x = nuevaX;
	        this.y = nuevaY;
	    }
}
	public boolean colisionaConRoca(Obstaculos[] rocas, double nuevaX, double nuevaY) {
	    for (Obstaculos roca : rocas) {
	        if (roca != null) {
	            boolean colisionX = nuevaX + ancho / 2 >= roca.getX() - roca.getAncho() / 2
	                             && nuevaX - ancho / 2 <= roca.getX() + roca.getAncho() / 2;
	            boolean colisionY = nuevaY + alto / 2 >= roca.getY() - roca.getAlto() / 2
	                             && nuevaY - alto / 2 <= roca.getY() + roca.getAlto() / 2;
	            if (colisionX && colisionY)
	                return true;
	        }
	    }
	    return false;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenActual, x, y, 0);
	}
	
	public boolean colisionaEnemigo(Murcielago[] murcielagos) {
		for (Murcielago m : murcielagos) {
			if (m != null && this.y + this.alto / 2 - 25 <= m.getY() + m.getAlto() / 2
					&& this.y - this.alto / 2 + 30 >= m.getY() - m.getAlto() / 2
					&& this.x - this.ancho / 2 + 10 <= m.getX() + m.getAncho() / 2
					&& this.x + this.ancho / 2 - 10 >= m.getX() - m.getAncho() / 2) {
				return true;
			}
		}
		return false;
		
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}


}
