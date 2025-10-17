import { useState, useRef } from "react";
import { excluirUsuario, buscarUsuarioId } from "../../../api/usuariosService";

interface Usuario {
  nome: string;
}

interface UsuarioExclusaoProps {
  usuarioId: number;
  onChange?: () => void;
}

function ExcluirUsuario({ usuarioId, onChange }: UsuarioExclusaoProps) {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  const abrirDialog = async () => {
    const dados = await buscarUsuarioId(Number(usuarioId));
    setUsuario(dados);
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const confirmarExclusao = async () => {
    try {
      await excluirUsuario(usuarioId);
      fecharDialog();
      if (onChange) onChange();
    } catch (error) {
      console.error("Erro ao excluir usuário:", error);
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-trash-fill"></i>
      </button>

      <style>{`#dlg-${usuarioId}::backdrop{background:rgba(0,0,0,.55)}`}</style>

      <dialog
        id={`dlg-${usuarioId}`}
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
            <h5 className="m-0">{usuario?.nome || "Usuário"}</h5>
            <button
              onClick={fecharDialog}
              className="btn-close btn-close-white"
            ></button>
          </div>

          <div className="p-4">
            <p className="text-secondary mb-2">
              Deseja realmente excluir o usuário{" "}
              <strong>{usuario?.nome}</strong>?
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

export default ExcluirUsuario;
