import React, { useEffect, useState } from "react";
import axios from "axios";

function ClienteList({ onEdit }) {
    const [clientes, setClientes] = useState([]);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = async () => {
        const response = await axios.get("http://localhost:8081/clientes");
        setClientes(response.data);
    };

    const handleDelete = async (id) => {
        await axios.delete(`http://localhost:8081/clientes/${id}`);
        fetchClientes();
    };

    return (
        <div>
            <h2>Lista de Clientes</h2>
            <table border="1">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                {clientes.map((c) => (
                    <tr key={c.id}>
                        <td>{c.id}</td>
                        <td>{c.nombre}</td>
                        <td>{c.email}</td>
                        <td>
                            <button onClick={() => onEdit(c)}>Editar</button>
                            <button onClick={() => handleDelete(c.id)}>Eliminar</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default ClienteList;
