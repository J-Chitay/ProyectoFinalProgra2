// src/Dashboard.js
import React from "react";

export default function Dashboard() {
    const nombre = localStorage.getItem("nombre") || "Usuario";

    return (
        <div>
            <h2>Bienvenido, {nombre}</h2>
            <p>Selecciona una opción del menú lateral.</p>
        </div>
    );
}
