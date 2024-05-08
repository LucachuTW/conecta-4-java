public class PesosOptimos {

     


    public double[] pesos_iniciales(){
        double pesos[] = {1, 1, 1, 1, 1};
        return pesos;
    }



    public double[] encontrarPesosOptimos{

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

        tablero.mostrar();
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
    */
}