import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setFilmes, updateFilme } from "../../Redux/filmesSlice";
import { listarFilmes } from "../../api/filmesService";
import FilmeView from "../../Componentes/Dialogs/Filmes/FilmeViewDialog";
import type { RootState } from "../../Redux/store";

export default function Home() {
  const dispatch = useDispatch();
  const filmes = useSelector((state: RootState) => state.filmes.filmes);
  const stats = useSelector((state: RootState) => state.filmes.stats);

  const version = useSelector((state) => state.filmes.version);

  useEffect(() => {
    async function carregar() {
      const data = await listarFilmes();
      dispatch(setFilmes(data));
    }
    carregar();
  }, [version]);

  return (
    <main className="container py-4">
      <h2 className="mb-4">Dashboard</h2>

      <div className="row mb-4">
        <div className="col-md-4">
          <div className="card text-center bg-success text-white">
            <div className="card-body">
              <h5 className="card-title">Disponíveis</h5>
              <p className="display-6">{stats.disponiveis}</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center bg-danger text-white">
            <div className="card-body">
              <h5 className="card-title">Alugados</h5>
              <p className="display-6">{stats.alugados}</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-center bg-secondary text-white">
            <div className="card-body">
              <h5 className="card-title">Desativados</h5>
              <p className="display-6">{stats.inativos}</p>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
