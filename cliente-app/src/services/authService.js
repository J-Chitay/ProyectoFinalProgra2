// src/services/authService.js
import axios from "axios";

export async function loginAdmin(email, password) {
    try {
        const res = await axios.post("http://localhost:8081/auth/login", { email, password });
        // Backend debe devolver: { token: "...", nombre: "..." }
        return res.data;
    } catch (err) {
        throw new Error(err.response?.data || "Error al iniciar sesión");
    }
}
