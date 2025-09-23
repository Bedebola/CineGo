function Login() {
  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card p-4 shadow" style={{ minWidth: "300px" }}>
        <h1 className="text-center mb-4">Login</h1>

        <form>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email</label>
            <input
              type="email"
              id="email"
              className="form-control"
              placeholder="Digite seu email"
            />
          </div>

          <div className="mb-3">
            <label htmlFor="senha" className="form-label">Senha</label>
            <input
              type="password"
              id="senha"
              className="form-control"
              placeholder="Digite sua senha"
            />
          </div>

          <button type="submit" className="btn btn-dark w-100">
            Entrar
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
