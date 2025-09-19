package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository;
import java.util.List;

public interface Irepository<T, K> {
    boolean agregar(T obj);
    T obtener(K id);
    boolean actualizar(T obj);
    boolean eliminar(K id);
    List<T> listarTodos();
}
