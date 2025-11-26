import { Route, Routes } from "react-router-dom";
import Layout from "./Componentes/Layout";
import Home from "./Paginas/Home/index";
import Filme from "./Paginas/Filmes";
import Login from "./Paginas/Login";
import UsuariosListView from "./Paginas/Usuarios/index";
import UsuarioView from "./Paginas/Usuarios/UsuarioView";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route element={<Layout />}>
        <Route
          path="/"
          element={<Home />}
        />
        <Route path="/filmes" element={<Filme />} />
        <Route path="/filmeView/:filmeId" element={<Filme />} />

        <Route
          path="/usuariosListView"
          element={<UsuariosListView />}
        />

        <Route path="/usuarioMeusDados" element={<UsuarioView />} />
      </Route>
    </Routes>
  );
}

export default AppRoutes;