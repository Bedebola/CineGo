import { useState, useRef } from "react";
import { buscarFilmeId } from "../../api/filmes-api";

interface Filme {
  titulo: string;
  sinopse: string;
  status: string;
}

interface FilmeFormProps {
  filmeId: number;
}


function FilmeForm({ filmeId }: FilmeFormProps) {
  const [filme, setFilme] = useState<Filme | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);

  const abrirDialog = async () => {
    try {
      const dadosFilme = await buscarFilmeId(Number(filmeId));
      console.log(filmeId)
      setFilme(dadosFilme);

      if (dialogRef.current) {
        dialogRef.current.showModal();
      }
    } catch (error) {
      console.error("Erro ao buscar filme:", error);
    }
  };

  const fecharDialog = () => {
    if (dialogRef.current) {
      dialogRef.current.close();
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-primary">
        Ver Filme
      </button>

      <dialog ref={dialogRef}>
        <h3>{filme?.titulo}</h3>
        <p>{filme?.sinopse}</p>
        <p>Status: {filme?.status}</p>
        <button onClick={fecharDialog}>Fechar</button>
        <button>Alugar</button>
      </dialog>
    </>
  );
}

export default FilmeForm;
