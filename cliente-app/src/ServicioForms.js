import { useState } from "react";
import axios from "axios";

export default function ServicioForm({ onAdd }) {
    const [form, setForm] = useState({
        nombre: "",
        descripcion: "",
        precio: ""
    });
    const [mensaje, setMensaje] = useState("");

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        if(!form.nombre || !form.descripcion || !form.precio) {
            setMensaje("Por favor complete todos los campos.");
            return;
        }

        try {
            await axios.post("http://localhost:8081/servicios", form);
            setMensaje("Servicio agregado correctamente.");
            setForm({ nombre: "", descripcion: "", precio: "" });
            if(onAdd) onAdd();
        } catch(err) {
            setMensaje(err.response?.data || "Error al agregar el servicio.");
        }
    };

    return (
        <div className="servicio-form">
            {mensaje && <div className="alert alert-info">{mensaje}</div>}
            <form onSubmit={handleSubmit}>
                <input type="text" name="nombre" placeholder="Nombre" value={form.nombre} onChange={handleChange} className="form-control mb-2"/>
                <input type="text" name="descripcion" placeholder="Descripción" value={form.descripcion} onChange={handleChange} className="form-control mb-2"/>
                <input type="number" name="precio" placeholder="Precio" value={form.precio} onChange={handleChange} className="form-control mb-2"/>
                <button type="submit" className="btn btn-success">Agregar Servicio</button>
            </form>
        </div>
    );
}
