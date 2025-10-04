// src/Login.js
import { useState } from "react";
import { loginAdmin } from "./services/authService"; // asegúrate que authService.js esté dentro de src
import { useNavigate } from "react-router-dom";

export default function Login({ onLoginSuccess }) {
    const [form, setForm] = useState({
        email: "",
        password: ""
    });
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!form.email || !form.password) {
            setMensaje("Por favor complete todos los campos correctamente.");
            return;
        }

        try {
            const res = await loginAdmin(form.email, form.password);

            // Guardamos token y nombre
            localStorage.setItem("token", res.token);
            localStorage.setItem("nombre", res.nombre);

            // Llamamos a la función del App.js para cambiar el estado de login
            if (onLoginSuccess) onLoginSuccess();

            navigate("/dashboard");
        } catch (err) {
            setMensaje(err.message || "No se pudo conectar. Intente de nuevo.");
        }
    };

    return (
        <div className="login-container">
            <h2>Login Recepcionista</h2>
            {mensaje && <div className="alert alert-danger">{mensaje}</div>}
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
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
                <button type="submit" className="btn btn-primary">Iniciar Sesión</button>
            </form>
        </div>
    );
}
