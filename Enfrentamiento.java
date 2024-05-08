public class Enfrentamiento {

    public static void main(String[] args) {
        double[] pesos_no_ajustados = {1, 1, 1, 1, 1}; // Pesos para el primer evaluador
        double[] pesos_optimizados = {2, 4, 0, 2, 1}; // Pesos para el segundo evaluador
        int n = 10; // Número de enfrentamientos a realizar


        // Simulación de enfrentamientos con estrategias ALFA-BETA con profundidad 4 y diferentes evaluadores
        double[] resultados1 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_optimizados), 4), new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_no_ajustados), 4), n);
        double[] resultados2 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_optimizados), 4), new EstrategiaAlfaBeta(new EvaluadorAleatorio(), 4), n);
        double[] resultados3 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_no_ajustados), 4), new EstrategiaAlfaBeta(new EvaluadorAleatorio(), 4), n);
        mostrarResultados("ALFA-BETA con evaluador optimizado (J1) vs. evaluador sin ajustar (J2)", resultados1);
        mostrarResultados("ALFA-BETA con evaluador optimizado (J1) vs. evaluador aleatorio (J2)", resultados2);
        mostrarResultados("ALFA-BETA con evaluador inicial (J1) vs. evaluador aleatorio (J2)", resultados3);
        System.out.println("\nAhora se realizaran las comparaciones de capa maxima\n");
        for (int i=2; i<=5; i++){
            System.out.println("\nCAPA MAXIMA "+i);
            double[] resultados4 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_optimizados), i), new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_no_ajustados), i), n);
            double[] resultados5 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_optimizados), i), new EstrategiaAlfaBeta(new EvaluadorAleatorio(), i), n);
            double[] resultados6 = simularEnfrentamientos(new EstrategiaAlfaBeta(new EvaluadorPonderadoDinamico(pesos_no_ajustados), i), new EstrategiaAlfaBeta(new EvaluadorAleatorio(), i), n);
            mostrarResultados("\nALFA-BETA con evaluador optimizado (J1) vs. evaluador sin ajustar (J2)", resultados4);
            mostrarResultados("ALFA-BETA con evaluador optimizado (J1) vs. evaluador aleatorio (J2)", resultados5);
            mostrarResultados("ALFA-BETA con evaluador inicial (J1) vs. evaluador aleatorio (J2)", resultados6);

        } 

        // Mostrar resultados
        
        

        for (int i = 2; i < 6; i++) {
            
        }
    }

    public static double[] simularEnfrentamientos(Estrategia estrategia1, Estrategia estrategia2, int n) {
        int victoriasJugador1 = 0;
        int victoriasJugador2 = 0;
        int empates = 0;
        double tiempoMedioJugador1 = 0; // Variable para almacenar el tiempo medio de búsqueda para el Jugador 1
        double tiempoMedioJugador2 = 0; // Variable para almacenar el tiempo medio de búsqueda para el Jugador 2
        double nodosMediosJugador1 = 0; // Variable para almacenar el número medio de nodos generados para el Jugador 1
        double nodosMediosJugador2 = 0; // Variable para almacenar el número medio de nodos generados para el Jugador 2
    
        for (int i = 0; i < n; i++) {
            // Crear jugadores y establecer estrategias
            Jugador jugador1 = new Jugador(1);
            jugador1.establecerEstrategia(estrategia1);
            Jugador jugador2 = new Jugador(2);
            jugador2.establecerEstrategia(estrategia2);
    
            // Jugar
            Tablero tablero = new Tablero();
            tablero.inicializar();
            jugar(jugador1, jugador2, tablero);
    
            // Obtener tiempo medio de búsqueda y nodos medios generados
            tiempoMedioJugador1 += ((EstrategiaAlfaBeta) estrategia1).obtenerTiempoMedioBusqueda();
            tiempoMedioJugador2 += ((EstrategiaAlfaBeta) estrategia2).obtenerTiempoMedioBusqueda();
            nodosMediosJugador1 += ((EstrategiaAlfaBeta) estrategia1).obtenerNodosMediosGenerados();
            nodosMediosJugador2 += ((EstrategiaAlfaBeta) estrategia2).obtenerNodosMediosGenerados();
    
            // Verificar ganador y actualizar contadores
            if (tablero.ganaJ1()) {
                victoriasJugador1++;
            } else if (tablero.ganaJ2()) {
                victoriasJugador2++;
            } else {
                empates++;
            }
        }
    
        // Calcular porcentajes
        double total = (double) n;
        double porcentajeVictoriasJugador1 = (victoriasJugador1 / total) * 100.0;
        double porcentajeVictoriasJugador2 = (victoriasJugador2 / total) * 100.0;
        double porcentajeEmpates = (empates / total) * 100.0;
    
        // Calcular el tiempo medio de búsqueda y el número medio de nodos generados para cada jugador
        tiempoMedioJugador1 /= n;
        tiempoMedioJugador2 /= n;
        nodosMediosJugador1 /= n;
        nodosMediosJugador2 /= n;
    
        return new double[]{porcentajeVictoriasJugador1, porcentajeVictoriasJugador2, porcentajeEmpates,
                            tiempoMedioJugador1, tiempoMedioJugador2, nodosMediosJugador1, nodosMediosJugador2};
    }
    

    private static void jugar(Jugador jugador1, Jugador jugador2, Tablero tablero) {
        int turno = 0;
        Jugador jugadorActual;
        int movimiento;
        boolean posicionesPosibles[];

        tablero.obtenerGanador();
        while (!tablero.esFinal()) {
            turno++;
            jugadorActual = (turno % 2 == 1) ? jugador1 : jugador2;
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

    private static void mostrarResultados(String titulo, double[] resultados) {
        System.out.println(titulo);
        System.out.println("\nPorcentaje de victorias del Jugador 1: " + resultados[0] + "%");
        System.out.println("Tiempo medio de búsqueda del Jugador 1: " + resultados[3] + " ms");
        System.out.println("Nodos medios generados por el Jugador 1: " + resultados[5]);
        System.out.println("\nPorcentaje de victorias del Jugador 2: " + resultados[1] + "%");
        System.out.println("Tiempo medio de búsqueda del Jugador 2: " + resultados[4] + " ms");
        System.out.println("Nodos medios generados por el Jugador 2: " + resultados[6]);
        System.out.println("Porcentaje de empates: " + resultados[2] + "%");

        System.out.println("\n\n");
    }

    public static final void ERROR_FATAL(String mensaje) {
        System.out.println("ERROR FATAL\n\t" + mensaje);
        System.exit(0); // Finalizar aplicación
    }
}
