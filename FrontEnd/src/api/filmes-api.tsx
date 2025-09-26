import { api } from "../api/http";

export async function listarFilmes(){
    const response = await api(`/filme/listarFilmes`);
    const data = await response.json();
    return data;
}

export async function buscarFilmeId(filmeId: number){
    const response = await api(`/filme/buscarFilmeId/${filmeId}`);
    const data = await response.json();
    return data;
}

export async function alugarFilme(filmeId: number){
    const response = await api(`/filme/alugarFilme/${filmeId}`, {
        method: 'PUT'
    });
    const data = await response.json();
    return data;
}

export async function devolverFilme(filmeId: number){
    const response = await api(`/filme/devolverFilme/${filmeId}`, {
        method: 'PUT'
    });
    const data = await response.json();
    return data;
}

export async function desativarFilme(filmeId: number){
    const response = await api(`/filme/desativarFilme/${filmeId}`, {
        method: 'PUT'
    });
    const data = await response.json();
    return data;
}

export async function ativarFilme(filmeId: number){
    const response = await api(`/filme/ativarFilme/${filmeId}`, {
        method: 'PUT'
    });
    const data = await response.json();
    return data;
}

export async function cadastrarFilme(filme: { titulo: string; sinopse: string }) {
  const response = await api("/filme/cadastrarFilme", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(filme),
  });

  if (!response.ok) {
    throw new Error("Erro ao cadastrar filme");
  }

  return response.json();
}

export async function editarFilme(filmeId: number, filme: { titulo: string; sinopse: string }){
    const response = await api(`/filme/editarFilme/${filmeId}`, {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(filme),
    });
    const data = await response.json();
    return data;
}

export async function excluirFilme(filmeId: number){
    await api(`/filme/excluirFilme/${filmeId}`, {
        method: 'DELETE'
    });
    return;
}

