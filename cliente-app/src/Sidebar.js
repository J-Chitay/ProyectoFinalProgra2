export default function Sidebar({ setPantalla, handleLogout }) {
    return (
        <div
            style={{
                width: "200px",
                backgroundColor: "#2c3e50",
                color: "white",
                display: "flex",
                flexDirection: "column",
                padding: "20px",
            }}
        >
            <h2>Admin</h2>
            <button onClick={() => setPantalla("dashboard")}>Inicio</button>
            <button onClick={() => setPantalla("clientes-form")}>Agregar Cliente</button>
            <button onClick={() => setPantalla("clientes-list")}>Listado de Clientes</button>
            <button onClick={handleLogout}>Cerrar sesión</button>
        </div>
    );
}
