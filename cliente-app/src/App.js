import React, { useState } from "react";

import Sidebar from "./Sidebar";
import Dashboard from "./Dashboard";
import Login from "./Login";
import ClienteForm from "./ClienteForm";
import AuditoriaList from "./AuditoriaList";
import ServicioList from "./ServicioList";
import AgendarCita from "./AgendarCita";
import axios from "axios";
import "./App.css";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [pantalla, setPantalla] = useState("dashboard");
    const [clientes, setClientes] = useState([]); // Para la lista de clientes

    // Función para cargar clientes desde el backend
    const fetchClientes = async () => {
        try {
            const res = await axios.get("http://localhost:8081/clientes");
            setClientes(res.data);
        } catch (err) {
            console.error("Error al cargar clientes", err);
        }
    };

    const handleLoginSuccess = () => {
        setIsLoggedIn(true);
        if (pantalla === "clientes") fetchClientes(); // Cargar clientes al inicio
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setPantalla("dashboard");
    };

    // Renderizado condicional según la pantalla
    const renderContenido = () => {
        switch (pantalla) {
            case "clientes":
                return (
                    <ClienteForm
                        onSuccess={() => {
                            fetchClientes(); // Actualiza lista
                            console.log("Cliente registrado o actualizado");
                        }}
                        clientes={clientes} // Pasamos lista a ClienteForm si quieres mostrar tabla dentro
                    />
                );
            case "auditoria":
                return <AuditoriaList />;
            case "servicios":
                return <ServicioList />;
            case "agendar-cita":
                return <AgendarCita />;
            default:
                return <Dashboard />;
        }
    };

    if (!isLoggedIn) {
        return <Login onLoginSuccess={handleLoginSuccess} />;
    }

    return (
        <div className="app-container" style={{ display: "flex", height: "100vh" }}>
            <aside className="sidebar">
                <Sidebar setPantalla={setPantalla} handleLogout={handleLogout} />
            </aside>
            <main className="contenido" style={{ flex: 1, padding: "20px", overflowY: "auto" }}>
                {renderContenido()}
            </main>
        </div>
    );
}

export default App;
