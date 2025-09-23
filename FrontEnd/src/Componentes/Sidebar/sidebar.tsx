import { useState } from "react";
import { Link } from "react-router-dom";

export default function SideBar() {
  const [isCadastroOpen, setIsCadastroOpen] = useState(false);

  const toggleSubmenu = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsCadastroOpen(v => !v);
  };

  return (
    <aside
      className="position-fixed top-0 end-0 vh-100 bg-black bg-dark text-white border-start border-secondary shadow-sm overflow-auto"
      style={{ width: 250 }}
    >
      <ul className="nav flex-column p-0 m-0">
        <li className="nav-item">
          <Link to="/" className="nav-link text-white px-4 py-3">
            Home
          </Link>
        </li>

        <li className="nav-item">
          <a
            href="#"
            onClick={toggleSubmenu}
            className="nav-link text-white px-4 py-3"
          >
            Cadastro
          </a>

          <ul className={`list-unstyled collapse ${isCadastroOpen ? "show" : ""}`}>
            <li>
              <Link to="/usuario" className="nav-link text-white ps-5 py-2">
                Usuario
              </Link>
            </li>
          </ul>
        </li>
      </ul>
    </aside>
  );
}
