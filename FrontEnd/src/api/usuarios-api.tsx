import { api } from "./http";

export async function listarUsuarios() {
  const response = await api(`/usuario/listarUsuarios`);
  const data = await response.json();
  return data;
}

export async function buscarUsuarioId(usuarioId: number) {
  const response = await api(`/usuario/usuario/${usuarioId}`);
  const data = await response.json();
  return data;
}

export async function cadastrarUsuario(usuario: {
  nome: string;
  email: string;
  cpf: string;
  senha: string;
  role: string;
}) {
  const response = await api(`/usuario/cadastrarUsuario`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(usuario),
  });
  const data = await response.json();
  return data;
}

export async function editarUsuario(usuario:{
  usuarioId: number
  nome: string;
  email: string;
  cpf: string;
  senha: string;
  role: string;
}) {
  const response = await api(`/usuario/editarUsuario/${usuario.usuarioId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(usuario),
  });
  const data = await response.json();
  return data;
}

export async function excluirUsuario(usuarioId: number) {
  const response = await api(`/usuario/excluirUsuario/${usuarioId}`, {
    method: "DELETE",
  });
  const data = await response.json();
  return data;
}
