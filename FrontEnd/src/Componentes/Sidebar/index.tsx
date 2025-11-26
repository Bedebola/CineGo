import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getRoleFromToken } from "../../api/api";

export default function SideBar() {
  const [isUsuariosOpen, setIsUsuariosOpen] = useState(false);
  const [isFilmesOpen, setIsFilmesOpen] = useState(false);
  const [isMeusDadosOpen, setIsMeusDadosOpen] = useState(false);
  const [role, setRole] = useState<string | null>(null);

  useEffect(() => {
    async function fetchRole() {
      const r = await getRoleFromToken();
      setRole(r);
    }
    fetchRole();
  }, []);

  return (
    <ul
      className="list-unstyled position-fixed top-0 start-0 vh-100 p-0 m-0 bg-black border-end border-secondary shadow"
      style={{ width: "250px", overflowY: "auto" }}
    >
      {role === "ADMIN" && (
        <li className="border-bottom border-secondary">
          <Link to="/" className="nav-link text-white ps-4 py-3 fw-semibold">
            Home
          </Link>
        </li>
      )}

      <li className="border-bottom border-secondary">
        {role === "ADMIN" && (
          <>
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                setIsUsuariosOpen((v) => !v);
              }}
              className="nav-link text-white ps-4 py-3 fw-semibold"
              aria-expanded={isUsuariosOpen}
              aria-controls="submenuusuario"
            >
              Usuarios
            </a>

            <ul
              id="submenuusuario"
              className={`list-unstyled collapse ${isUsuariosOpen ? "show" : ""}`}
            >
              <li>
                <Link
                  to="/usuariosListView"
                  className="nav-link text-white-50 ps-5 py-2"
                >
                  Usuarios
                </Link>
              </li>
            </ul>
          </>
        )}

        <a
          href="#"
          onClick={(e) => {
            e.preventDefault();
            setIsFilmesOpen((v) => !v);
          }}
          className="nav-link text-white ps-4 py-3 fw-semibold"
          aria-expanded={isFilmesOpen}
          aria-controls="submenufilme"
        >
          Filmes
        </a>
        <ul
          id="submenufilme"
          className={`list-unstyled collapse ${isFilmesOpen ? "show" : ""}`}
        >
          <li>
            <Link to="/filmes" className="nav-link text-white-50 ps-5 py-2">
              Catalogo de Filmes
            </Link>
          </li>
        </ul>
      </li>
    </ul>
  );
}