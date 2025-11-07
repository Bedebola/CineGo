import { useState, useRef } from "react";

import {
  buscarFilmeId,
  alugarFilme,
  devolverFilme,
  desativarFilme,
  ativarFilme,
  type Filme,
  type FilmeViewProps
} from "../../../api/filmesService";


function FilmeViewDialog({filmeId, onChange }: FilmeViewProps) {
  const [filme, setFilme] = useState<Filme | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  const abrirDialog = async () => {
    const dados = await buscarFilmeId(Number(filmeId));
    console.log(filmeId)
    setFilme(dados);
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const handleAlugar = async () => {
    await alugarFilme(Number(filme?.filmeId));
    const atualizado = await buscarFilmeId(Number(filmeId));
    setFilme(atualizado);
    onChange?.();
  };

  const handleDevolver = async () => {
    await devolverFilme(Number(filme?.filmeId));
    const atualizado = await buscarFilmeId(Number(filmeId));
    setFilme(atualizado);
    onChange?.();
  };

  const handleDesativar = async () => {
    await desativarFilme(Number(filme?.filmeId));
    const atualizado = await buscarFilmeId(Number(filmeId));
    setFilme(atualizado);
    onChange?.();
  };

  const handleAtivar = async () => {
    await ativarFilme(Number(filme?.filmeId));
    const atualizado = await buscarFilmeId(Number(filmeId));
    setFilme(atualizado);
    onChange?.();
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-eye-fill"></i>
      </button>

      <style>{`#dlg-${Number(filmeId)}::backdrop{background:rgba(0,0,0,.55)} #dlg-${filmeId} .form-label { color: #212529 !important; }`}</style>

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
            <h5 className="m-0">{filme?.titulo || "Detalhes do Filme"}</h5>

            <div className="d-flex align-items-center gap-2">
              <span
                className={`badge ${
                  filme?.status === "DISPONIVEL"
                    ? "bg-success"
                    : filme?.status === "ALUGADO"
                    ? "bg-danger"
                    : "bg-secondary"
                }`}
              >
                {filme?.status}
              </span>
 
              <button
                onClick={fecharDialog}
                className="btn-close btn-close-white"
                aria-label="Fechar"
              ></button>
            </div>
          </div>

          <div className="p-4">
            <p className="form-label fw-bold text-dark">
              <strong>Sinopse do Filme</strong>
            </p>
            <p className="text-muted">{filme?.sinopse}</p>
          </div>

          <div className="p-4">
            <p className="form-label fw-bold text-dark">
              <strong>Gênero do Filme</strong>
            </p>
            <p className="text-muted">{filme?.genero}</p>
          </div>

          <div className="d-flex gap-2 justify-content-end px-4 pb-4">
            {filme?.status === "DISPONIVEL" && (
              <>
                <button onClick={handleAlugar} className="btn btn-success">
                  Alugar
                </button>
                <button onClick={handleDesativar} className="btn btn-warning">
                  Desativar
                </button>
              </>
            )}

            {filme?.status === "ALUGADO" && (
              <button onClick={handleDevolver} className="btn btn-info">
                Devolver
              </button>
            )}

            {filme?.status === "DESATIVADO" && (
              <button onClick={handleAtivar} className="btn btn-secondary">
                Ativar
              </button>
            )}
          </div>
        </div>
      </dialog>
    </>
  );
}

export default FilmeViewDialog;
