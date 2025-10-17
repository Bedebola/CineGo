import { AxiosError } from "axios";
import api from "./api";

export async function listarFilmes() {
  const { data } = await api.get("/filme/listarFilmes");
  return data;
}

export async function listarFilmesPorStatus(status: string) {
  const { data } = await api.get(`/filme/listarFilmesPorStatus/${status}`);
  return data;
}

export async function buscarFilmeId(filmeId: number) {
  const { data } = await api.get(`/filme/buscarFilmeId/${filmeId}`);
  return data;
}

export async function alugarFilme(filmeId: number) {
  const { data } = await api.put(`/filme/alugarFilme/${filmeId}`);
  return data;
}

export async function devolverFilme(filmeId: number) {
  const { data } = await api.put(`/filme/devolverFilme/${filmeId}`);
  return data;
}

export async function desativarFilme(filmeId: number) {
  const { data } = await api.put(`/filme/desativarFilme/${filmeId}`);
  return data;
}

export async function ativarFilme(filmeId: number) {
  const { data } = await api.put(`/filme/ativarFilme/${filmeId}`);
  return data;
}

type FilmeRequest = { titulo: string; genero: string, sinopse: string };
export async function cadastrarFilme(filme: FilmeRequest) {
  try {
    const { data } = await api.post("/filme/cadastrarFilme", filme);
    return data;
  } catch (err: unknown) {
    if (err instanceof AxiosError) {
      throw new Error(err.response?.data?.message || "Erro ao cadastrar filme");
    }
    throw new Error("Erro ao cadastrar filme");
  }
}
export async function editarFilme(
  filmeId: number,
  filme: { titulo: string; genero: string, sinopse: string }
) {
  const { data } = await api.put(`/filme/editarFilme/${filmeId}`, filme);
  return data;
}

export async function excluirFilme(filmeId: number) {
  await api.delete(`/filme/excluirFilme/${filmeId}`);
}
