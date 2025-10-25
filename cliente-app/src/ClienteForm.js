import React, { useState, useEffect } from "react";
import axios from "axios";

export default function ClienteForm() {
    const [clientes, setClientes] = useState([]);
    const [form, setForm] = useState({
        nombreCompleto: "",
        dpi: "",
        correo: "",
        telefono: "",
        direccion: "",
        fechaNacimiento: "",
    });
    const [editCliente, setEditCliente] = useState(null);
    const [mensaje, setMensaje] = useState("");
    const [tipoMensaje, setTipoMensaje] = useState("");

    // Cargar clientes al inicio
    const fetchClientes = async () => {
        try {
            const res = await axios.get("http://localhost:8081/clientes");
            setClientes(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchClientes();
    }, []);

    // Si editCliente cambia, llenar formulario
    useEffect(() => {
        if (editCliente) setForm(editCliente);
        else
            setForm({
                nombreCompleto: "",
                dpi: "",
                correo: "",
                telefono: "",
                direccion: "",
                fechaNacimiento: "",
            });
    }, [editCliente]);

    const handleChange = (e) =>
        setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editCliente) {
                await axios.put(
                    `http://localhost:8081/clientes/${editCliente.id}`,
                    form
                );
                setMensaje("Cliente actualizado exitosamente");
            } else {
                await axios.post("http://localhost:8081/clientes", form);
                setMensaje("Cliente registrado exitosamente");
            }
            setTipoMensaje("success");
            setEditCliente(null);
            fetchClientes(); // recargar lista
        } catch (err) {
            setTipoMensaje("danger");
            setMensaje(err.response?.data?.message || err.message);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm("¿Desea eliminar este cliente?")) {
            try {
                await axios.delete(`http://localhost:8081/clientes/${id}`);
                fetchClientes();
                setMensaje("Cliente eliminado correctamente");
                setTipoMensaje("success");
            } catch (err) {
                setMensaje("Error al eliminar cliente");
                setTipoMensaje("danger");
            }
        }
    };

    return (
        <div className="container mt-3">
            <h2>{editCliente ? "Editar Cliente" : "Registrar Cliente"}</h2>
            {mensaje && <div className={`alert alert-${tipoMensaje}`}>{mensaje}</div>}

            <form onSubmit={handleSubmit}>
                <input
                    name="nombreCompleto"
                    placeholder="Nombre completo"
                    value={form.nombreCompleto}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    name="dpi"
                    placeholder="DPI"
                    value={form.dpi}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    name="correo"
                    placeholder="Correo"
                    value={form.correo}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    name="telefono"
                    placeholder="Teléfono"
                    value={form.telefono}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    name="direccion"
                    placeholder="Dirección"
                    value={form.direccion}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    name="fechaNacimiento"
                    placeholder="Fecha nacimiento (yyyy-MM-dd)"
                    value={form.fechaNacimiento}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <button type="submit" className="btn btn-primary mb-3">
                    {editCliente ? "Actualizar" : "Guardar"}
                </button>
                {editCliente && (
                    <button
                        type="button"
                        className="btn btn-secondary ms-2"
                        onClick={() => setEditCliente(null)}
                    >
                        Cancelar
                    </button>
                )}
            </form>

            <h3>Lista de Clientes</h3>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>DPI</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th>Fecha Nac.</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                {clientes.map((c) => (
                    <tr key={c.id}>
                        <td>{c.id}</td>
                        <td>{c.nombreCompleto}</td>
                        <td>{c.dpi}</td>
                        <td>{c.correo}</td>
                        <td>{c.telefono}</td>
                        <td>{c.direccion}</td>
                        <td>{c.fechaNacimiento}</td>
                        <td>
                            <button
                                className="btn btn-warning me-2"
                                onClick={() => setEditCliente(c)}
                            >
                                Editar
                            </button>
                            <button
                                className="btn btn-danger"
                                onClick={() => handleDelete(c.id)}
                            >
                                Eliminar
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
