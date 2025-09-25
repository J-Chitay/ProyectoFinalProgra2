import React, { useState } from "react";
import ClienteForm from "./ClienteForm";
import ClienteList from "./ClienteList";

function App() {
    const [clienteEdit, setClienteEdit] = useState(null);
    const [refresh, setRefresh] = useState(false);

    const handleSave = () => {
        setClienteEdit(null);
        setRefresh(!refresh);
    };

    return (
        <div>
            <ClienteForm clienteEdit={clienteEdit} onSave={handleSave} />
            <ClienteList key={refresh} onEdit={setClienteEdit} />
        </div>
    );
}

export default App;
