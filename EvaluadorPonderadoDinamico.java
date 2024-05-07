public class EvaluadorPonderadoDinamico extends Evaluador {
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

    public double[] pesos_iniciales() {
        
        double[] pesos = {1.0, 1.0, 1.0, 1.0, 1.0};
        
        return pesos;
    }

    public int valoracion_heuristica_ponderada(Tablero tablero, int jugador, double[] pesos) {
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

        System.out.println(valoracionHeuristica);
        
        return valoracionHeuristica;
    }

    public double[] pesosOptimos(double[] pesos, Tablero tablero, int jugador) {
        double[] pesosOptimos = pesos;
        double mejorValoracion = valoracion_heuristica_ponderada(tablero, jugador, pesos_iniciales());
        
        // Iterar sobre los pesos
        for (int i = 0; i < pesos.length; i++) {
            // Incrementar el peso actual
            pesos[i] *= 1.1;
            
            // Calcular la valoración heurística con el peso actualizado
            double valoracion = valoracion_heuristica_ponderada(tablero, jugador, pesos_iniciales());
            
            // Si la valoración es mejor, actualizar los pesos óptimos
            if (valoracion > mejorValoracion) {
                mejorValoracion = valoracion;
                pesosOptimos = pesos.clone();
            }
            
            // Restaurar el peso original
            pesos[i] *= 0.9;
        }
        
        return pesosOptimos;
    }

    public double[] busquedaAscensoColinas(double[] pesos, Tablero tablero, int jugador) {
        double[] mejoresPesos = pesos;
        double mejorEvaluacion = valoracion_heuristica_ponderada(tablero, jugador, mejoresPesos);
    
        boolean mejoraEncontrada;
        do {
            mejoraEncontrada = false;
            for (int i = 0; i < mejoresPesos.length; i++) {
                double pesoOriginal = mejoresPesos[i];
    
                // Intenta incrementar el peso
                mejoresPesos[i] *= 1.1;
                double nuevaEvaluacion = valoracion_heuristica_ponderada(tablero, jugador, mejoresPesos);
                if (nuevaEvaluacion > mejorEvaluacion) {
                    mejorEvaluacion = nuevaEvaluacion;
                    mejoraEncontrada = true;
                } else {
                    // Si no hay mejora, intenta disminuir el peso
                    mejoresPesos[i] = pesoOriginal * 0.9;
                    nuevaEvaluacion = valoracion_heuristica_ponderada(tablero, jugador, mejoresPesos);
                    if (nuevaEvaluacion > mejorEvaluacion) {
                        mejorEvaluacion = nuevaEvaluacion;
                        mejoraEncontrada = true;
                    } else {
                        // Si aún no hay mejora, restaura el peso original
                        mejoresPesos[i] = pesoOriginal;
                    }
                }
            }
        } while (mejoraEncontrada);
        System.out.println("mejores pesos\n");
        System.out.println(mejoresPesos[0]);
    
        return mejoresPesos;
    }

    public int valoracion(Tablero tablero, int jugador) {
        System.out.println(valoracion_heuristica_ponderada(tablero, jugador, busquedaAscensoColinas(pesos_iniciales(), tablero, jugador)));
        return valoracion_heuristica_ponderada(tablero, jugador, busquedaAscensoColinas(pesos_iniciales(), tablero, jugador));
    }
}
