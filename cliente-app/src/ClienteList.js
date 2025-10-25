import { useEffect, useState } from "react";
import axios from "axios";
import ClienteForm from "./ClienteForm";

export default function ClienteList() {
    const [clientes, setClientes] = useState([]);

    const fetchClientes = async () => {
        try {
            const res = await axios.get("http://localhost:8081/clientes");
            setClientes(res.data);
        } catch(err) {
            console.error("Error al cargar clientes", err);
        }
    };

    useEffect(() => {
        fetchClientes();
    }, []);

    return (
        <div className="cliente-list">
            <h2>Clientes</h2>
            <ClienteForm onSuccess={fetchClientes} />
            <table className="table mt-3">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                </tr>
                </thead>
                <tbody>
                {clientes.map((c) => (
                    <tr key={c.id}>
                        <td>{c.nombre}</td>
                        <td>{c.correo}</td>
                        <td>{c.telefono}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
