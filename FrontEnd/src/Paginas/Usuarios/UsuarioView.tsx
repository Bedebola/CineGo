import { useState, useEffect } from "react";
import { buscarUsuarioId, type Usuario, type UsuarioViewProps } from "../../api/usuariosService";

function UsuarioView({ usuarioId }: UsuarioViewProps) {
  const [usuario, setUsuario] = useState<Usuario | null>(null);

  useEffect(() => {
    const fetchUsuario = async () => {
      const dados = await buscarUsuarioId(Number(usuarioId));
      setUsuario(dados);
    };
    fetchUsuario();
  }, [usuarioId]);

  if (!usuario) return <p>Carregando usuário...</p>;

  return (
    <form className="bg-white p-4 rounded-4 shadow-sm" style={{ maxWidth: "500px" }}>
      <h5 className="mb-4">Detalhes do Usuário</h5>

      <div className="mb-3">
        <label className="form-label"><strong>Nome</strong></label>
        <input type="text" className="form-control" value={usuario.nome} readOnly />
      </div>

      <div className="mb-3">
        <label className="form-label"><strong>Email</strong></label>
        <input type="email" className="form-control" value={usuario.email} readOnly />
      </div>

      <div className="mb-3">
        <label className="form-label"><strong>CPF</strong></label>
        <input type="text" className="form-control" value={usuario.cpf} readOnly />
      </div>
    </form>
  );
}

export default UsuarioView;
