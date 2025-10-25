import { useState } from "react";
import axios from "axios";

export default function Login({ onLoginSuccess }) {
    const [form, setForm] = useState({ email: "", password: "" });
    const [mensaje, setMensaje] = useState("");

    // Actualiza el estado del formulario
    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    // Maneja el submit del formulario
    const handleSubmit = async (e) => {
        e.preventDefault();

        const email = form.email.trim();
        const password = form.password.trim();

        if (!email || !password) {
            setMensaje("Por favor complete todos los campos correctamente.");
            return;
        }

        console.log("Datos enviados al backend:", { email, password });

        try {
            const response = await axios.post(
                "http://localhost:8081/auth/login",
                { email, password },
                { headers: { "Content-Type": "application/json" } }
            );

            if (response.data.status === "success") {
                localStorage.setItem("token", response.data.token);
                localStorage.setItem("nombre", response.data.nombre);
                localStorage.setItem("role", response.data.role);

                // Actualiza el estado de login en App.js
                if (onLoginSuccess) onLoginSuccess();
            } else {
                setMensaje(response.data.message || "Error en login");
            }

        } catch (err) {
            console.error("Error en Axios:", err);
            setMensaje("Error al conectar con el servidor");
        }
    };

    return (
        <div className="login-container">
            <h2>Login Cliente</h2>
            {mensaje && <div className="alert alert-danger">{mensaje}</div>}
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    name="email"
                    placeholder="Correo"
                    value={form.email}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Contraseña"
                    value={form.password}
                    onChange={handleChange}
                    className="form-control mb-2"
                />
                <button type="submit" className="btn btn-primary">
                    Iniciar Sesión
                </button>
            </form>
        </div>
    );
}
