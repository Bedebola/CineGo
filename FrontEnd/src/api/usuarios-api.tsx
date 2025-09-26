import { http } from "./http";

export async function listarUsuarios() {
  const { data } = await http.get("/usuario/listarUsuarios");
  return data;
}

export async function buscarUsuarioId(usuarioId: number) {
  const { data } = await http.get(`/usuario/usuario/${usuarioId}`);
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
  const { data } = await http.post("/usuario/cadastrarUsuario", usuario);
  return data;
}

export async function editarUsuario(usuarioId: number, usuario: UsuarioPayload) {
  const { data } = await http.put(`/usuario/editarUsuario/${usuarioId}`, usuario);
  return data;
}

export async function excluirUsuario(usuarioId: number) {
  await http.delete(`/usuario/excluirUsuario/${usuarioId}`);
}
