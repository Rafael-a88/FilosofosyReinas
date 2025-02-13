public class NReinasPrograma {
    private int[] tablero;
    private int n;
    private int contadorSoluciones; // Contador de soluciones encontradas

    public NReinasPrograma(int n) {
        this.n = n;
        this.tablero = new int[n];
        this.contadorSoluciones = 0; // Inicializar contador
    }

    private boolean esSeguro(int fila, int col) {
        for (int i = 0; i < fila; i++) {
            if (tablero[i] == col ||
                    tablero[i] - i == col - fila ||
                    tablero[i] + i == col + fila) {
                return false;
            }
        }
        return true;
    }

    private void resolverNReinas(int fila) {
        if (fila == n) {
            imprimirSolucion();
            contadorSoluciones++; // Incrementar contador de soluciones
            if (contadorSoluciones >= 2) { // Si se encontraron 2 soluciones, salir
                System.exit(0);
            }
            return;
        }

        for (int col = 0; col < n; col++) {
            if (esSeguro(fila, col)) {
                tablero[fila] = col;
                resolverNReinas(fila + 1);
                tablero[fila] = -1; // Deshacer el movimiento
            }
        }
    }

    private void imprimirSolucion() {
        System.out.println("Solución posible:");
        for (int fila = n - 1; fila >= 0; fila--) { // De 8 a 1
            for (int col = 0; col < n; col++) { // De A a H
                if (tablero[fila] == col) {
                    System.out.print("Q "); // Colocar reina
                } else {
                    System.out.print(". "); // Espacio vacío
                }
            }
            System.out.println(" " + (fila + 1)); // Imprimir número de fila
        }
        System.out.print("  "); // Espacio para las letras
        for (char col = 'A'; col < 'A' + n; col++) {
            System.out.print(col + " "); // Imprimir letras de columna
        }
        System.out.println();
    }

    public void resolver() { // Método agregado
        resolverNReinas(0);
    }

    public static void ejecutar(int n) {
        NReinasPrograma nReinas = new NReinasPrograma(n);
        nReinas.resolver();
    }
}
