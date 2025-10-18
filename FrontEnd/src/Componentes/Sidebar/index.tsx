import { useState } from "react";
import { Link } from "react-router-dom";

export default function SideBar() {
  const [isUsuariosOpen, setIsUsuariosOpen] = useState(false);
  const [isFilmesOpen, setIsFilmesOpen] = useState(false);

  return (
    <ul
      className="list-unstyled position-fixed top-0 start-0 vh-100 p-0 m-0 bg-black border-end border-secondary shadow"
      style={{ width: "250px", overflowY: "auto" }}
    >
      <li className="border-bottom border-secondary">
        <Link to="/" className="nav-link text-white ps-4 py-3 fw-semibold">
          Home
        </Link>
      </li>

      <li className="border-bottom border-secondary">
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
            <Link
              to="/cadastrarFilme"
              className="nav-link text-white-50 ps-5 py-2"
            >
              Cadastrar Filme
            </Link>
          </li>
          <li>
            <Link to="/filmes" className="nav-link text-white-50 ps-5 py-2">
              Catálogo de Filmes
            </Link>
          </li>
        </ul>
      </li>
    </ul>
  );
}
