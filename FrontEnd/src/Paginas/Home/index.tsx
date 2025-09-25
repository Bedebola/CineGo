import { useEffect, useState } from "react";
import { listarFilmes } from "../../api/filmes-api";
import FilmeView from "../../Componentes/Dialogs/Filmes/FilmeViewDialog";

interface Filme {
  filmeId: number;
  titulo: string;
  sinopse: string;
  status: string;
}

export default function Home() {
  const [filmes, setFilmes] = useState<Filme[]>([]);

  useEffect(() => {
    async function carregar() {
      const data = await listarFilmes();
      setFilmes(data);
    }
    carregar();
  }, []);

  const filmesRecentes = [...filmes]
    .sort((a, b) => b.filmeId - a.filmeId)
    .slice(0, 4);

  const stats = filmes.reduce(
    (acc, f) => {
      const s = f.status.toLowerCase();
      if (s === "disponivel") acc.disponiveis++;
      if (s === "alugado") acc.alugados++;
      if (s === "desativado") acc.inativos++;
      return acc;
    },
    { disponiveis: 0, alugados: 0, inativos: 0 }
  );

  async function carregar() {
    const data = await listarFilmes();
    setFilmes(data);
  }

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

      <h4 className="mb-3">Novidades</h4>
      <div className="row">
        {filmesRecentes.map((f) => (
          <div key={f.filmeId} className="col-md-3">
            <div className="card h-100 d-flex flex-column justify-content-between">
              <div className="card-body">
                <h5 className="card-title">{f.titulo}</h5>
                <p className="card-text text-truncate">{f.sinopse}</p>
                <span
                  className={`badge ${
                    f.status === "DISPONIVEL"
                      ? "bg-success"
                      : f.status === "ALUGADO"
                      ? "bg-danger"
                      : "bg-secondary"
                  }`}
                >
                  {f.status}
                </span>
              </div>
              <div className="card-footer bg-transparent border-0 text-end">
                <FilmeView filmeId={f.filmeId} onChange={() => carregar()} />
              </div>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
