import java.util.ArrayList;

public class ArbolBMas {

    //Atributos
    private int      orden;
    private NodoBMas raiz;

    //Metodos

    //Contructor
    public ArbolBMas (int pOrden){
        orden = pOrden;
        if (orden < 3) orden = 3;
        raiz  = new NodoBMas(true);
    }

    //Getters

    public int getOrden() {
        return orden;
    }

    //Setters

    public void setOrden(int otroOrden) {
        orden = otroOrden;
    }

    public void insertar (int llave) {
        if (raiz.getLlaves().size() == orden - 1) {
            NodoBMas nuevaRaiz = new NodoBMas(false);
            nuevaRaiz.getHijos().add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
        insertarNoLleno(raiz, llave);
    }

    private void insertarNoLleno(NodoBMas pNodo, int pLlave){
        int i = pNodo.getLlaves().size() - 1;
        if (pNodo.esHoja()){
            while (i >= 0 && pLlave < pNodo.getLlaves().get(i)){
                i--;
            }
            pNodo.getLlaves().add(i + 1, pLlave);
        } else {
            while (i >= 0 && pLlave < pNodo.getLlaves().get(i)){
                i--;
            }
            i++;
            NodoBMas hijo = pNodo.getHijos().get(i);
            if (hijo.getLlaves().size() == orden - 1) {
                dividirHijo(pNodo, i);
                if (pLlave > pNodo.getLlaves().get(i)){
                    i++;
                }
            }
            insertarNoLleno(pNodo.getHijos().get(i), pLlave);
        }
    }

    private void dividirHijo (NodoBMas padre, int indice) {
        NodoBMas nodoLleno = padre.getHijos().get(indice);
        NodoBMas nuevoNodo = new NodoBMas(nodoLleno.esHoja());
        int      mitad     = orden / 2;

        if (nodoLleno.esHoja()){
            // mover la mitad derecha de llaves a la nueva hoja
            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0, mitad)));

            // ajustar enlaces entre hojas
            nuevoNodo.setSiguienteHoja(nodoLleno.siguienteHoja());
            nodoLleno.setSiguienteHoja(nuevoNodo);

            // la clave promotora es la primera llave de la nueva hoja
            int llaveMedia = nuevoNodo.getLlaves().get(0);
            padre.getLlaves().add(indice, llaveMedia);
            padre.getHijos() .add(indice + 1, nuevoNodo);
        } else {
            // nodo interno: promover la llave central y repartir llaves/hijos
            int claveMedia = nodoLleno.getLlaves().get(mitad);

            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad + 1, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0,mitad)));

            // mover hijos correspondientes si existían
            if (!nodoLleno.getHijos().isEmpty()) {
                ArrayList<NodoBMas> hijosRight = new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size()));
                // recortar hijos en nodoLleno
                nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size()).clear();
                nuevoNodo.setHijos(hijosRight);
            }

            padre.getLlaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    public void imprimirArbol(){
        imprimirNodo(raiz, "", true);
    }

    private void imprimirNodo(NodoBMas nodo, String indentacion, boolean esUltimo){
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja" : "Interno") + " Nodo: " + nodo.getLlaves());
        indentacion += esUltimo ? "  " : "|  ";
        for (int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    public boolean buscar(int llave){
        return buscarNodo(raiz, llave);
    }

    private boolean buscarNodo(NodoBMas nodo, int llave) {
        int i = 0;
        while (i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)){
            i++;
        }
        if(i < nodo.getLlaves().size() && llave == nodo.getLlaves().get(i)) {
            return true;
        }
        if(nodo.esHoja()) {
            return false;
        } else {
            return buscarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Busca la hoja donde debería estar llaveInicial y muestra n llaves en orden usando enlaces entre hojas.
    public void recorrer(int llaveInicial, int n) {
        NodoBMas nodo = raiz;
        // bajar hasta hoja correspondiente
        while (!nodo.esHoja()){
            int i = 0;
            while (i < nodo.getLlaves().size() && llaveInicial > nodo.getLlaves().get(i)) i++;
            nodo = nodo.getHijos().get(i);
        }

        // en la hoja, busca la posición inicial
        int pos = 0;
        while (pos < nodo.getLlaves().size() && nodo.getLlaves().get(pos) < llaveInicial) pos++;

        int mostrados = 0;
        NodoBMas actual = nodo;
        while (actual != null && mostrados < n){
            while (pos < actual.getLlaves().size() && mostrados < n){
                System.out.println(actual.getLlaves().get(pos));
                pos++;
                mostrados++;
            }
            actual = actual.siguienteHoja();
            pos = 0;
        }
        if (mostrados == 0) System.out.println("No se encontraron elementos a partir de la llave dada.");
    }

    public boolean eliminar(int llave) {
        NodoBMas nodo = raiz;
        // bajar hasta hoja
        while (!nodo.esHoja()){
            int i = 0;
            while (i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)) i++;
            nodo = nodo.getHijos().get(i);
        }
        int pos = -1;
        for (int i = 0; i < nodo.getLlaves().size(); i++){
            if (nodo.getLlaves().get(i) == llave) { pos = i; break; }
        }
        if (pos == -1) return false;
        nodo.getLlaves().remove(pos);

        // ajustar raíz si es necesario
        if (!raiz.esHoja() && raiz.getLlaves().isEmpty() && raiz.getHijos().size() == 1){
            raiz = raiz.getHijos().get(0);
        }
        return true;
    }
}
