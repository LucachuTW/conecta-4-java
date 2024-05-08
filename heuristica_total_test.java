public class heuristica_total_test {

    public static void main(String[] args) {
        // Paso 1: Crear una instancia de heuristica_total
        heuristica_total pruebaHeuristica = new heuristica_total();

        // Paso 2: Crear un objeto Tablero (asumiendo que ya tienes una clase Tablero)
        Tablero tablero = new Tablero(); // Asegúrate de inicializar el tablero adecuadamente

        // Paso 3: Definir un jugador específico
        int jugador = 1; // Identificador del jugador

        double[] pesos = {1, 1, 1, 1, 1}; // Pesos iniciales

        // Paso 4: Llamar a la función encontrarPesosOptimos
        int numPartidas = 5; // Número de partidas a simular

        double[] pesosOptimos = pruebaHeuristica.encontrarPesosOptimos(new EvaluadorPonderadoDinamico(pesos), tablero, jugador, numPartidas);

        // Imprimir los pesos óptimos encontrados
        System.out.println("Pesos óptimos encontrados:");
        for (double peso : pesosOptimos) {
            System.out.print(peso + " ");
        }
    }
}
