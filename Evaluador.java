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
    /* Implementa la superclase del patron Estrategia para encapsular
     * las distintas funciones de evaluacion
     * 
     * Define el interfaz (funcion "valoracion")
     */


     public int evaluarControlEsquinas(Tablero tablero, int jugador) {
        int puntuacion = 0;
        
        // Definir las coordenadas de las esquinas
        int[][] esquinas = {{0, 0}, {0, Tablero.NCOLUMNAS - 1}, {Tablero.NFILAS - 1, 0}, {Tablero.NFILAS - 1, Tablero.NCOLUMNAS - 1}};
        
        // Evaluar el control de las esquinas
        for (int[] esquina : esquinas) {
            int fila = esquina[0];
            int columna = esquina[1];
            
            // Si el jugador tiene una ficha en la esquina, sumar puntos
            if (tablero.getCasilla(fila, columna) == jugador) {
                puntuacion += 1;
            }
            // Si el oponente tiene una ficha en la esquina, restar puntos
            else if (tablero.getCasilla(fila, columna) != Tablero.VACIO) {
                puntuacion -= 1;
            }
        }
        
        return puntuacion;
    }
    
    public int evaluarCasillasCentro(Tablero tablero, int jugador) {
        // Puntuación más alta para las casillas más cercanas al centro
        int puntuacionMaxima = 10;
        
        // Definir el rango de filas y columnas para las casillas del centro
        int filaInicio = 2;
        int filaFin = 3;
        int columnaInicio = 2;
        int columnaFin = 4;
        
        int puntuacionTotal = 0;
        
        // Iterar sobre las casillas del centro
        for (int fila = filaInicio; fila <= filaFin; fila++) {
            for (int columna = columnaInicio; columna <= columnaFin; columna++) {
                // Calcular la distancia al centro
                int distanciaFilaCentro = Math.abs(fila - (Tablero.NFILAS / 2));
                int distanciaColumnaCentro = Math.abs(columna - (Tablero.NCOLUMNAS / 2));
                
                // Calcular la puntuación para esta casilla
                int puntuacionCasilla = puntuacionMaxima - Math.max(distanciaFilaCentro, distanciaColumnaCentro);
                
                // Asegurarse de que la casilla está vacía antes de asignar la puntuación
                if (tablero.getCasilla(fila, columna) == Tablero.VACIO) {
                    puntuacionTotal += puntuacionCasilla;
                }
            }
        }
        
        return puntuacionTotal;
    }

    public int evaluarControlColumnas(Tablero tablero, int jugador) {
        int puntuacion = 0;
        
        // Iterar sobre las columnas
        for (int columna = 0; columna < Tablero.NCOLUMNAS; columna++) {
            // Contar las fichas del jugador en la columna
            int fichasJugador = 0;
            for (int fila = 0; fila < Tablero.NFILAS; fila++) {
                if (tablero.getCasilla(fila, columna) == jugador) {
                    fichasJugador++;
                }
            }
            
            // Asignar puntuación en función del número de fichas del jugador en la columna
            if (fichasJugador == 1) {
                puntuacion += 1;
            } else if (fichasJugador == 2) {
                puntuacion += 3;
            } else if (fichasJugador == 3) {
                puntuacion += 5;
            }
        }
        
        return puntuacion;
    }

    public int evaluarControlFilas(Tablero tablero, int jugador) {
        int puntuacion = 0;
        
        // Iterar sobre las filas
        for (int fila = 0; fila < Tablero.NFILAS; fila++) {
            // Contar las fichas del jugador en la fila
            int fichasJugador = 0;
            for (int columna = 0; columna < Tablero.NCOLUMNAS; columna++) {
                if (tablero.getCasilla(fila, columna) == jugador) {
                    fichasJugador++;
                }
            }
            
            // Asignar puntuación en función del número de fichas del jugador en la fila
            if (fichasJugador == 1) {
                puntuacion += 1;
            } else if (fichasJugador == 2) {
                puntuacion += 3;
            } else if (fichasJugador == 3) {
                puntuacion += 5;
            }
        }
        
        return puntuacion;
    }

    public int evaluarControlDiagonales(Tablero tablero, int jugador) {
        int puntuacion = 0;
        
        // Iterar sobre las diagonales
        for (int fila = 0; fila < Tablero.NFILAS; fila++) {
            for (int columna = 0; columna < Tablero.NCOLUMNAS; columna++) {
                // Comprobar si la casilla actual es parte de una diagonal
                if (fila + 3 < Tablero.NFILAS && columna + 3 < Tablero.NCOLUMNAS) {
                    // Contar las fichas del jugador en la diagonal principal
                    int fichasJugadorPrincipal = 0;
                    for (int i = 0; i < 4; i++) {
                        if (tablero.getCasilla(fila + i, columna + i) == jugador) {
                            fichasJugadorPrincipal++;
                        }
                    }
                    
                    // Asignar puntuación en función del número de fichas del jugador en la diagonal principal
                    if (fichasJugadorPrincipal == 1) {
                        puntuacion += 1;
                    } else if (fichasJugadorPrincipal == 2) {
                        puntuacion += 3;
                    } else if (fichasJugadorPrincipal == 3) {
                        puntuacion += 5;
                    }
                }
                
                if (fila + 3 < Tablero.NFILAS && columna - 3 >= 0) {
                    // Contar las fichas del jugador en la diagonal secundaria
                    int fichasJugadorSecundaria = 0;
                    for (int i = 0; i < 4; i++) {
                        if (tablero.getCasilla(fila + i, columna - i) == jugador) {
                            fichasJugadorSecundaria++;
                        }
                    }
                    
                    // Asignar puntuación en función del número de fichas del jugador en la diagonal secundaria
                    if (fichasJugadorSecundaria == 1) {
                        puntuacion += 1;
                    } else if (fichasJugadorSecundaria == 2) {
                        puntuacion += 3;
                    } else if (fichasJugadorSecundaria == 3) {
                        puntuacion += 5;
                    }

    public int valoracion_heuristica(Tablero tablero, int jugador) {
    int ponderacionEsquinas = 2;
    int ponderacionCasillasCentro = 3;
    int ponderacionControlColumnas = 2;
    int ponderacionControlFilas = 2;
    int ponderacionControlDiagonales = 2;

    int valoracionTotal = 0;

    valoracionTotal += ponderacionEsquinas * evaluarControlEsquinas(tablero, jugador);
    valoracionTotal += ponderacionCasillasCentro * evaluarCasillasCentro(tablero, jugador);
    valoracionTotal += ponderacionControlColumnas * evaluarControlColumnas(tablero, jugador);
    valoracionTotal += ponderacionControlFilas * evaluarControlFilas(tablero, jugador);
    valoracionTotal += ponderacionControlDiagonales * evaluarControlDiagonales(tablero, jugador);

    return valoracionTotal;
}


    
     
   
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
