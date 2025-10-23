import { AxiosError } from "axios";
import api from "./api";

export type Filme = {
  filmeId?: number;
  titulo: string;
  genero: string;
  status: string;
  sinopse: string;
};

export type FilmeViewProps = {
  filmeId?: number;
  onChange?: () => void;
};

export async function listarFilmes(): Promise<Filme[]> {
  const response = await api.get<Filme[]>("/filme/listarFilmes");
  return response.data;
}

export async function listarFilmesPorStatus(status: string): Promise<Filme[]> {
  const response = await api.get<Filme[]>(
    `/filme/listarFilmesPorStatus/${status}`
  );
  return response.data;
}

export async function buscarFilmeId(filmeId: number): Promise<Filme> {
  const response = await api.get<Filme>(`/filme/buscarFilmeId/${filmeId}`);
  return response.data;
}

export async function alugarFilme(filmeId: number): Promise<Filme> {
  const response = await api.put<Filme>(`/filme/alugarFilme/${filmeId}`);
  return response.data;
}

export async function devolverFilme(filmeId: number): Promise<Filme> {
  const response = await api.put<Filme>(`/filme/devolverFilme/${filmeId}`);
  return response.data;
}

export async function desativarFilme(filmeId: number): Promise<Filme> {
  const response = await api.put<Filme>(`/filme/desativarFilme/${filmeId}`);
  return response.data;
}

export async function ativarFilme(filmeId: number): Promise<Filme> {
  const response = await api.put<Filme>(`/filme/ativarFilme/${filmeId}`);
  return response.data;
}

export async function cadastrarFilme(filme: Filme): Promise<Filme> {
  try {
    const response = await api.post("/filme/cadastrarFilme", filme);
    return response.data;
  } catch (err: unknown) {
    if (err instanceof AxiosError) {
      throw new Error(err.response?.data?.message || "Erro ao cadastrar filme");
    }
    throw new Error("Erro ao cadastrar filme");
  }
}
export async function editarFilme(filme: Filme): Promise<Filme> {
  const response = await api.put(`/filme/editarFilme/${filme.filmeId}`, filme);
  return response.data;
}

export async function excluirFilme(filmeId: number) {
  await api.delete(`/filme/excluirFilme/${filmeId}`);
}
