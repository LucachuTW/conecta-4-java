public class EvaluadorConnectThree extends Evaluador {
    
    @Override
    public int valoracion(Tablero tablero, int jugador) {
        int valoracion = 0;
        // Evaluación de filas
        valoracion += evaluarFilas(tablero, jugador);
        // Evaluación de columnas
        valoracion += evaluarColumnas(tablero, jugador);
        // Evaluación de diagonales
        valoracion += evaluarDiagonales(tablero, jugador);
        return valoracion;
    }
    
    private int evaluarFilas(Tablero tablero, int jugador) {
        int valoracion = 0;
        for (int fila = 0; fila < Tablero.NFILAS; fila++) {
            for (int columna = 0; columna <= Tablero.NCOLUMNAS - Tablero.NOBJETIVO; columna++) {
                valoracion += evaluarLinea(tablero, fila, columna, 0, 1, jugador);
            }
        }
        return valoracion;
    }
    
    private int evaluarColumnas(Tablero tablero, int jugador) {
        int valoracion = 0;
        for (int columna = 0; columna < Tablero.NCOLUMNAS; columna++) {
            for (int fila = 0; fila <= Tablero.NFILAS - Tablero.NOBJETIVO; fila++) {
                valoracion += evaluarLinea(tablero, fila, columna, 1, 0, jugador);
            }
        }
        return valoracion;
    }
    
    private int evaluarDiagonales(Tablero tablero, int jugador) {
        int valoracion = 0;
        // Diagonales hacia la derecha
        for (int fila = 0; fila <= Tablero.NFILAS - Tablero.NOBJETIVO; fila++) {
            for (int columna = 0; columna <= Tablero.NCOLUMNAS - Tablero.NOBJETIVO; columna++) {
                valoracion += evaluarLinea(tablero, fila, columna, 1, 1, jugador);
            }
        }
        // Diagonales hacia la izquierda
        for (int fila = 0; fila <= Tablero.NFILAS - Tablero.NOBJETIVO; fila++) {
            for (int columna = Tablero.NCOLUMNAS - 1; columna >= Tablero.NOBJETIVO - 1; columna--) {
                valoracion += evaluarLinea(tablero, fila, columna, 1, -1, jugador);
            }
        }
        return valoracion;
    }
    
    private int evaluarLinea(Tablero tablero, int fila, int columna, int incrementoFila, int incrementoColumna, int jugador) {
        int contadorJugador = 0;
        int contadorOponente = 0;
        for (int i = 0; i < Tablero.NOBJETIVO; i++) {
            int contenidoCasilla = tablero.obtenerCasilla(fila + i * incrementoFila, columna + i * incrementoColumna);
            if (contenidoCasilla == jugador) {
                contadorJugador++;
            } else if (contenidoCasilla != Tablero.VACIO) {
                contadorOponente++;
            }
        }
        if (contadorJugador == Tablero.NOBJETIVO) {
            return Evaluador.MAXIMO;
        } else if (contadorOponente == Tablero.NOBJETIVO) {
            return Evaluador.MINIMO;
        } else {
            return contadorJugador * 10 - contadorOponente * 10;
        }
    }
}
