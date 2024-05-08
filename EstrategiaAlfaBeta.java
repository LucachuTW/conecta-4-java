public class EstrategiaAlfaBeta extends Estrategia {
    private Evaluador _evaluador;
    private int _capaMaxima;
    private int _jugadorMAX;
    private double tiempoMedioBusqueda; // Variable para almacenar el tiempo medio de búsqueda
    private double nodosMediosGenerados; // Variable para almacenar el número medio de nodos generados

    public EstrategiaAlfaBeta() {
        tiempoMedioBusqueda = 0;
        nodosMediosGenerados = 0;
    }

    public EstrategiaAlfaBeta(Evaluador evaluador, int capaMaxima) {
        this.establecerEvaluador(evaluador);
        this.establecerCapaMaxima(capaMaxima);
        tiempoMedioBusqueda = 0;
        nodosMediosGenerados = 0;
    }

    public int buscarMovimiento(Tablero tablero, int jugador) {
        long startTime = System.nanoTime(); // Registrar tiempo de inicio
        int nodosGenerados = 0; // Contador de nodos generados
        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;
        int valorSucesor;
        int col;
        int mejorPosicion = -1;
        int mejorValor = Integer.MIN_VALUE;

        _jugadorMAX = jugador;
        for (col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                valorSucesor = ALFABETA(nuevoTablero, Jugador.alternarJugador(jugador), 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                nodosGenerados++; // Incrementar el contador de nodos generados

                if (valorSucesor >= mejorValor) {
                    mejorValor = valorSucesor;
                    mejorPosicion = col;
                }
            }
        }
        long endTime = System.nanoTime(); // Registrar tiempo de finalización
        double tiempoDeBusqueda = (endTime - startTime) / 1e6; // Convertir a milisegundos
        tiempoMedioBusqueda += tiempoDeBusqueda; // Sumar al tiempo medio de búsqueda total
        nodosMediosGenerados += (double) nodosGenerados; // Sumar al número medio de nodos generados total

        return mejorPosicion;
    }

    public double obtenerTiempoMedioBusqueda() {
        return tiempoMedioBusqueda;
    }

    public double obtenerNodosMediosGenerados() {
        return nodosMediosGenerados;
    }

    public int ALFABETA(Tablero tablero, int jugador, int capa, int alfa, int beta) {
        if (tablero.hayEmpate()) {
            return 0;
        }
        if (tablero.esGanador(_jugadorMAX)) {
            return Evaluador.MAXIMO;
        }
        if (tablero.esGanador(Jugador.alternarJugador(_jugadorMAX))) {
            return Evaluador.MINIMO;
        }
        if (capa == _capaMaxima) {
            return _evaluador.valoracion(tablero, _jugadorMAX);
        }

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;
        int col;

        if (esCapaMAX(capa)) {
            int alfa_actual = alfa;
            int aux = Evaluador.MINIMO;
            for (col = 0; col < Tablero.NCOLUMNAS; col++) {
                if (movimientosPosibles[col]) {
                    nuevoTablero = (Tablero) tablero.clone();
                    nuevoTablero.anadirFicha(col, jugador);
                    nuevoTablero.obtenerGanador();
                    int valorSucesor = ALFABETA(nuevoTablero, Jugador.alternarJugador(jugador), capa + 1, alfa_actual, beta);
                    nuevoTablero = null;
                    if (valorSucesor >= beta) {
                        return beta;
                    }
                    aux = maximo2(aux, valorSucesor);
                    alfa_actual = maximo2(alfa_actual, valorSucesor);
                }
            }
            return aux;
        } else if (esCapaMIN(capa)) {
            int beta_actual = beta;
            int aux = Evaluador.MAXIMO;
            for (col = 0; col < Tablero.NCOLUMNAS; col++) {
                if (movimientosPosibles[col]) {
                    nuevoTablero = (Tablero) tablero.clone();
                    nuevoTablero.anadirFicha(col, jugador);
                    nuevoTablero.obtenerGanador();
                    int valorSucesor = ALFABETA(nuevoTablero, Jugador.alternarJugador(jugador), capa + 1, alfa, beta_actual);
                    nuevoTablero = null;
                    if (valorSucesor <= alfa) {
                        return alfa;
                    }
                    aux = minimo2(aux, valorSucesor);
                    beta_actual = minimo2(beta_actual, valorSucesor);
                }
            }
            return aux;
        }
        return 0;
    }

    public void establecerCapaMaxima(int capaMaxima) {
        _capaMaxima = capaMaxima;
    }

    public void establecerEvaluador(Evaluador evaluador) {
        _evaluador = evaluador;
    }

    private static final boolean esCapaMIN(int capa) {
        return (capa % 2) == 1;
    }

    private static final boolean esCapaMAX(int capa) {
        return (capa % 2) == 0;
    }

    private static final int maximo2(int v1, int v2) {
        return v1 > v2 ? v1 : v2;
    }

    private static final int minimo2(int v1, int v2) {
        return v1 < v2 ? v1 : v2;
    }
}
