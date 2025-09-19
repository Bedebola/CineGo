package com.cinego.services;

import com.cinego.dtos.DtoUsuario;
import com.cinego.dtos.LoginRequest;
import com.cinego.exceptions.AcaoInvalidaException;
import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Usuario;
import com.cinego.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequest login){
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());
    }

    public DtoUsuario criarRetornoUsuario(Usuario usuario) throws ArgumentoInvalidoOuNaoEncontradoException{
        DtoUsuario usuarioRetorno = new DtoUsuario();
        usuarioRetorno.setNome(usuario.getNome());
        usuarioRetorno.setCpf(usuario.getCpf());
        usuarioRetorno.setEmail(usuario.getEmail());

        return usuarioRetorno;
    }

    public void validarCamposUsuario(Usuario usuario, Long idIgnorar) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (usuario == null){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Usuario não pode ser nulo!");
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio");
        }
        if (usuario.getCpf() == null || usuario.getCpf().isEmpty() || usuario.getCpf().length() < 11 || usuario.getCpf().length() > 14){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo CPF não pode ser vazio e deve conter 11 dígitos");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SENHA não pode ser vazio!");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo EMAIL não pode ser vazio!");
        }

        boolean emailExiste = (idIgnorar == null)
                ?usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())
                :usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuario.getEmail(), usuario.getId());

        if (emailExiste){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um usuário cadastrado com esse e-mail!");
        }

        if (usuario.getRole() == null || usuario.getRole().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo ROLE não pode ser vazio!");
        }
    }

    public List<Usuario> listarUsuarios() throws ArgumentoInvalidoOuNaoEncontradoException{
        try{
            List<Usuario> listaUsuariosRetorno = usuarioRepository.findAll();

            if (listaUsuariosRetorno.isEmpty()){
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de usuarios está vazia.");
            }

            return listaUsuariosRetorno;

        }catch (Exception e){
            throw new AcaoInvalidaException("Ocorreu um erro ao efetuar a busca no banco de dados: "+e.getMessage());
        }
    }

    public DtoUsuario buscarUsuarioId(Long idUsuario) throws ArgumentoInvalidoOuNaoEncontradoException{
        try{
            Usuario usuarioRecord = usuarioRepository.findById(idUsuario)
                    .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario foi encontrado para o ID informado!"));

            return criarRetornoUsuario(usuarioRecord);

        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoUsuario cadastrarUsuario(Usuario usuario) throws ArgumentoInvalidoOuNaoEncontradoException{
        try {
            validarCamposUsuario(usuario, null);
            usuarioRepository.save(usuario);

            return criarRetornoUsuario(usuario);

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao cadastrar o usuario: " + e.getMessage());
        }
    }

    public DtoUsuario editarUsuario(Long idUsuario, Usuario usuario) throws ArgumentoInvalidoOuNaoEncontradoException {
        try{
            Usuario usuarioExistente = usuarioRepository.findById(idUsuario)
                    .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

            validarCamposUsuario(usuarioExistente, null);
            usuarioRepository.save(usuarioExistente);

            return criarRetornoUsuario(usuarioExistente);

        } catch (Exception e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Não foi possível concluir a ação: " + e);
        }
    }

    public void excluirUsuario(Long idUsuario) throws ArgumentoInvalidoOuNaoEncontradoException {

        Usuario usuarioRegistrado = usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme foi encontrado para o ID informado!"));

        usuarioRepository.delete(usuarioRegistrado);
    }
}