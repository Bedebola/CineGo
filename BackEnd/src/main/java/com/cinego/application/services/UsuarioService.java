package com.cinego.application.services;

import com.cinego.application.dtos.usuario.UsuarioRequestDTO;
import com.cinego.application.dtos.usuario.UsuarioResponseDTO;
import com.cinego.application.dtos.login.LoginRequest;
import com.cinego.domain.exceptions.AcaoInvalidaException;
import com.cinego.domain.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.domain.entities.Usuario;
import com.cinego.domain.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Tag(name = "UsuarioService", description = "Classe de serviço da entidade USUARIO onde são implementadas as regras de negocio e validação de campos.")
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequest login){
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());
    }


    public void validarCamposUsuario(UsuarioRequestDTO usuario, Long idIgnorar) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (usuario == null){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Usuario não pode ser nulo!");
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio");
        }
        if (usuario.getCpf() == null || usuario.getCpf().isEmpty() || usuario.getCpf().length() < 11 || usuario.getCpf().length() > 14){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo CPF não pode ser vazio e deve conter 11 dígitos");
        }
        if (idIgnorar == null) {
            if (usuario.getSenha() == null || usuario.getSenha().isEmpty()){
                throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SENHA não pode ser vazio!");
            }
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo EMAIL não pode ser vazio!");
        }

        boolean emailExiste = (idIgnorar == null)
                ?usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())
                :usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuario.getEmail(), idIgnorar);

        if (emailExiste){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um usuário cadastrado com esse e-mail!");
        }
    }

    public List<UsuarioResponseDTO> consultarTodosUsuariosSemFiltro() throws ArgumentoInvalidoOuNaoEncontradoException{

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());

//        try{
//            List<Usuario> listaUsuarios = usuarioRepository.findAll();
//
//            if (listaUsuarios.isEmpty()){
//                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de usuarios está vazia.");
//            }
//
//            List<UsuarioResponseDTO> listaUsuariosRetorno = new ArrayList<>();
//
//            for (int i = 0; i < listaUsuarios.size(); i++) {
//                Usuario usuario = listaUsuarios.get(i);
//                UsuarioResponseDTO dtoUsuario = criarRetornoUsuario(usuario);
//
//                listaUsuariosRetorno.add(dtoUsuario);
//            }
//
//            return listaUsuariosRetorno;
//
//        }catch (Exception e){
//            throw new AcaoInvalidaException("Ocorreu um erro ao efetuar a busca no banco de dados: "+e.getMessage());
//        }
    }

    public UsuarioResponseDTO buscarUsuarioId(Long usuarioId) throws ArgumentoInvalidoOuNaoEncontradoException{

        return usuarioRepository.findById(usuarioId).map(UsuarioResponseDTO::new).orElse(null);
//        try{
//            Usuario usuarioRecord = usuarioRepository.findById(usuarioId)
//                    .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario foi encontrado para o ID informado!"));
//
//            return criarRetornoUsuario(usuarioRecord);
//
//        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequest) throws ArgumentoInvalidoOuNaoEncontradoException{

        var usuario = usuarioRepository.findByCpf(usuarioRequest.cpf()).map(u -> {
            u.setNome(usuarioRequest.nome());
            u.setEmail(usuarioRequest.email());
            u.setSenha(usuarioRequest.senha());
            u.setRole(usuarioRequest.role());
            return u;
        })
            .orElseThrow(new Usuario(usuarioRequest));

        usuarioRepository.save(usuario);
        return usuario.toResposteDto();


//        try {
//            validarCamposUsuario(usuario, null);
//            usuarioRepository.save(usuario);
//
//            return criarRetornoUsuario(usuario);
//
//        } catch (Exception e) {
//            throw new AcaoInvalidaException("Ocorreu um erro ao cadastrar o usuario: " + e.getMessage());
//        }
    }

    public UsuarioResponseDTO editarUsuario(Long usuarioId, Usuario usuario) throws ArgumentoInvalidoOuNaoEncontradoException {
        try{
            Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                    .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

            validarCamposUsuario(usuario, usuarioId);

            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setCpf(usuario.getCpf());
            usuarioExistente.setEmail(usuario.getEmail());

            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioExistente.setSenha(usuario.getSenha());
            }

            usuarioExistente.setRole(usuario.getRole());

            usuarioRepository.save(usuarioExistente);

            return criarRetornoUsuario(usuarioExistente);

        } catch (Exception e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Não foi possível concluir a ação: " + e);
        }
    }

    public void excluirUsuario(Long usuarioId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Usuario usuarioRegistrado = usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme foi encontrado para o ID informado!"));

        usuarioRepository.delete(usuarioRegistrado);
    }
}