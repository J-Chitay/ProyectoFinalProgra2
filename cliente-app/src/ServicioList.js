import { useEffect, useState } from "react";
import axios from "axios";
import ServicioForm from "./ServicioForms";

export default function ServicioList() {
    const [servicios, setServicios] = useState([]);

    const fetchServicios = async () => {
        try {
            const res = await axios.get("http://localhost:8081/servicios");
            setServicios(res.data);
        } catch(err) {
            console.error("Error cargando servicios:", err);
        }
    };

    useEffect(() => {
        fetchServicios();
    }, []);

    const handleAdd = () => {
        fetchServicios();
    };

    return (
        <div className="servicio-list">
            <h2>Lista de Servicios</h2>
            <ServicioForm onAdd={handleAdd} />
            <table className="table mt-3">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                </tr>
                </thead>
                <tbody>
                {servicios.map((s) => (
                    <tr key={s.id}>
                        <td>{s.nombre}</td>
                        <td>{s.descripcion}</td>
                        <td>{s.precio}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
