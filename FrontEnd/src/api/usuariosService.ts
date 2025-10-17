import api from "./api";

export async function listarUsuarios() {
  const { data } = await api.get("/usuario/listarUsuarios");
  return data;
}

export async function buscarUsuarioId(usuarioId: number) {
  const { data } = await api.get(`/usuario/usuario/${usuarioId}`);
  return data;
}

type UsuarioPayload = {
  nome: string;
  email: string;
  cpf: string;
  senha?: string;
  role: string;
};

export async function cadastrarUsuario(usuario: UsuarioPayload) {
  const { data } = await api.post("/usuario/cadastrarUsuario", usuario);
  return data;
}

export async function editarUsuario(usuarioId: number, usuario: UsuarioPayload) {
  const { data } = await api.put(`/usuario/editarUsuario/${usuarioId}`, usuario);
  return data;
}

export async function excluirUsuario(usuarioId: number) {
  await api.delete(`/usuario/excluirUsuario/${usuarioId}`);
}
