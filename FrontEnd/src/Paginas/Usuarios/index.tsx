import { useEffect, useState } from "react";
import { listarUsuarios } from "../../api/usuarios-api";
import UsuarioView from "../../Componentes/Dialogs/Usuarios/UsuarioViewDialog";
import UsuarioEdicaoDialog from "../../Componentes/Dialogs/Usuarios/UsuarioEdicaoDialog";
import UsuarioExclusaoDialog from "../../Componentes/Dialogs/Usuarios/UsuarioExclusaoDialog";

interface Usuario {
  usuarioId: number;
  nome: string;
  cpf: string;
  role: string;
  email: string;
}

function Usuarios() {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    const data = await listarUsuarios();
    setUsuarios(data);
  }

  return (
    <main className="min-vh-100 d-flex flex-column align-items-center py-4">
      <h2 className="h5 mb-4">Usuários</h2>

      <div className="table-responsive w-100" style={{ maxWidth: 1000 }}>
        <table className="table table-hover table-striped align-middle shadow rounded overflow-hidden">
          <thead className="table-dark">
            <tr>
              <th className="text-center" style={{ width: 180 }}>
                Nome
              </th>
              <th>E-mail</th>
              <th>CPF</th>
              <th>Role</th>
              <th className="text-center" style={{ width: 140 }}>
                Ações
              </th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map((usuario) => (
              <tr key={usuario.usuarioId}>
                <td>
                  <span className="fw-semibold">{usuario.nome}</span>
                </td>
                <td>
                  <span className="text-muted">{usuario.email}</span>
                </td>
                <td>
                  <span className="text-muted">{usuario.cpf}</span>
                </td>
                <td>
                  <span className="text-muted">{usuario.role}</span>
                </td>
                <td className="text-center">
                  <div className="d-inline-flex gap-2">
                    <UsuarioView usuarioId={usuario.usuarioId} />
                    <UsuarioEdicaoDialog
                      usuarioId={usuario.usuarioId}
                      onChange={carregar}
                    />
                    <UsuarioExclusaoDialog
                      usuarioId={usuario.usuarioId}
                      onChange={carregar}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
}

export default Usuarios;
