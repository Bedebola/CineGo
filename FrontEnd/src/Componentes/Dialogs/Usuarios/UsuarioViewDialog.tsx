import { useState, useRef } from "react";
import { buscarUsuarioId } from "../../../api/usuariosService";

interface Usuario {
  nome: string;
  cpf: string;
  email: string;
}

interface UsuarioViewProps {
  usuarioId: number;
}

function UsuarioView({ usuarioId }: UsuarioViewProps) {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  const abrirDialog = async () => {
    const dados = await buscarUsuarioId(Number(usuarioId));
    setUsuario(dados);
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-eye-fill"></i>
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
            <h5 className="m-0">{usuario?.nome || "Detalhes do Usuário"}</h5>
            <button
              onClick={fecharDialog}
              className="btn-close btn-close-white"
              aria-label="Fechar"
            ></button>
          </div>

          <div className="p-4">
            <p className="text-secondary mb-2"><strong>Email:</strong> {usuario?.email}</p>
            <p className="text-secondary mb-2"><strong>CPF:</strong> {usuario?.cpf}</p>
          </div>

          <div className="d-flex gap-2 justify-content-end px-4 pb-4">
            <button onClick={fecharDialog} className="btn btn-outline-secondary">
              Fechar
            </button>
          </div>
        </div>
      </dialog>
    </>
  );
}


export default UsuarioView;