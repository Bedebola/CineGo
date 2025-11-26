import React, { useState, useEffect, useRef } from "react";
import { editarFilme, buscarFilmeId, type Filme, type FilmeViewProps } from "../../../api/filmesService";


function EditarFilme({ filmeId, onChange }: FilmeViewProps) {
  const [filme, setFilme] = useState<Filme | null>(null);
  const [titulo, setTitulo] = useState("");
  const [genero, setGenero] = useState("");
  const [sinopse, setSinopse] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);
  const [message, setMessage] = useState("");
  const dialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    setIsFormValid(titulo.trim() !== "" && sinopse.trim() !== "");
  }, [titulo, genero, sinopse]);

  const abrirDialog = async () => {
    try {
      const dados = await buscarFilmeId(Number(filmeId));
      setFilme(dados);
      setTitulo(dados.titulo);
      setGenero(dados.genero);
      setSinopse(dados.sinopse);

      dialogRef.current?.showModal();
    } catch (error) {
      console.error("Erro ao buscar filme:", error);
    }
  };

  const fecharDialog = () => dialogRef.current?.close();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage("");

    if (!filme) return;

    const filmeAtualizado: Filme = {
      ...filme,
      titulo,
      genero,
      sinopse,
    };

    try {
      await editarFilme(filmeAtualizado);

      setMessage("As alterações foram salvas com sucesso!");

      onChange?.();

      setTimeout(() => fecharDialog(), 1000);

    } catch (error) {
      console.error(error);
      setMessage("Erro ao editar os dados do filme. Tente novamente.");
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-pencil-square"></i>
      </button>

      <style>{`
       #dlg-${Number(filmeId)}::backdrop {
        background: rgba(0, 0, 0, 0.55);
        }
        .form-label {
        color: black !important;
        }
 `}</style>

      <dialog
        id={`dlg-${Number(filmeId)}`}
        ref={dialogRef}
        className="border-0 rounded-4 shadow-lg p-0"
        style={{
          position: "fixed",
          inset: "50% auto auto 50%",
          transform: "translate(-50%, -50%)",
          width: "min(500px, 92vw)",
          background: "transparent",
          zIndex: 1100,
        }}
      >
        <div className="bg-white rounded-4 overflow-hidden">
          <div className="bg-dark text-white px-4 py-3 d-flex justify-content-between align-items-center">
            <h5 className="m-0">{filme?.titulo || "Editar Filme"}</h5>

            <button
              onClick={fecharDialog}
              className="btn-close btn-close-white"
              aria-label="Fechar"
            ></button>
          </div>

          {message && (
            <div
              className={`alert ${message.includes("sucesso") ? "alert-success" : "alert-danger"
                } mb-2`}
              role="alert"
            >
              {message}
            </div>
          )}

          <form onSubmit={handleSubmit} className="p-4">
            <div className="mb-3">
              <label htmlFor="tituloFilme" className="form-label fw-bold">
                Titulo do Filme
              </label>
              <input
                type="text"
                id="tituloFilme"
                className="form-control"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="generoFilme" className="form-label fw-bold">
                Gênero do Filme
              </label>
              <input
                type="text"
                id="generoFilme"
                className="form-control"
                value={genero}
                onChange={(e) => setGenero(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="sinopseFilme" className="form-label fw-bold">
                Sinopse do Filme
              </label>
              <input
                type="text"
                id="sinopseFilme"
                className="form-control"
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
              }}
              disabled={!isFormValid}
            >
              Salvar
            </button>
          </form>
        </div>
      </dialog>
    </>
  );
}

export default EditarFilme;