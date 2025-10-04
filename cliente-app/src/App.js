import React, { useState } from "react";
import Sidebar from "./Sidebar";
import Dashboard from "./Dashboard";
import Login from "./Login";
import ClienteForm from "./ClienteForm";
import ClienteList from "./ClienteList";
import "./App.css"; // aquí estarán los estilos

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [pantalla, setPantalla] = useState("dashboard");

    const handleLoginSuccess = () => {
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setPantalla("dashboard");
    };

    const renderContenido = () => {
        switch (pantalla) {
            case "clientes-form":
                return <ClienteForm />;
            case "clientes-list":
                return <ClienteList />;
            default:
                return <Dashboard />;
        }
    };

    if (!isLoggedIn) {
        return <Login onLoginSuccess={handleLoginSuccess} />;
    }

    return (
        <div className="app-container">
            <aside className="sidebar">
                <Sidebar setPantalla={setPantalla} />
                <button className="logout-btn" onClick={handleLogout}>
                    Cerrar sesión
                </button>
            </aside>
            <main className="contenido">
                {renderContenido()}
            </main>
        </div>
    );
}

export default App;
