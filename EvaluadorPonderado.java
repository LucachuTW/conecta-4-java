public class EvaluadorPonderado extends Evaluador {
    // Implementación del método abstracto
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
            else if (tablero.getCasilla(fila, columna) != 0) {
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
                if (tablero.getCasilla(fila, columna) == 0) {
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
                }
            }
        }
        return puntuacion;
    }

    public int valoracion_pesos_iniciales(Tablero tablero, int jugador) {
        // Ponderación de los factores de evaluación
        int ponderacionControlEsquinas = 1;
        int ponderacionCasillasCentro = 1;
        int ponderacionControlColumnas = 1;
        int ponderacionControlFilas = 1;
        int ponderacionControlDiagonales = 1;
        
        // Evaluar los factores de la función heurística
        int puntuacionControlEsquinas = evaluarControlEsquinas(tablero, jugador);
        int puntuacionCasillasCentro = evaluarCasillasCentro(tablero, jugador);
        int puntuacionControlColumnas = evaluarControlColumnas(tablero, jugador);
        int puntuacionControlFilas = evaluarControlFilas(tablero, jugador);
        int puntuacionControlDiagonales = evaluarControlDiagonales(tablero, jugador);
        
        // Calcular la valoración heurística ponderada
        int valoracionHeuristica = ponderacionControlEsquinas * puntuacionControlEsquinas
            + ponderacionCasillasCentro * puntuacionCasillasCentro
            + ponderacionControlColumnas * puntuacionControlColumnas
            + ponderacionControlFilas * puntuacionControlFilas
            + ponderacionControlDiagonales * puntuacionControlDiagonales;
        
        return valoracionHeuristica;
    }

    public int[] pesos_iniciales{
        int[] pesos = {1, 1, 1, 1, 1};
        return pesos;
    }

    public int[] pesos_optimos(Tablero tablero, int jugador) {
        int[] pesos = pesos_iniciales();
        int mejor_valoracion = valoracion_pesos_iniciales(tablero, jugador);
        int[] mejor_pesos = pesos;
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + 1;
            int valoracion = valoracion_pesos_iniciales(tablero, jugador);
            if (valoracion > mejor_valoracion) {
                mejor_valoracion = valoracion;
                mejor_pesos = pesos;
            }
            pesos[i] = pesos[i] - 1;
        }
        return mejor_pesos;
    }

    public int valoracion_optima(Tablero tablero, int jugador) {
        int[] pesos = pesos_optimos(tablero, jugador);
        int valoracion = pesos[0] * evaluarControlEsquinas(tablero, jugador)
            + pesos[1] * evaluarCasillasCentro(tablero, jugador)
            + pesos[2] * evaluarControlColumnas(tablero, jugador)
            + pesos[3] * evaluarControlFilas(tablero, jugador)
            + pesos[4] * evaluarControlDiagonales(tablero, jugador);
        return valoracion;
    }

    public int valoracion(Tablero tablero, int jugador) {
        return valoracion_heuristica_ponderada(tablero, jugador);
    }
}
