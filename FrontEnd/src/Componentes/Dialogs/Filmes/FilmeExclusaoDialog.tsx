import { useState, useRef } from "react";
import { excluirFilme, buscarFilmeId, type Filme, type FilmeViewProps } from "../../../api/filmesService";


function ExcluirFilme ({ onChange }: FilmeViewProps) {
  const [filme, setFilme] = useState<Filme | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  const abrirDialog = async () => {
    const dados = await buscarFilmeId(Number(filme?.filmeId));
    setFilme(dados);
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const confirmarExclusao = async () => {
    try {
      await excluirFilme(Number(filme?.filmeId));
      fecharDialog();
      if (onChange) onChange();
    } catch (error) {
      console.error("Erro ao excluir filme:", error);
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-trash-fill"></i>
      </button>

      <style>{`#dlg-${filme?.filmeId}::backdrop{background:rgba(0,0,0,.55)}`}</style>

      <dialog
        id={`dlg-${filme?.filmeId}`}
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
            <h5 className="m-0">{filme?.titulo || "Filme"}</h5>
            <button
              onClick={fecharDialog}
              className="btn-close btn-close-white"
            ></button>
          </div>

          <div className="p-4">
            <p className="text-secondary mb-2">
              Deseja realmente excluir o filme{" "}
              <strong>{filme?.titulo}</strong>?
            </p>
          </div>

          <div className="d-flex gap-2 justify-content-end px-4 pb-4">
            <button
              onClick={fecharDialog}
              className="btn btn-outline-secondary"
            >
              Cancelar
            </button>

            <button
              onClick={confirmarExclusao}
              className="btn"
              style={{
                backgroundColor: "#343a40",
                color: "white",
                fontWeight: "600",
                borderRadius: "10px",
                padding: "10px 20px",
              }}
            >
              Confirmar
            </button>
          </div>
        </div>
      </dialog>
    </>
  );
}

export default ExcluirFilme;
