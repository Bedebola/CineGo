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

