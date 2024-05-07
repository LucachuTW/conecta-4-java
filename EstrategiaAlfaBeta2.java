public class EstrategiaAlfaBeta2 extends Estrategia {
    private Evaluador _evaluador;
    private int _capaMaxima;
    private int _jugadorMAX;

    public EstrategiaAlfaBeta2() {
    }

    public EstrategiaAlfaBeta2(Evaluador evaluador, int capaMaxima) {
        this.establecerEvaluador(evaluador);
        this.establecerCapaMaxima(capaMaxima);
    }

    public int buscarMovimiento(Tablero tablero, int jugador) {
        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;
        int col, valorSucesor;
        int mejorPosicion = -1;
        int mejorValor = _evaluador.MINIMO;

        _jugadorMAX = jugador;
        for (col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                valorSucesor = ALFABETA2(nuevoTablero, Jugador.alternarJugador(jugador), 1, _evaluador.MINIMO, _evaluador.MAXIMO);

                if (valorSucesor >= mejorValor) {
                    mejorValor = valorSucesor;
                    mejorPosicion = col;
                }
            }
        }
        return (mejorPosicion);
    }

    public int ALFABETA2(Tablero tablero, int jugador, int capa, int alfa, int beta) {
        if (tablero.hayEmpate()) {
            return (0);
        }
        if (tablero.esGanador(_jugadorMAX)) {
            return (_evaluador.MAXIMO);
        }
        if (tablero.esGanador(Jugador.alternarJugador(_jugadorMAX))) {
            return (_evaluador.MINIMO);
        }
        if (capa == (_capaMaxima)) {
            return (_evaluador.valoracion(tablero, _jugadorMAX));
        }

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;
        int col, valor, valorSucesor;

        if (esCapaMAX(capa)) {
            int alfa_actual = alfa;
            int aux = _evaluador.MINIMO;
            for (col = 0; col < Tablero.NCOLUMNAS; col++) {
                if (movimientosPosibles[col]) {
                    nuevoTablero = (Tablero) tablero.clone();
                    nuevoTablero.anadirFicha(col, jugador);
                    nuevoTablero.obtenerGanador();
                    valorSucesor = ALFABETA2(nuevoTablero, Jugador.alternarJugador(jugador), capa + 1, alfa_actual, beta);
                    nuevoTablero = null;
                    if (valorSucesor >= beta) {
                        return (beta);
                    }
                    aux = maximo2(aux, valorSucesor);
                    alfa_actual = maximo2(alfa_actual, valorSucesor);
                }
            }
            return (aux); // Se inicializa auxiliar 
        } else if (esCapaMIN(capa)) {
            int beta_actual = beta;
            int aux = _evaluador.MAXIMO;
            for (col = 0; col < Tablero.NCOLUMNAS; col++) {
                if (movimientosPosibles[col]) {
                    nuevoTablero = (Tablero) tablero.clone();
                    nuevoTablero.anadirFicha(col, jugador);
                    nuevoTablero.obtenerGanador();
                    valorSucesor = ALFABETA2(nuevoTablero, Jugador.alternarJugador(jugador), capa + 1, alfa, beta_actual);
                    nuevoTablero = null;
                    if (valorSucesor <= alfa) {
                        return (alfa);
                    }
                    aux = minimo2(aux, valorSucesor);
                    beta_actual = minimo2(beta_actual, valorSucesor);
                }
            }
            return (aux); // Se inicializa auxiliar 
        }
        return (0); // Valor por defecto
    }

    public void establecerCapaMaxima(int capaMaxima) {
        _capaMaxima = capaMaxima;
    }

    public void establecerEvaluador(Evaluador evaluador) {
        _evaluador = evaluador;
    }

    private static final boolean esCapaMIN(int capa) {
        return ((capa % 2) == 1); // es impar
    }

    private static final boolean esCapaMAX(int capa) {
        return ((capa % 2) == 0); // es par
    }

    private static final int maximo2(int v1, int v2) {
        if (v1 > v2)
            return (v1);
        else
            return (v2);
    }

    private static final int minimo2(int v1, int v2) {
        if (v1 < v2)
            return (v1);
        else
            return (v2);
    }
}
