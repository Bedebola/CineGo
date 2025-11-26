import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getRoleFromToken } from "../../api/api";

export default function Header() {
  const [role, setRole] = useState<string | null>(null);

  useEffect(() => {
    async function fetchRole() {
      const r = await getRoleFromToken();
      setRole(r);
    }
    fetchRole();
  }, []);

  return (
    <header
      className="position-fixed top-0 start-0 end-0 bg-black border-bottom border-secondary shadow-sm"
      style={{ marginLeft: 250, zIndex: 1040 }}
    >
      <nav className="container d-flex justify-content-center align-items-center gap-4 py-3">
        {role === "ADMIN" && (
          <Link to="/" className="text-white text-decoration-none">
            Home
          </Link>
        )}
        {role === "ADMIN" && (
          <Link to="/usuariosListView" className="text-white text-decoration-none">
            Usuarios
          </Link>
        )}
        <Link to="/filmes" className="text-white text-decoration-none">
          Catálogo de Filmes
        </Link>
      </nav>
    </header>
  );
}