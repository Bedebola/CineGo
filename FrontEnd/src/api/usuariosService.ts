import api from "./api";

export type Usuario = {
  usuarioId?: number;
  nome: string;
  email: string;
  cpf: string;
  senha?: string;
  role: string;
};

export type UsuarioViewProps = {
  usuarioId: number;
  onChange?: () => void;
};

export interface EditarUsuarioProps {
  usuarioId: number;
  onChange?: () => void;
}

export async function listarUsuarios() :Promise<Usuario[]> {
  const  response  = await api.get<Usuario[]>("/usuario/listarUsuarios");
  return response.data;
}

export async function buscarUsuarioId(usuarioId: number) :Promise<Usuario> {
  const response = await api.get<Usuario>(`/usuario/usuario/${usuarioId}`);
  return response.data;
}

export async function cadastrarUsuario(usuario: Usuario) :Promise<Usuario> {
  const response = await api.post<Usuario>("/usuario/cadastrarUsuario", usuario);
  return response.data;
}

export async function editarUsuario(usuarioId: number, usuario: Usuario) :Promise<Usuario> {
  const response = await api.put<Usuario>(`/usuario/editarUsuario/${usuarioId}`, usuario);
  return response.data;
}

export async function excluirUsuario(usuarioId: number) {
  await api.delete(`/usuario/excluirUsuario/${usuarioId}`);
}
