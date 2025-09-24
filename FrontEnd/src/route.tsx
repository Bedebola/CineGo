import { Route, Routes } from "react-router-dom";
import Layout from "./Componentes/Layout";
import Home from "./Paginas/Home/index";
import Filme from "./Paginas/Filmes";
import Usuario from "./Paginas/Usuarios";
import Login from "./Paginas/Login";

function AppRoutes() {
  return (


    <Routes>
        <Route path="/login" element={<Login />} />

      <Route element={<Layout />}>
        <Route path ="/" element={<Home />} />
        <Route path ="/filmes" element={<Filme />} />
        <Route path="/filmeView/:filmeId" element={<Filme />} />
        <Route path="/usuarios" element={<Usuario />} />
        <Route path="/usuarioView/:usuarioId" element={<Usuario />} />
      </Route>
    </Routes>
  );
}

export default AppRoutes;