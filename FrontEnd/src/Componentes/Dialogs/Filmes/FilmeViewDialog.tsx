import { useState, useRef, useEffect } from "react";
import {
  buscarFilmeId,
  alugarFilme,
  devolverFilme,
  desativarFilme,
  ativarFilme,
  enviarEmailLembreteDeDevolucao,
  type Filme,
  type FilmeViewProps,
} from "../../../api/filmesService";
import { useDispatch } from "react-redux";
import { updateFilme } from "../../../Redux/filmesSlice";
import { getRoleFromToken } from "../../../api/api";

function FilmeViewDialog({ filmeId, onChange }: FilmeViewProps) {
  const [filme, setFilme] = useState<Filme | null>(null);
  const [emailCliente, setEmailCliente] = useState("");
  const [role, setRole] = useState<string | null>(null);
  const dialogRef = useRef<HTMLDialogElement>(null);
  const dispatch = useDispatch();

  useEffect(() => {
    async function buscarRole() {
      const r = await getRoleFromToken();
      setRole(r);
    }
    buscarRole();
  }, []);

  const isAdmin = role === "ADMIN";

  const abrirDialog = async () => {
    const dados = await buscarFilmeId(Number(filmeId));
    setFilme(dados);
    dialogRef.current?.showModal();
  };

  const fecharDialog = () => dialogRef.current?.close();

  const handleAlugar = async () => {
    if (emailCliente && filme?.filmeId) {
      await alugarFilme({ filmeId: filme.filmeId, emailCliente });
      const atualizado = await buscarFilmeId(Number(filmeId));
      setFilme(atualizado);
      dispatch(updateFilme(atualizado));
      onChange?.();
    } else {
      alert("Por favor, preencha o e-mail do cliente.");
    }
  };

  const handleDevolver = async () => {
    if (filme?.filmeId) {
      await devolverFilme(Number(filme.filmeId));
      const atualizado = await buscarFilmeId(Number(filmeId));
      setFilme(atualizado);
      dispatch(updateFilme(atualizado));
      onChange?.();
    }
  };

  const handleDesativar = async () => {
    if (filme?.filmeId) {
      await desativarFilme(Number(filme.filmeId));
      const atualizado = await buscarFilmeId(Number(filmeId));
      setFilme(atualizado);
      dispatch(updateFilme(atualizado));
      onChange?.();
    }
  };

  const handleAtivar = async () => {
    if (filme?.filmeId) {
      await ativarFilme(Number(filme.filmeId));
      const atualizado = await buscarFilmeId(Number(filmeId));
      setFilme(atualizado);
      dispatch(updateFilme(atualizado));
      onChange?.();
    }
  };

  const handleEnviarEmail = async () => {
    if (filme?.filmeId) {
      try {
        await enviarEmailLembreteDeDevolucao(filme.filmeId);
        alert("E-mail de lembrete enviado com sucesso!");
      } catch (error) {
        alert("Erro ao enviar e-mail de lembrete.");
        console.error(error);
      }
    }
  };

  return (
    <>
      <button onClick={abrirDialog} className="btn btn-sm">
        <i className="bi bi-eye-fill"></i>
      </button>

      <dialog
        ref={dialogRef}
        className="border-0 rounded-4 shadow-lg p-0"
        style={{
          position: "fixed",
          inset: "50% auto auto 50%",
          transform: "translate(-50%, -50%)",
          width: "min(550px, 92vw)",
          background: "transparent",
          zIndex: 1100,
        }}
      >
        <div className="bg-white rounded-4 overflow-hidden">
          <div className="bg-dark text-white px-4 py-3 d-flex justify-content-between align-items-center">
            <h5 className="m-0">{filme?.titulo || "Detalhes do Filme"}</h5>
            <div className="d-flex align-items-center gap-2">
              <span
                className={`badge ${
                  filme?.status === "DISPONIVEL"
                    ? "bg-success"
                    : filme?.status === "ALUGADO"
                    ? "bg-danger"
                    : "bg-secondary"
                }`}
              >
                {filme?.status}
              </span>
              <button onClick={fecharDialog} className="btn-close btn-close-white" />
            </div>
          </div>

          <div className="p-4">
            <p className="form-label fw-bold text-dark">Sinopse do Filme</p>
            <p className="text-muted">{filme?.sinopse}</p>
          </div>

          <div className="p-4">
            <p className="form-label fw-bold text-dark">Gênero do Filme</p>
            <p className="text-muted">{filme?.genero}</p>
          </div>

          {filme?.status === "DISPONIVEL" && (
            <div className="p-4">
              <p className="form-label fw-bold text-dark">E-mail Cliente:</p>
              <input
                type="email"
                placeholder="Insira o e-mail do cliente"
                value={emailCliente}
                onChange={(e) => setEmailCliente(e.target.value)}
                className="form-control"
              />
            </div>
          )}

          <div className="d-flex gap-2 justify-content-end px-4 pb-4 flex-wrap">
            {filme?.status === "DISPONIVEL" && (
              <>
                <button onClick={handleAlugar} className="btn btn-success">
                  Alugar
                </button>
                {isAdmin && (
                  <button onClick={handleDesativar} className="btn btn-warning">
                    Desativar
                  </button>
                )}
              </>
            )}

            {filme?.status === "ALUGADO" && (
              <>
                <button onClick={handleDevolver} className="btn btn-info">
                  Devolver
                </button>
              </>
            )}

            {filme?.status === "DESATIVADO" && isAdmin && (
              <button onClick={handleAtivar} className="btn btn-secondary">
                Ativar
              </button>
            )}

            <button onClick={handleEnviarEmail} className="btn btn-outline-primary">
              Enviar E-mail
            </button>
          </div>
        </div>
      </dialog>
    </>
  );
}

export default FilmeViewDialog;
