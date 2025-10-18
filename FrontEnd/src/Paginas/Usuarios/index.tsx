import React, { useState, useEffect } from "react";
import { listarUsuarios } from "../../api/usuariosService";
import UsuarioCadastro from "../../Componentes/Dialogs/Usuarios/UsuarioCadastro";

interface Usuario {
  usuarioId: number;
  nome: string;
  email: string;
  cpf: string;
  role: string;
}

function UsuariosListView() {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [usuarioSelecionado, setUsuarioSelecionado] = useState<Usuario | null>(
    null
  );

  useEffect(() => {
    async function carregarUsuarios() {
      const data: Usuario[] = await listarUsuarios();
      setUsuarios(data);
    }
    carregarUsuarios();
  }, []);

  return (
    <div className="d-flex vh-100">
      <div className="col-3 border-end overflow-auto p-3">
        <h3>Usuários</h3>
        <UsuarioCadastro />

        <ul className="list-unstyled mt-3">
          {usuarios.map((u) => (
            <li
              key={u.usuarioId}
              onClick={() => setUsuarioSelecionado(u)}
              className={`p-2 rounded cursor-pointer ${
                usuarioSelecionado?.usuarioId === u.usuarioId
                  ? "bg-light fw-semibold"
                  : "bg-transparent"
              }`}
              style={{ cursor: "pointer" }}
            >
              {u.nome}
            </li>
          ))}
        </ul>
      </div>

      <div className="flex-grow-1 p-3">
        {usuarioSelecionado ? (
          <>
            <h2>{usuarioSelecionado.nome}</h2>

            <div className="mb-3">
              <label htmlFor="cpfUsuario" className="form-label fw-bold">
                CPF:
              </label>
              <input
                type="text"
                id="cpfUsuario"
                className="form-control"
                value={usuarioSelecionado.cpf}
                readOnly
              />
            </div>

            <div className="mb-3">
              <label htmlFor="emailUsuario" className="form-label fw-bold">
                E-mail:
              </label>
              <input
                type="email"
                id="emailUsuario"
                className="form-control"
                value={usuarioSelecionado.email}
                readOnly
              />
            </div>

            <div className="mb-3">
              <label htmlFor="roleUsuario" className="form-label fw-bold">
                Acesso:
              </label>
              <input
                type="text"
                id="roleUsuario"
                className="form-control"
                value={usuarioSelecionado.role}
                readOnly
              />
            </div>
          </>
        ) : (
          <p className="text-muted">Selecione um usuário no lado esquerdo</p>
        )}
      </div>
    </div>
  );
}

export default UsuariosListView;
