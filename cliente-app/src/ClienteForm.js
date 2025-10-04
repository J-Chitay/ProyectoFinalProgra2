import { useState } from "react";
import axios from "axios";

export default function ClienteForm() {
    const [form, setForm] = useState({
        nombreCompleto: "",
        identificador: "",
        fechaNacimiento: "",
        telefono: "",
        email: ""
    });

    const [mensaje, setMensaje] = useState("");
    const [tipoMensaje, setTipoMensaje] = useState(""); // success, danger, warning

    // Maneja cambios en los inputs
    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    // Validación simple de campos
    const validarCampos = () => {
        const { nombreCompleto, identificador, fechaNacimiento, telefono, email } = form;

        if (!nombreCompleto || !identificador || !fechaNacimiento || !telefono || !email) {
            setTipoMensaje("warning");
            setMensaje("Debe completar todos los campos obligatorios");
            return false;
        }

        // Validación de email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            setTipoMensaje("danger");
            setMensaje("Formato de email inválido");
            return false;
        }

        // Validación de teléfono (solo números y mínimo 8 dígitos)
        if (isNaN(telefono) || telefono.length < 8) {
            setTipoMensaje("danger");
            setMensaje("Número de teléfono inválido");
            return false;
        }

        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validarCampos()) return;

        const data = {
            nombreCompleto: form.nombreCompleto,
            identificador: form.identificador,
            fechaNacimiento: form.fechaNacimiento,
            telefono: Number(form.telefono),
            email: form.email
        };

        try {
            const res = await axios.post("http://localhost:8081/clientes", data);

            setTipoMensaje("success");
            setMensaje(res.data.message);

            setForm({
                nombreCompleto: "",
                identificador: "",
                fechaNacimiento: "",
                telefono: "",
                email: ""
            });

        } catch (err) {
            if (err.response) {
                setTipoMensaje("danger");
                setMensaje(err.response.data.message || "Error al registrar, por favor intente más tarde");
            } else {
                setTipoMensaje("danger");
                setMensaje("Error de conexión con el servidor");
            }
        }
    };



    return (
        <div className="container mt-3">
            <h2>Agregar Cliente</h2>

            {mensaje && (
                <div className={`alert alert-${tipoMensaje}`} role="alert">
                    {mensaje}
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="nombreCompleto"
                    placeholder="Nombre completo"
                    value={form.nombreCompleto}
                    onChange={handleChange}
                    className="form-control mb-2"
                />

                <input
                    type="text"
                    name="identificador"
                    placeholder="Identificador (DPI, NIT, etc.)"
                    value={form.identificador}
                    onChange={handleChange}
                    className="form-control mb-2"
                />

                <input
                    type="date"
                    name="fechaNacimiento"
                    value={form.fechaNacimiento}
                    onChange={handleChange}
                    className="form-control mb-2"
                />


                <input
                    type="text"
                    name="telefono"
                    placeholder="Teléfono"
                    value={form.telefono}
                    onChange={handleChange}
                    className="form-control mb-2"
                />

                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleChange}
                    className="form-control mb-2"
                />

                <button type="submit" className="btn btn-primary">
                    Guardar
                </button>
            </form>
        </div>
    );
}
