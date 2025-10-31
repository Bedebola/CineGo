package com.cinego.application.services;

import com.cinego.application.dtos.usuario.UsuarioPrincipalDTO;
import com.cinego.application.dtos.usuario.UsuarioRequestDTO;
import com.cinego.application.dtos.usuario.UsuarioResponseDTO;
import com.cinego.application.dtos.login.LoginRequest;
import com.cinego.domain.exceptions.AcaoInvalidaException;
import com.cinego.domain.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.domain.entities.Usuario;
import com.cinego.domain.interfaces.IEnvioEmail;
import com.cinego.domain.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "UsuarioService", description = "Classe de serviço da entidade USUARIO onde são implementadas as regras de negócio e validação de campos.")
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IEnvioEmail iEnvioEmail;

    public boolean validarSenha(LoginRequest login){
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());
    }

    public void validarCamposUsuario(UsuarioRequestDTO usuario, Long idIgnorar) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (usuario == null){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Usuário não pode ser nulo!");
        }
        if (usuario.nome() == null || usuario.nome().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio.");
        }
        if (usuario.cpf() == null || usuario.cpf().isEmpty() || usuario.cpf().length() < 11 || usuario.cpf().length() > 14){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo CPF não pode ser vazio e deve conter 11 dígitos.");
        }
        if (idIgnorar == null) {
            if (usuario.senha() == null || usuario.senha().isEmpty()){
                throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SENHA não pode ser vazio!");
            }
        }
        if (usuario.email() == null || usuario.email().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo EMAIL não pode ser vazio!");
        }

        boolean emailExiste = (idIgnorar == null)
                ? usuarioRepository.existsByEmailIgnoreCase(usuario.email())
                : usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuario.email(), idIgnorar);

        if (emailExiste){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um usuário cadastrado com esse e-mail!");
        }
    }

    public List<UsuarioResponseDTO> consultarTodosUsuariosSemFiltro() throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            List<Usuario> listaUsuarios = usuarioRepository.findAll();

            if (listaUsuarios.isEmpty()) {
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de usuários está vazia.");
            }

            List<UsuarioResponseDTO> listaUsuariosRetorno = new ArrayList<>();
            for (Usuario usuario : listaUsuarios) {
                listaUsuariosRetorno.add(new UsuarioResponseDTO(usuario));
            }

            return listaUsuariosRetorno;

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao efetuar a busca no banco de dados: " + e.getMessage());
        }
    }

    public UsuarioResponseDTO buscarUsuarioId(Long usuarioId) throws ArgumentoInvalidoOuNaoEncontradoException {
        Usuario usuarioRecord = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuário foi encontrado para o ID informado!"));

        return new UsuarioResponseDTO(usuarioRecord);
    }

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuario) throws ArgumentoInvalidoOuNaoEncontradoException {
        try {
            validarCamposUsuario(usuario, null);

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(usuario.nome());
            novoUsuario.setCpf(usuario.cpf());
            novoUsuario.setEmail(usuario.email());
            novoUsuario.setSenha(usuario.senha());
            novoUsuario.setRole(usuario.role());

            usuarioRepository.save(novoUsuario);

            return new UsuarioResponseDTO(novoUsuario);

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao cadastrar o usuário: " + e.getMessage());
        }
    }

    public UsuarioResponseDTO editarUsuario(Long usuarioId, UsuarioRequestDTO usuario) throws ArgumentoInvalidoOuNaoEncontradoException {
        try {
            Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuário encontrado para o ID informado."));

            validarCamposUsuario(usuario, usuarioId);

            usuarioExistente.setNome(usuario.nome());
            usuarioExistente.setCpf(usuario.cpf());
            usuarioExistente.setEmail(usuario.email());
            if (usuario.senha() != null && !usuario.senha().isEmpty()) {
                usuarioExistente.setSenha(usuario.senha());
            }
            usuarioExistente.setRole(usuario.role());

            usuarioRepository.save(usuarioExistente);

            return new UsuarioResponseDTO(usuarioExistente);

        } catch (Exception e) {
            throw new AcaoInvalidaException("Não foi possível concluir a ação: " + e.getMessage());
        }
    }

    public void excluirUsuario(Long usuarioId) throws ArgumentoInvalidoOuNaoEncontradoException {
        Usuario usuarioRegistrado = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuário foi encontrado para o ID informado!"));

        usuarioRepository.delete(usuarioRegistrado);
    }

    public void recuperarSenha(UsuarioPrincipalDTO usuarioLogado) {

        iEnvioEmail.enviarEmailSImples(usuarioLogado.email(),
                "CodigoRecuperacao",
                "123456"
                );

    }
}
