import { useEffect, useState, useRef } from "react";
import { listarFilmes, buscarFilmeId } from "../../api/filmes-api";

interface Filme {
  filmeId: number;
  titulo: string;
  sinopse: string;
  status: string;
}

function Filmes() {
  const [filmes, setFilmes] = useState<Filme[]>([]);
  const [filmeSelecionado, setFilmeSelecionado] = useState<Filme | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    async function carregarFilmes() {
      try {
        const data: Filme[] = await listarFilmes();
        setFilmes(data);
      } catch (error) {
        console.error("Erro ao buscar filmes:", error);
      }
    }
    carregarFilmes();
  }, []);

  const abrirDialog = async (filmeId: number) => {
    try {
      const filme = await buscarFilmeId(filmeId);
      setFilmeSelecionado(filme);
      dialogRef.current?.showModal();
    } catch (error) {
      console.error("Erro ao buscar filme:", error);
    }
  };

  const fecharDialog = () => dialogRef.current?.close();

  return (
    <>
      {/* deixa espaço livre para a sidebar fixa à direita (250px) */}
      <main
        className="container-fluid min-vh-100 py-4"
        style={{ paddingRight: 260 }}
      >
        <h2 className="h5 mb-3">Filmes</h2>

        <div className="table-responsive">
          <table className="table align-middle">
            <thead>
              <tr>
                <th>Status</th>
                <th>Título</th>
                <th className="text-nowrap">Ações</th>
              </tr>
            </thead>
            <tbody>
              {filmes.map((filme) => (
                <tr key={filme.filmeId}>
                  <td>{filme.status}</td>
                  <td>{filme.titulo}</td>
                  <td>
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => abrirDialog(filme.filmeId)}
                    >
                      Visualizar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>

      <dialog ref={dialogRef} style={{ zIndex: 1100 }}>
        {filmeSelecionado && (
          <>
            <h3 className="h5">{filmeSelecionado.titulo}</h3>
            <p className="mb-2">{filmeSelecionado.sinopse}</p>
            <p className="mb-3">Status: {filmeSelecionado.status}</p>
            <div className="d-flex gap-2">
              <button className="btn btn-secondary" onClick={fecharDialog}>
                Fechar
              </button>
              <button className="btn btn-success">Alugar</button>
            </div>
          </>
        )}
      </dialog>
    </>
  );
}

export default Filmes;
