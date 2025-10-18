import { Route, Routes } from "react-router-dom";
import Layout from "./Componentes/Layout";
import Home from "./Paginas/Home/index";
import Filme from "./Paginas/Filmes";
import FilmeListView from "./Paginas/Filmes/FilmesListView";
import Login from "./Paginas/Login";
import UsuariosListView from "./Paginas/Usuarios";


function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route element={<Layout />}>
        <Route path="/" element={<Home />} />
        <Route path="/filmes" element={<Filme />} />
        <Route path="/filmeView/:filmeId" element={<Filme />} />
        <Route path="/filmesListView" element={<FilmeListView/>} />

        <Route path="/usuariosListView" element={<UsuariosListView />} />

      </Route>
    </Routes>
  );
}

export default AppRoutes;
