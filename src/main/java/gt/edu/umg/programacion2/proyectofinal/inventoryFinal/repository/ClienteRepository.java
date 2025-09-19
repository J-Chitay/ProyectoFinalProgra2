package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;

import java.util.*;


import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository{
    private final Map<Long, Cliente> clientes = new HashMap<>();
    private long nextId = 1;

    public Cliente save(Cliente c) {
        if (c.getId() == null) {
            c.setId(nextId++);
        }
        clientes.put(c.getId(), c);
        return c;
    }

    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public void deleteById(Long id) {
        clientes.remove(id);
    }

    public Optional<Cliente> findByEmail(String email) {
        return clientes.values().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }


    public boolean agregar(Cliente c) {
        if (c.getId() == null) {
            c.setId(nextId++);
        }
        return clientes.put(c.getId(), c) == null; // true si se agregó
    }
}
