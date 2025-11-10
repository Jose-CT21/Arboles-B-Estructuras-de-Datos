import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ArbolBMas arbol = new ArbolBMas(4); // ajusta orden si lo deseas
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("\n--- Menu Arbol B+ (orden " + arbol.getOrden() + ") ---");
            System.out.println("1. Insertar llave");
            System.out.println("2. Buscar llave");
            System.out.println("3. Eliminar llave");
            System.out.println("4. Recorrer desde llave (mostrar n siguientes)");
            System.out.println("5. Imprimir estructura");
            System.out.println("6. Salir");
            System.out.print("Opcion: ");
            int opt = sc.nextInt();
            if (opt == 1){
                System.out.print("Llave a insertar: ");
                int k = sc.nextInt();
                arbol.insertar(k);
                System.out.println("Insertada " + k);
            } else if (opt == 2){
                System.out.print("Llave a buscar: ");
                int k = sc.nextInt();
                boolean existe = arbol.buscar(k);
                System.out.println("Resultado: " + (existe ? "Encontrada" : "No encontrada"));
            } else if (opt == 3){
                System.out.print("Llave a eliminar: ");
                int k = sc.nextInt();
                boolean borrada = arbol.eliminar(k);
                System.out.println(borrada ? "Eliminada" : "No encontrada");
            } else if (opt == 4){
                System.out.print("Llave inicial: ");
                int k = sc.nextInt();
                System.out.print("Cantidad a mostrar: ");
                int n = sc.nextInt();
                System.out.println("Recorrido a partir de " + k + ":");
                arbol.recorrer(k, n);
            } else if (opt == 5){
                arbol.imprimirArbol();
            } else if (opt == 6){
                System.out.println("Saliendo...");
                break;
            } else {
                System.out.println("Opcion no valida.");
            }
        }
        sc.close();
    }
}
