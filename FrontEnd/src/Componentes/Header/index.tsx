import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header
      className="position-fixed top-0 start-0 end-0 bg-black border-bottom border-secondary shadow-sm"
      style={{ marginLeft: 250, zIndex: 1040 }}
    >
      <nav className="container d-flex justify-content-center align-items-center gap-4 py-3">
        <Link to="/" className="text-white text-decoration-none">
          Home
        </Link>
        <Link to="/usuarios" className="text-white text-decoration-none">
          Usuarios
        </Link>
      </nav>
    </header>
  );
}
