import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import { type Filme } from "../api/filmesService";

interface FilmesState {
    filmes: Filme[];
    stats: {
        disponiveis: number;
        alugados: number;
        inativos: number;
    };
    version: number
}

const initialState: FilmesState = {
    filmes: [],
    stats: {
        disponiveis: 0,
        alugados: 0,
        inativos: 0,
    },
    version: 0
};

const filmesSlice = createSlice({
    name: "filmes",
    initialState,
    reducers: {
        setFilmes: (state, action: PayloadAction<Filme[]>) => {
            state.filmes = action.payload;
            state.stats = state.filmes.reduce(
                (acc: { disponiveis: number; alugados: number; inativos: number; }, filme: { status: string; }) => {
                    const status = filme.status.toLowerCase();
                    if (status === "disponivel") acc.disponiveis++;
                    if (status === "alugado") acc.alugados++;
                    if (status === "desativado") acc.inativos++;
                    return acc;
                },
                { disponiveis: 0, alugados: 0, inativos: 0 }
            );
        },
        
        updateFilme(state, action) {
            const filme = action.payload;
            const index = state.filmes.findIndex(f => f.filmeId === filme.filmeId);
            if (index !== -1) {
                state.filmes[index] = filme;
            }
            state.version++;
        },
    },
});

export const { setFilmes, updateFilme } = filmesSlice.actions;

export default filmesSlice.reducer;
