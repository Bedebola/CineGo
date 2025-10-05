import React, { useState, useEffect, useRef } from "react";
import { cadastrarUsuario } from "../../../api/usuarios-api";

function CadastrarUsuario() {
  const dialogRef = useRef<HTMLDialogElement>(null);
  const [nome, setNome] = useState("");
  const [cpf, setCpf] = useState("");
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("");
  const [senha, setSenha] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    setIsFormValid(
      nome.trim() !== "" &&
        cpf.trim() !== "" &&
        email.trim() !== "" &&
        role !== "" &&
        senha.trim() !== ""
    );
  }, [nome, cpf, email, role, senha]);

  const abrirDialog = () => {
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await cadastrarUsuario({ nome, cpf, email, role, senha });
      fecharDialog();
      setNome("");
      setCpf("");
      setEmail("");
      setRole("");
      setSenha("");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-plus-square"> Cadastrar Usuario</i>
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
          <h4 className="mb-3 text-center">Cadastrar Usuario</h4>

          <div className="mb-3">
            <label htmlFor="nomeUsuario" className="form-label">
              Nome:
            </label>
            <input
              type="text"
              id="nomeUsuario"
              className="form-control"
              placeholder="Digite o nome do usuario"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="cpfUsuario" className="form-label">
              CPF:
            </label>
            <input
              type="text"
              id="cpfUsuario"
              className="form-control"
              placeholder="Digite o CPF do usuario"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="emailUsuario" className="form-label">
              E-mail:
            </label>
            <input
              type="email"
              id="emailUsuario"
              className="form-control"
              placeholder="Digite o e-mail do usuario"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="roleUsuario" className="form-label">
              Tipo de Acesso:
            </label>
            <select
              id="roleUsuario"
              className="form-control"
              value={role}
              onChange={(e) => setRole(e.target.value)}
            >
              <option value="">Selecione uma opção</option>
              <option value="ADMIN">Administrador</option>
              <option value="USER">Colaborador</option>
            </select>
          </div>

          <div className="mb-3">
            <label htmlFor="senhaUsuario" className="form-label">
              Senha:
            </label>
            <input
              type="password"
              id="senhaUsuario"
              className="form-control"
              placeholder="Digite a senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
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

export default CadastrarUsuario;
