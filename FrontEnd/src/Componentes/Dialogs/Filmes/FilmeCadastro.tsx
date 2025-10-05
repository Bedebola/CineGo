import React, { useState, useEffect, useRef } from "react";
import { cadastrarFilme } from "../../../api/filmes-api";

function CadastrarFilme() {
  const dialogRef = useRef<HTMLDialogElement>(null);
  const [titulo, setTitulo] = useState("");
  const [genero, setGenero] = useState("");
  const [sinopse, setSinopse] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    setIsFormValid(
      titulo.trim() !== "" && genero.trim() !== "" && sinopse.trim() !== ""
    );
  }, [titulo, genero, sinopse]);

  const abrirDialog = () => {
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await cadastrarFilme({ titulo, genero, sinopse });
      fecharDialog();
      setTitulo("");
      setGenero("");
      setSinopse("");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-plus-square"> Cadastrar Filme</i>
      </button>

      <dialog
        ref={dialogRef}
        className="border-0 rounded-4 shadow-lg p-4"
        style={{
          width: "min(500px, 90vw)",
          border: "none",
          borderRadius: "12px",
          boxShadow: "0 5px 15px rgba(0,0,0,0.3)",
        }}
      >
        <form onSubmit={handleSubmit}>
          <h4 className="mb-3 text-center">Cadastrar Filme</h4>

          <div className="mb-3">
            <label htmlFor="tituloFilme" className="form-label">
              Título do Filme:
            </label>
            <input
              type="text"
              id="tituloFilme"
              className="form-control"
              placeholder="Digite o título do filme"
              value={titulo}
              onChange={(e) => setTitulo(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="generoFilme" className="form-label">
              Gênero:
            </label>
            <input
              type="text"
              id="generoFilme"
              className="form-control"
              placeholder="Digite o gênero do filme"
              value={genero}
              onChange={(e) => setGenero(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="sinopseFilme" className="form-label">
              Sinopse:
            </label>
            <textarea
              id="sinopseFilme"
              className="form-control"
              rows={3}
              placeholder="Digite a sinopse do filme"
              value={sinopse}
              onChange={(e) => setSinopse(e.target.value)}
            />
          </div>

          <div className="d-flex justify-content-between mt-4">
            <button
              type="button"
              onClick={fecharDialog}
              className="btn btn-outline-secondary"
            >
              Cancelar
            </button>

            <button
              type="submit"
              className="btn btn-dark"
              disabled={!isFormValid}
            >
              Cadastrar
            </button>
          </div>
        </form>
      </dialog>
    </>
  );
}

export default CadastrarFilme;
