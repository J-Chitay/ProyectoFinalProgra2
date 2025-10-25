export default function Sidebar({ setPantalla }) {
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
            <button onClick={() => setPantalla("clientes")}>Clientes</button>
            <button onClick={() => setPantalla("auditoria")}>Auditoría</button>
            <button onClick={() => setPantalla("servicios")}>Servicios</button>
            <button onClick={() => setPantalla("agendar-cita")}>Agendar Cita</button>
        </div>
    );
}
