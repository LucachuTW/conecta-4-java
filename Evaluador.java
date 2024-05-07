/*
 * Evaluador.java
 *
 * Created on 10 de enero de 2004, 17:01
 */

/**
 *
 * @author  ribadas
 */
public abstract class Evaluador {

    
     
   
    public static int MAXIMO = 20000;
    public static int MINIMO = -MAXIMO;
    
    private Tablero _tablero;
    private int _jugador;
    private int _valoracion;
    
    
    /** Creates a new instance of Evaluador */
    public Evaluador() {

    }
    
    public abstract int valoracion(Tablero tablero, int jugador);
    
}
