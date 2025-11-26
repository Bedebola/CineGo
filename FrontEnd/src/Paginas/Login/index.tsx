import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/loginService";
import { useDispatch } from "react-redux";
import { loginSucesso } from "../../Redux/authSlice";
import { getRoleFromToken } from "../../api/api";

function Login() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      const response = await login(email, senha);
      const token = response.token;

      if (token) {
        dispatch(
          loginSucesso({
            usuario: { email: email, nome: "" },
            token: token,
          })
        );

        const role = getRoleFromToken(token);
        console.log(role)

        if (role === "ADMIN") {
          navigate("/");
        } else {
          navigate("/filmes");
        }

      } else {
        alert("Token não recebido. Verifique a resposta do servidor.");
      }

    } catch (error) {
      console.error("Erro no login:", error);
      alert("Credenciais inválidas");
    }
  }

  const handleRegisterClick = () => {
    navigate("/cadastroDeNovaEmpresa");
  };

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
            Entrar
          </button>
        </form>
        <div>
          <button
            type="button"
            onClick={handleRegisterClick}
            className="btn w-100"
            style={{
              backgroundColor: "#b7bcc2ff",
              color: "white",
              fontWeight: 600,
              borderRadius: "10px",
              padding: "10px",
              transition: "0.3s",
            }}
          >
            Registre-se
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
