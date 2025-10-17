import React, { useState, useEffect, useRef } from "react";
import { editarUsuario, buscarUsuarioId } from "../../../api/usuariosService";

interface Usuario {
  nome: string;
  email: string;
  cpf: string;
  role: "ADMIN";
  senha?: string
}

interface EditarUsuarioProps {
  usuarioId: number;
  onChange?: () => void;
}

function EditarUsuario({ usuarioId, onChange }: EditarUsuarioProps) {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);
  const [message, setMessage] = useState("");

  const dialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (nome.trim() && email.trim() && cpf.trim()) {
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [nome, email, cpf]);

  const abrirDialog = async () => {
    try {
      const dados = await buscarUsuarioId(usuarioId);
      setUsuario(dados);
      setNome(dados.nome);
      setEmail(dados.email);
      setCpf(dados.cpf);
      setSenha("");
      dialogRef.current?.showModal();
    } catch (error) {
      console.error("Erro ao buscar usuário:", error);
    }
  };

  const fecharDialog = () => dialogRef.current?.close();

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  setMessage("");

  try {
    const usuarioEditado: Usuario = {
      nome,
      email,
      cpf,
      role: "ADMIN",
    };

    if (senha.trim()) {
      usuarioEditado.senha = senha;
    }

    await editarUsuario(usuarioId, usuarioEditado);

    setMessage("As alterações foram salvas com sucesso!");
    onChange?.();
  } catch (error) {
    console.error(error);
    setMessage("Erro ao editar os dados do usuário. Tente novamente.");
  }
};
  return (
    <>
      <button onClick={abrirDialog} className="btn  btn-sm">
        <i className="bi bi-pencil-square"></i>
      </button>

      <style>{`
        #dlg-${usuarioId}::backdrop {
          background: rgba(0, 0, 0, 0.55);
        }
        .form-label {
          color: black !important;
        }
      `}</style>

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
            <h5 className="m-0">{usuario?.nome || "Editar Usuário"}</h5>
            <button
              onClick={fecharDialog}
              className="btn-close btn-close-white"
              aria-label="Fechar"
            ></button>
          </div>

          {message && (
            <div
              className={`alert ${
                message.includes("sucesso") ? "alert-success" : "alert-danger"
              } mb-2`}
              role="alert"
            >
              {message}
            </div>
          )}

          <form onSubmit={handleSubmit} className="p-4">
            <div className="mb-3">
              <label htmlFor="nomeUsuario" className="form-label fw-bold">
                Nome do Usuário
              </label>
              <input
                type="text"
                id="nomeUsuario"
                className="form-control"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="emailUsuario" className="form-label fw-bold">
                E-mail do Usuário
              </label>
              <input
                type="email"
                id="emailUsuario"
                className="form-control"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="cpfUsuario" className="form-label fw-bold">
                CPF do Usuário
              </label>
              <input
                type="text"
                id="cpfUsuario"
                className="form-control"
                value={cpf}
                onChange={(e) => setCpf(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label htmlFor="senhaUsuario" className="form-label fw-bold">
                Senha do Usuário
              </label>
              <input
                type="password"
                id="senhaUsuario"
                className="form-control"
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

export default EditarUsuario;
