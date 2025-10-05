import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/login-api"

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await login(email, senha);
      navigate("/filmes");
    } catch {
      alert("Credenciais inválidas");
    }
  }

  return (
    <div
      className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
      style={{ backgroundColor: "#f8f9fa", zIndex: 1 }}
    >
      <div
        className="card p-4 shadow-lg"
        style={{ minWidth: "350px", borderRadius: "15px", backgroundColor: "white" }}
      >
        <h2 className="text-center mb-3" style={{ color: "#333", fontWeight: 700 }}>
          CineGo Login
        </h2>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email</label>
            <input
              type="text"
              id="email"
              className="form-control"
              placeholder="Digite seu email"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="mb-2">
            <label htmlFor="senha" className="form-label">Senha</label>
            <input
              type="password"
              id="senha"
              className="form-control"
              placeholder="Digite sua senha"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
          </div>

          <button
            type="submit"
            className="btn w-100"
            style={{
              backgroundColor: "#343a40",
              color: "white",
              fontWeight: 600,
              borderRadius: "10px",
              padding: "10px",
              transition: "0.3s",
            }}
          >
            {"Entrar"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
