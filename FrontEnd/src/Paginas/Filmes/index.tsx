import { useEffect, useState } from "react";
import { listarFilmes } from "../../api/filmes-api";
import FilmeView from "../../Componentes/Dialogs/Filmes/FilmeViewDialog";

interface Filme {
  filmeId: number;
  titulo: string;
  sinopse: string;
  status: string;
}

function normalizarStatus(s: string) {
  return s
    .normalize("NFD")
    .replace(/\p{Diacritic}/gu, "")
    .toLowerCase()
    .trim();
}

function classeStatus(status: string) {
  const s = normalizarStatus(status);
  if (s === "disponivel") return "bg-success";
  if (s === "alugado") return "bg-danger";
  if (s === "desativado") return "bg-secondary";
  return "bg-light";
}

function Filmes() {
  const [filmes, setFilmes] = useState<Filme[]>([]);

  useEffect(() => {
    async function carregarFilmes() {
      const data: Filme[] = await listarFilmes();
      setFilmes(data);
    }
    carregarFilmes();
  }, []);

  async function carregar() {
    const data = await listarFilmes();
    setFilmes(data);
  }

  return (
    <main
      className="container-fluid min-vh-100 py-4"
      style={{ paddingRight: 260 }}
    >
      <h2 className="h5 mb-3">Filmes</h2>

      <div className="row g-3">
        {filmes.map((filme) => (
          <div key={filme.filmeId} className="col-md-4 col-lg-3">
            <div className="card h-100 shadow-sm position-relative">
              <div className="position-absolute top-0 end-0 m-2">
                <FilmeView filmeId={filme.filmeId} onChange={() => carregar()} />
              </div>

              <div className="card-body">
                <span className={`badge ${classeStatus(filme.status)} mb-2`}>
                  {filme.status}
                </span>

                <h5 className="card-title">{filme.titulo}</h5>
                <p className="card-text text-muted text-truncate">
                  {filme.sinopse}
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Filmes;
