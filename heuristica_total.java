import java.util.Arrays;

public class heuristica_total {
    private static final int MAX_DEPTH = 4;
    private static final int NUM_TRIALS = 8;
    private static final double INCREMENTO_PESOS = 0.1; // Incremento o decremento de pesos

    public double[] encontrarPesosOptimos(EvaluadorPonderadoDinamico evaluador, Tablero tablero, int jugador, int numPartidas) {
        double[] pesosOptimos = busca_pesos_optimos(); // Obtener pesos óptimos iniciales
        double mejorValoracion = evaluador.valoracion(tablero, jugador);
        
        boolean mejoraEncontrada;
        
        do {
            mejoraEncontrada = false;
            
            // Copiar los pesos óptimos actuales
            double[] nuevosPesosOptimos = pesosOptimos.clone();
            
            // Probar cada peso incrementado y decrementado
            for (int i = 0; i < pesosOptimos.length; i++) {
                // Incrementar el peso
                double[] nuevosPesosIncrementados = pesosOptimos.clone();
                nuevosPesosIncrementados[i] += INCREMENTO_PESOS;
                
                // Decrementar el peso
                double[] nuevosPesosDecrementados = pesosOptimos.clone();
                nuevosPesosDecrementados[i] -= INCREMENTO_PESOS;
                
                // Contadores para las victorias de cada configuración de pesos
                int victoriasNuevosPesosIncrementados = 0;
                int victoriasNuevosPesosDecrementados = 0;
                
                // Simular partidas entre los pesos óptimos actuales y los nuevos pesos
                for (int j = 0; j < NUM_TRIALS; j++) {
                    // Simular partida entre los nuevos pesos incrementados y los óptimos
                    int resultadoIncrementados = simularPartida(
                        new EstrategiaMiniMax(new EvaluadorPonderadoDinamico(nuevosPesosIncrementados), MAX_DEPTH),
                        new EstrategiaMiniMax(new EvaluadorPonderadoDinamico(pesosOptimos), MAX_DEPTH)
                    );
                    
                    // Simular partida entre los nuevos pesos decrementados y los óptimos
                    int resultadoDecrementados = simularPartida(
                        new EstrategiaMiniMax(new EvaluadorPonderadoDinamico(nuevosPesosDecrementados), MAX_DEPTH),
                        new EstrategiaMiniMax(new EvaluadorPonderadoDinamico(pesosOptimos), MAX_DEPTH)
                    );
                    
                    // Contabilizar los resultados
                    if (resultadoIncrementados == 1) {
                        victoriasNuevosPesosIncrementados++;
                    }
                    if (resultadoDecrementados == 1) {
                        victoriasNuevosPesosDecrementados++;
                    }
                }
                
                // Si los nuevos pesos incrementados superan a los óptimos actuales en la mayoría de las partidas, actualizar
                if (victoriasNuevosPesosIncrementados > NUM_TRIALS / 2) {
                    nuevosPesosOptimos = nuevosPesosIncrementados.clone();
                    mejoraEncontrada = true;
                }
                
                // Si los nuevos pesos decrementados superan a los óptimos actuales en la mayoría de las partidas, actualizar
                if (victoriasNuevosPesosDecrementados > NUM_TRIALS / 2) {
                    nuevosPesosOptimos = nuevosPesosDecrementados.clone();
                    mejoraEncontrada = true;
                }
            }
            
            // Si se encontró una mejora, actualizar los pesos óptimos y la mejor valoración
            if (mejoraEncontrada) {
                pesosOptimos = nuevosPesosOptimos.clone();
                mejorValoracion = evaluador.valoracion(tablero, jugador);
                System.out.println("Nuevos pesos óptimos encontrados: " + Arrays.toString(pesosOptimos));
            }
        } while (mejoraEncontrada);
        
        return pesosOptimos;
    }

    public double[] busca_pesos_optimos(){
        double[] pesos_optimos = {1, 1, 1, 1, 1};
        return pesos_optimos;
    }
    public static int simularPartida(Estrategia estrategia1, Estrategia estrategia2) {
        Jugador jugador1 = new Jugador(1);
        jugador1.establecerEstrategia(estrategia1);
        DEBUG("Jugador 1: " + estrategia1 + "\n");

        Jugador jugador2 = new Jugador(2);
        jugador2.establecerEstrategia(estrategia2);
        DEBUG("Jugador 2: " + estrategia2 + "\n");

        Tablero tablero = new Tablero();
        tablero.inicializar();
        jugar(jugador1, jugador2, tablero);

        
        if (tablero.hayEmpate()) {
            System.out.println("RESULTADO: Empate");
            return 0; // Empate, retorna 0
        }
        if (tablero.ganaJ1()) {
            System.out.println("RESULTADO: Gana jugador 1");
            return 1; // Estrategia 1 gana, retorna 1
        }
        if (tablero.ganaJ2()) {
            System.out.println("RESULTADO: Gana jugador 2");
            return -1; // Estrategia 2 gana, retorna -1
        }
        System.out.println("RESULTADO: Sin resultado");
        return 0; // No hay ganador
    }

    public static final void ERROR_FATAL(String mensaje) {
        System.out.println("ERROR FATAL\n\t" + mensaje);
        System.exit(0); // Finalizar aplicación
    }

    public static final void DEBUG(String str) {
        System.out.print("DBG:" + str);
    }

    public static final void ERROR(String mensaje) {
        System.out.println("ERROR\n\t" + mensaje);
    }

    private static void jugar(Jugador jugador1, Jugador jugador2, Tablero tablero) {
        
        int turno = 0;
        Jugador jugadorActual;
        int movimiento;
        boolean posicionesPosibles[];

        tablero.obtenerGanador();
        while (!tablero.esFinal()) {
            turno++;
            if ((turno % 2) == 1) {
                jugadorActual = jugador1;
            } else {
                jugadorActual = jugador2;
            }
            movimiento = jugadorActual.obtenerJugada(tablero);
            if ((movimiento >= 0) && (movimiento < Tablero.NCOLUMNAS)) {
                posicionesPosibles = tablero.columnasLibres();
                if (posicionesPosibles[movimiento]) {
                    tablero.anadirFicha(movimiento, jugadorActual.getIdentificador());
                    tablero.obtenerGanador();
                } else {
                    ERROR_FATAL("Columna completa. Juego Abortado.");
                }
            } else {
                ERROR_FATAL("Movimiento inválido. Juego Abortado.");
            }
        }
    }
}
