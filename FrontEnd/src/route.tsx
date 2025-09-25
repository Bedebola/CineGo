import { Route, Routes } from "react-router-dom";
import Layout from "./Componentes/Layout";
import Home from "./Paginas/Home/index";
import Filme from "./Paginas/Filmes";
import CadastrarFilme from "./Paginas/Filmes/CadastrarFilme";
import CadastrarUsuario from "./Paginas/Usuarios/CadastrarUsuario";
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
        <Route path="/cadastrarFilme" element={<CadastrarFilme />} />        

        <Route path="/usuarios" element={<Usuario />} />
        <Route path="/usuarioView/:usuarioId" element={<Usuario />} />
        <Route path="/cadastrarUsuario" element={<CadastrarUsuario />} />

      </Route>
    </Routes>
  );
}

export default AppRoutes;