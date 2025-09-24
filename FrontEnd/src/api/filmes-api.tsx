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


