import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Problema de los Filósofos");
            System.out.println("2. Problema de las N-Reinas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número de filósofos: ");
                    int numFilosofo = scanner.nextInt();
                    FilosofoPrograma.ejecutar(numFilosofo);
                    break;
                case 2:
                    System.out.print("Ingrese el número de reinas: ");
                    int numReinas = scanner.nextInt();
                    NReinasPrograma.ejecutar(numReinas);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intentelo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
