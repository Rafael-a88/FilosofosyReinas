import java.util.logging.Level;
import java.util.logging.Logger;

public class FilosofoPrograma {

    // Clase Mesa
    public static class Mesa {
        private boolean[] tenedores;

        public Mesa(int numTenedores) {
            this.tenedores = new boolean[numTenedores];
        }

        public int tenedorIzquierda(int i) {
            return i;
        }

        public int tenedorDerecha(int i) {
            if (i == 0) {
                return this.tenedores.length - 1;
            } else {
                return i - 1;
            }
        }

        public synchronized void cogerTenedores(int comensal) {
            while (tenedores[tenedorIzquierda(comensal)] || tenedores[tenedorDerecha(comensal)]) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            tenedores[tenedorIzquierda(comensal)] = true;
            tenedores[tenedorDerecha(comensal)] = true;
        }

        public synchronized void dejarTenedores(int comensal) {
            tenedores[tenedorIzquierda(comensal)] = false;
            tenedores[tenedorDerecha(comensal)] = false;
            notifyAll();
        }
    }

    // Clase Filósofo
    public static class Filosofo extends Thread {
        private Mesa mesa;               // Instancia de la clase Mesa
        private int comensal;           // Número del filósofo (1, 2, 3, ...)
        private int indiceComensal;     // Índice del filósofo en el array de tenedores
        private static int contadorComiendo = 0; // Contador de filósofos comiendo
        private static final Object lock = new Object(); // Objeto para sincronización
        private static boolean todosHanComido = false; // Estado de finalización

        public Filosofo(Mesa m, int comensal) {
            this.mesa = m;               // Asignar la mesa
            this.comensal = comensal;    // Asignar el número del filósofo
            this.indiceComensal = comensal - 1; // Ajustar el índice para el array
        }

        @Override
        public void run() {
            // Cada filósofo solo debe comer una vez
            if (!todosHanComido) {
                pensando(); // El filósofo piensa
                mesa.cogerTenedores(indiceComensal); // Intenta coger los tenedores
                comiendo(); // El filósofo come
                System.out.println("Filósofo " + comensal + " deja de comer, tenedores libres: " +
                        (mesa.tenedorIzquierda(indiceComensal) + 1) + ", " +
                        (mesa.tenedorDerecha(indiceComensal) + 1));

                // Contar cuántos filósofos han comido
                synchronized (lock) {
                    contadorComiendo++;
                    if (contadorComiendo == mesa.tenedores.length) {
                        System.out.println("Todos los filósofos han terminado de comer.");
                        todosHanComido = true; // Cambiar el estado para detener la ejecución
                    }
                }

                mesa.dejarTenedores(indiceComensal); // Deja los tenedores
            }
        }

        public void pensando() {
            System.out.println("Filósofo " + comensal + " está pensando.");
            try {
                sleep((long) (Math.random() * 4000)); // Simula el tiempo de pensar
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            }
        }

        public void comiendo() {
            System.out.println("Filósofo " + comensal + " está comiendo.");
            try {
                sleep((long) (Math.random() * 4000)); // Simula el tiempo de comer
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            }
        }
    }

    // Método para ejecutar el programa
    public static void ejecutar(int numFilosofo) {
        Mesa mesa = new Mesa(numFilosofo); // Crear la mesa con el número de filósofos
        Filosofo[] filosofos = new Filosofo[numFilosofo];

        for (int i = 1; i <= numFilosofo; i++) {
            filosofos[i - 1] = new Filosofo(mesa, i); // Crear filósofos
            filosofos[i - 1].start(); // Iniciar el hilo del filósofo
        }

        // Esperar a que todos los filósofos terminen
        for (Filosofo filosofo : filosofos) {
            try {
                filosofo.join(); // Esperar a que cada filósofo termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("El programa ha finalizado.");
    }
}
