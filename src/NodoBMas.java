import java.util.ArrayList;
import java.util.List;

public class NodoBMas {

    private boolean        esHoja;
    private List<Integer>  llaves;
    private List<NodoBMas> hijos;
    private NodoBMas       siguiente; // añadido para enlace entre hojas

    public NodoBMas(boolean pEsHoja){
        esHoja    = pEsHoja;
        llaves    = new ArrayList<>();
        hijos     = new ArrayList<>();
        siguiente = null;
    }

    /// Getters
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getLlaves() {
        return llaves;
    }

    public List<NodoBMas> getHijos() {
        return hijos;
    }

    public NodoBMas siguienteHoja() {
        return siguiente;
    }

    /// Setters
    public void setLlaves(List<Integer> otrallaves) {
        llaves = otrallaves;
    }

    public void setHijos(List<NodoBMas> otroHijos) {
        hijos = otroHijos;
    }

    public void setSiguienteHoja(NodoBMas pSiguiente) {
        siguiente = pSiguiente;
    }
}
