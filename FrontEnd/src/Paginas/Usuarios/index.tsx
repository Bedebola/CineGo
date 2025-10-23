import { useState, useEffect } from "react";
import {listarUsuarios,editarUsuario, type Usuario,} from "../../api/usuariosService";
import UsuarioCadastro from "../../Componentes/Dialogs/Usuarios/UsuarioCadastro";

function UsuariosListView() {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [usuarioSelecionado, setUsuarioSelecionado] = useState<Usuario | null>(null);
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [role, setRole] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    const carregarUsuarios = async () => {
      const data = await listarUsuarios();
      setUsuarios(data);
    };
    carregarUsuarios();
  }, []);

  useEffect(() => {
    if (usuarioSelecionado) {
      setNome(usuarioSelecionado.nome);
      setEmail(usuarioSelecionado.email);
      setCpf(usuarioSelecionado.cpf);
      setRole(usuarioSelecionado.role);
      setSenha("");
    }
  }, [usuarioSelecionado]);

  useEffect(() => {
    if (nome.trim() && email.trim() && cpf.trim()) {
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [nome, email, cpf]);

  async function handleSalvar() {
    if (!usuarioSelecionado) return;

    try {
      const usuarioEditado: Usuario = {
        ...usuarioSelecionado,
        nome,
        email,
        cpf,
        role,
      };

      if (senha.trim()) {
        usuarioEditado.senha = senha;
      }

      await editarUsuario(Number(usuarioEditado.usuarioId), usuarioEditado);

      const atualizados = await listarUsuarios();
      setUsuarios(atualizados);

      setUsuarioSelecionado(usuarioEditado);

      alert("Usuário atualizado com sucesso!");
    } catch (error) {
      console.error("Erro ao editar usuário:", error);
      alert("Erro ao salvar as alterações.");
    }
  }

  return (
    <div className="d-flex vh-100">
      <div className="col-6 border-end overflow-4 p-4">
        <h3>Usuários</h3>
        <UsuarioCadastro />

        <ul className="list-unstyled mt-2">
          {usuarios.map((usuario) => (
            <li
              key={usuario.usuarioId}
              onClick={() => setUsuarioSelecionado(usuario)}
              className={`p-2 rounded ${
                usuarioSelecionado?.usuarioId === usuario.usuarioId
                  ? "bg-light fw-semibold"
                  : "bg-transparent"
              }`}
              style={{ cursor: "pointer" }}
            >
              {usuario.nome}
            </li>
          ))}
        </ul>
      </div>

      <div className="flex-grow-1 p-4">
        {usuarioSelecionado ? (
          <>
            <h2>{usuarioSelecionado.nome}</h2>

            <div className="mb-3">
              <label htmlFor="nomeUsuario" className="form-label">
                Nome:
              </label>
              <input
                type="text"
                id="nomeUsuario"
                className="form-control"
                placeholder="Digite o nome do usuário"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="cpfUsuario" className="form-label fw-bold">
                CPF:
              </label>
              <input
                type="text"
                id="cpfUsuario"
                className="form-control"
                value={cpf}
                onChange={(e) => setCpf(e.target.value)}
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
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="roleUsuario" className="form-label fw-bold">
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
                placeholder="Digite uma nova senha (opcional)"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
              />
            </div>

            <button
              type="button"
              className="btn w-100"
              style={{
                backgroundColor: "#343a40",
                color: "white",
                fontWeight: "600",
                borderRadius: "10px",
                padding: "10px",
              }}
              disabled={!isFormValid}
              onClick={handleSalvar}
            >
              Salvar
            </button>
          </>
        ) : (
          <p className="text-muted">Selecione um usuário no lado esquerdo</p>
        )}
      </div>
    </div>
  );
}

export default UsuariosListView;
