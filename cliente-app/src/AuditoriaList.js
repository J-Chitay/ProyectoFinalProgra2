import React, { useEffect, useState } from "react";
import axios from "axios";

export default function AuditoriaList() {
    const [auditoria, setAuditoria] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8081/auditoria")
            .then(res => setAuditoria(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="container mt-3">
            <h2>Historial de Sesiones</h2>
            <table className="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Usuario</th>
                    <th>Acción</th>
                    <th>Fecha</th>
                </tr>
                </thead>
                <tbody>
                {auditoria.map(a => (
                    <tr key={a.id}>
                        <td>{a.id}</td>
                        <td>{a.usuario}</td>
                        <td>{a.accion}</td>
                        <td>{new Date(a.fecha).toLocaleString()}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
