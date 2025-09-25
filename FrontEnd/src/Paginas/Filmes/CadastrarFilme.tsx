import React, { useState, useEffect } from "react";
import { cadastrarFilme } from "../../api/filmes-api";

function CadastrarFilme() {
  const [titulo, setTitulo] = useState("");
  const [sinopse, setSinopse] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    setIsFormValid(titulo.trim() !== "" && sinopse.trim() !== "");
  }, [titulo, sinopse]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage("");

    try {
      await cadastrarFilme({ titulo, sinopse });
      setMessage("Filme cadastrado com sucesso!");
      setTitulo("");
      setSinopse("");
    } catch (error) {
      console.error(error);
      setMessage("Erro ao cadastrar filme. Tente novamente.");
    }
  };

  return (
    <div
      className="d-flex justify-content-center align-items-center"
      style={{ minHeight: "80vh", marginLeft: "250px", padding: "20px" }}
    >
      <div
        className="card p-4 shadow"
        style={{ minWidth: "400px", width: "100%", maxWidth: "500px", borderRadius: "12px" }}
      >
        <h2 className="text-center mb-4" style={{ fontWeight: "700", color: "#333" }}>
          Cadastrar Filme
        </h2>

        {message && (
          <div
            className={`alert ${message.includes("sucesso") ? "alert-success" : "alert-danger"} mb-3`}
            role="alert"
          >
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="tituloFilme" className="form-label">Título do Filme:</label>
            <input
              type="text"
              id="tituloFilme"
              className="form-control"
              placeholder="Digite o título do filme"
              style={{ borderRadius: "10px", padding: "10px" }}
              value={titulo}
              onChange={(e) => setTitulo(e.target.value)}
            />
          </div>

          <div className="mb-4">
            <label htmlFor="sinopseFilme" className="form-label">Sinopse do Filme:</label>
            <textarea
              id="sinopseFilme"
              className="form-control"
              rows={4}
              placeholder="Digite a sinopse do filme"
              style={{ borderRadius: "10px", padding: "10px", resize: "none" }}
              value={sinopse}
              onChange={(e) => setSinopse(e.target.value)}
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

export default CadastrarFilme;
