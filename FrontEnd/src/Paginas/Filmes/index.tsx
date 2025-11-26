import { useEffect, useState } from "react";
import {
  listarFilmes,
  listarFilmesPorStatus,
  type Filme,
} from "../../api/filmesService";
import FilmeView from "../../Componentes/Dialogs/Filmes/FilmeViewDialog";
import FilmeEdicaoDialog from "../../Componentes/Dialogs/Filmes/FilmeEdicaoDialog";
import FilmeExclusaoDialog from "../../Componentes/Dialogs/Filmes/FilmeExclusaoDialog";
import CadastrarFilme from "../../Componentes/Dialogs/Filmes/FilmeCadastro";
import { getRoleFromToken } from "../../api/api";

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
  const [statusSelecionado, setStatusSelecionado] = useState<string>("");
  const [role, setRole] = useState<string | null>(null);

  async function carregar() {
    if (statusSelecionado && statusSelecionado !== "TODOS") {
      await filtrarFilmesPorStatus();
      return;
    }
    const data = await listarFilmes();
    setFilmes(data);
  }

  useEffect(() => {
    carregar();

    async function buscarRole() {
      const r = await getRoleFromToken();
      setRole(r);
    }
    buscarRole();
  }, []);

  async function filtrarFilmesPorStatus() {
    if (statusSelecionado === "" || statusSelecionado === "TODOS") {
      await carregar();
      return;
    }

    const data = await listarFilmesPorStatus(statusSelecionado);
    setFilmes(data);
  }

  const isAdmin = role === "ADMIN";

  return (
    <main
      className="container-fluid min-vh-100 py-4"
      style={{ paddingRight: 260 }}
    >
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="h5 m-0">Filmes</h2>
        {isAdmin && <CadastrarFilme onChange={() => carregar()} />}
      </div>

      <div className="d-flex align-items-center gap-2 mb-3">
        <select
          id="statusFilme"
          className="form-select form-select-sm w-auto"
          value={statusSelecionado}
          onChange={(e) => setStatusSelecionado(e.target.value)}
        >
          <option value="">Todos</option>
          <option value="DISPONIVEL">Disponíveis</option>
          <option value="ALUGADO">Alugados</option>
          <option value="DESATIVADO">Desativados</option>
        </select>
        <button
          onClick={filtrarFilmesPorStatus}
          className="btn btn-primary btn-sm"
        >
          <i className="bi bi-funnel"> Filtrar</i>
        </button>
      </div>

      <div className="row g-4">
        {filmes.map((filme) => (
          <div key={Number(filme.filmeId)} className="col-md-6 col-lg-4">
            <div
              className="card h-100 shadow-sm position-relative border-0 rounded-3"
              style={{ minHeight: "200px" }}
            >
              <div
                className="position-absolute top-0 end-0 m-2 d-flex gap-2"
                style={{ right: "0.75rem", top: "0.75rem" }}
              >
                <FilmeView filmeId={Number(filme.filmeId)} />
                {isAdmin && (
                  <>
                    {/* Estes já estavam corretos */}
                    <FilmeEdicaoDialog
                      filmeId={Number(filme.filmeId)}
                      onChange={() => carregar()}
                    />
                    <FilmeExclusaoDialog
                      filmeId={Number(filme.filmeId)}
                      onChange={() => carregar()}
                    />
                  </>
                )}
              </div>

              <div className="card-body p-4 pt-5">
                <span className={`badge ${classeStatus(filme.status)} mb-3 fw-bold`}>
                  {filme.status}
                </span>
                <h4 className="card-title mb-2 text-dark">
                  {filme.titulo}
                </h4>
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