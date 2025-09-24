import { api } from "./http";

export async function listarUsuarios(){
    const response = await api(`/usuario/listarUsuarios`);
    const data = await response.json();
    return data;
}

export async function buscarUsuarioId(usuarioId: number){
    const response = await api(`/usuario/usuario/${usuarioId}`);
    const data = await response.json();
    return data;
}