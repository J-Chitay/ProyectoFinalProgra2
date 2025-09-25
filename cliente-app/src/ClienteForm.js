import React, { useState, useEffect } from "react";
import axios from "axios";

function ClienteForm({ clienteEdit, onSave }) {
    const [nombre, setNombre] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    useEffect(() => {
        if (clienteEdit) {
            setNombre(clienteEdit.nombre);
            setEmail(clienteEdit.email);
            setPassword(clienteEdit.password || "");
            setConfirmPassword(clienteEdit.password || "");
        }
    }, [clienteEdit]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (clienteEdit) {
                // ✅ Actualizar
                await axios.put(`http://localhost:8081/clientes/${clienteEdit.id}`, {
                    nombre,
                    email,
                    password,
                    confirmPassword,
                });
                alert("Cliente actualizado");
            } else {
                // ✅ Crear
                await axios.post("http://localhost:8081/clientes", {
                    nombre,
                    email,
                    password,
                    confirmPassword,
                });
                alert("Cliente registrado");
            }
            setNombre("");
            setEmail("");
            setPassword("");
            setConfirmPassword("");
            onSave(); // recargar lista
        } catch (error) {
            alert("Error: " + (error.response?.data || error.message));
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>{clienteEdit ? "Editar Cliente" : "Registrar Cliente"}</h2>
            <input
                type="text"
                placeholder="Nombre"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
            />
            <br />
            <input
                type="email"
                placeholder="Correo"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <br />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <br />
            <input
                type="password"
                placeholder="Confirmar contraseña"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <br />
            <button type="submit">
                {clienteEdit ? "Actualizar" : "Registrar"}
            </button>
        </form>
    );
}

export default ClienteForm;
