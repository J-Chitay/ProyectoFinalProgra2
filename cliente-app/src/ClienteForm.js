import React, { useState } from 'react';
import axios from 'axios';

function ClienteForm() {
    const [nombre, setNombre] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/clientes', {
                nombre,
                email,
                password,
                confirmPassword
            });
            console.log('Respuesta backend:', response); // <--- VER DETALLE
            alert('Cliente registrado: ' + response.data.nombre);
            setNombre('');
            setEmail('');
            setPassword('');
            setConfirmPassword('');
        } catch (error) {
            console.error('Error completo:', error);  // <--- VER DETALLE
            alert('Error: ' + (error.response?.data || error.message));
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Nombre"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
            />
            <input
                type="email"
                placeholder="Correo"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <input
                type="password"
                placeholder="Confirmar contraseña"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <button type="submit">Registrar</button>
        </form>
    );
}

export default ClienteForm;
