import React, { useState, useEffect } from "react";
import { cadastrarUsuario } from "../../api/usuarios-api";

function CadastrarUsuario() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (nome.trim() && email.trim() && cpf.trim() && senha.trim()) {
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [nome, email, cpf, senha]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage("");

    try {
      await cadastrarUsuario({ nome, email, cpf, senha, role: "ADMIN" });
      setMessage("Usuário cadastrado com sucesso!");
      setNome("");
      setEmail("");
      setCpf("");
      setSenha("");
    } catch (error) {
      console.error(error);
      setMessage("Erro ao cadastrar usuário. Tente novamente.");
    }
  };

  return (
    <div
      className="d-flex justify-content-center align-items-center"
      style={{ minHeight: "80vh", marginLeft: "250px", padding: "20px" }}
    >
      <div
        className="card p-4 shadow"
        style={{
          minWidth: "400px",
          width: "100%",
          maxWidth: "500px",
          borderRadius: "12px",
        }}
      >
        <h2
          className="text-center mb-4"
          style={{ fontWeight: "700", color: "#333" }}
        >
          Cadastrar Usuário
        </h2>

            {message && (
              <div
                className={`alert ${
                  message.includes("sucesso") ? "alert-success" : "alert-danger"
                } mb-3`}
                role="alert"
              >
                {message}
              </div>
            )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="nomeUsuario" className="form-label">
              Nome do Usuário:
            </label>
            <input
              type="text"
              id="nomeUsuario"
              className="form-control"
              placeholder="Digite o nome do Usuário"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="emailUsuario" className="form-label">
              E-mail do Usuário:
            </label>
            <input
              type="email"
              id="emailUsuario"
              className="form-control"
              placeholder="Digite o e-mail do Usuário"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="cpfUsuario" className="form-label">
              CPF do Usuário:
            </label>
            <input
              type="text"
              id="cpfUsuario"
              className="form-control"
              placeholder="Digite o CPF do Usuário"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
            />
          </div>

          <div className="mb-4">
            <label htmlFor="senhaUsuario" className="form-label">
              Senha do Usuário:
            </label>
            <input
              type="password"
              id="senhaUsuario"
              className="form-control"
              placeholder="Digite a senha do Usuário"
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
              fontWeight: "600",
              borderRadius: "10px",
              padding: "10px",
              transition: "0.3s",
            }}
            disabled={!isFormValid}
          >
            Cadastrar
          </button>
        </form>
      </div>
    </div>
  );
}

export default CadastrarUsuario;
