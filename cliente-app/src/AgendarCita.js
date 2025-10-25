import React, { useState, useEffect } from "react";
import axios from "axios";

export default function AgendarCita() {
    const [clientes, setClientes] = useState([]);
    const [servicios, setServicios] = useState([]);
    const [form, setForm] = useState({
        clienteId: "",
        servicioId: "",
        fechaHora: "",
        notas: ""
    });
    const [mensaje, setMensaje] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        // Cargar clientes
        axios.get("http://localhost:8081/clientes")
            .then(res => setClientes(res.data))
            .catch(() => setError("Error al cargar clientes"));

        // Cargar servicios
        axios.get("http://localhost:8081/servicios")
            .then(res => setServicios(res.data))
            .catch(() => setError("Error al cargar servicios"));
    }, []);

    const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async e => {
        e.preventDefault();
        setMensaje("");
        setError("");

        // Validación de 2 horas de anticipación
        const fecha = new Date(form.fechaHora);
        const ahora = new Date();
        if (fecha - ahora < 2 * 60 * 60 * 1000) {
            setError("La cita debe programarse con al menos 2 horas de antelación");
            return;
        }

        try {
            const usuario = localStorage.getItem("nombre") || "Recepcionista";
            const response = await axios.post(`http://localhost:8081/citas/agendar?usuario=${usuario}`, form);
            setMensaje(`Cita programada con éxito para ${new Date(response.data.fechaHora).toLocaleString()}`);
            // Limpiar formulario
            setForm({ clienteId: "", servicioId: "", fechaHora: "", notas: "" });
        } catch (err) {
            if (err.response && err.response.data) {
                setError(err.response.data.message || "Error al agendar la cita");
            } else {
                setError("Error al agendar la cita. Intenta más tarde.");
            }
        }
    };

    return (
        <div>
            <h2>Agendar Nueva Cita</h2>

            {mensaje && <div style={{ color: "green" }}>{mensaje}</div>}
            {error && <div style={{ color: "red" }}>{error}</div>}

            <form onSubmit={handleSubmit}>
                <label>Cliente:</label>
                <select
                    name="clienteId"
                    value={form.clienteId}
                    onChange={handleChange}
                    required
                >
                    <option value="">--Selecciona cliente--</option>
                    {clientes.map(c => (
                        <option key={c.id} value={c.id}>
                            {c.nombreCompleto}
                        </option>
                    ))}
                </select>
                <br />

                <label>Servicio:</label>
                <select
                    name="servicioId"
                    value={form.servicioId}
                    onChange={handleChange}
                    required
                >
                    <option value="">--Selecciona servicio--</option>
                    {servicios.map(s => (
                        <option key={s.id} value={s.id}>
                            {s.nombre}
                        </option>
                    ))}
                </select>
                <br />

                <label>Fecha y hora:</label>
                <input
                    type="datetime-local"
                    name="fechaHora"
                    value={form.fechaHora}
                    onChange={handleChange}
                    required
                />
                <br />

                <label>Notas:</label>
                <textarea
                    name="notas"
                    value={form.notas}
                    onChange={handleChange}
                />
                <br />

                <button type="submit">Programar</button>
            </form>
        </div>
    );
}
