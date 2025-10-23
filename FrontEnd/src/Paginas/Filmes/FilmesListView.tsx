import { useState, useEffect } from "react";
import { listarFilmes, type Filme } from "../../api/filmesService";
import FilmeCadastro from "../../Componentes/Dialogs/Filmes/FilmeCadastro";

function FilmesListView() {
  const [filmes, setFilmes] = useState<Filme[]>([]);
  const [filmeSelecionado, setFilmeSelecionado] = useState<Filme | null>(null);

  useEffect(() => {
      const carregarFilmes = async () => {
        const filmes = await listarFilmes();
        setFilmes(filmes);
      };
      carregarFilmes();
  }, []);

  return (
    <div className="d-flex vh-100">
      <div className="col-3 border-end overflow-auto p-3">
        <h3>Filmes</h3>
        <FilmeCadastro />

        <ul className="list-unstyled mt-3">
          {filmes.map((u) => (
            <li
              key={u.filmeId}
              onClick={() => setFilmeSelecionado(u)}
              className={`p-2 rounded cursor-pointer ${
                filmeSelecionado?.filmeId === u.filmeId
                  ? "bg-light fw-semibold"
                  : "bg-transparent"
              }`}
              style={{ cursor: "pointer" }}
            >
              {u.titulo}
            </li>
          ))}
        </ul>
      </div>

      <div className="flex-grow-1 p-3">
        {filmeSelecionado ? (
          <>
            <h2>{filmeSelecionado.titulo}</h2>

            <div className="mb-3">
              <label htmlFor="generoFilme" className="form-label fw-bold">
                Gênero:
              </label>
              <input
                type="text"
                id="generoFilme"
                className="form-control"
                value={filmeSelecionado.genero}
                readOnly
              />
            </div>

            <div className="mb-3">
              <label htmlFor="sinopseFilme" className="form-label fw-bold">
                Sinopse:
              </label>
              <textarea
                id="sinopseFilme"
                className="form-control"
                value={filmeSelecionado.sinopse}
                readOnly
              />
            </div>
          </>
        ) : (
          <p>Selecione um usuário no lado esquerdo</p>
        )}
      </div>
    </div>
  );
}

export default FilmesListView;
