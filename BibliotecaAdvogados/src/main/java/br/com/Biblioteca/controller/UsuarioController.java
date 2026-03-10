package br.com.Biblioteca.controller;

import br.com.Biblioteca.model.entity.Livro;
import br.com.Biblioteca.model.entity.Usuario;
import br.com.Biblioteca.model.enums.FuncaoUsuario;
import br.com.Biblioteca.repository.HistoricoLeituraRepository;
import br.com.Biblioteca.repository.RepositoryManager;
import br.com.Biblioteca.repository.UsuarioRepository;
import br.com.Biblioteca.validacoes.ValidacaoCPF;
import br.com.Biblioteca.validacoes.ValidacaoEmail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private final RepositoryManager repositoryManager;
    private final UsuarioRepository usuarioRepository;
    private final ValidacaoCPF validacaoCPF;
    private final ValidacaoEmail validacaoEmail;

    public UsuarioController() {
        this.repositoryManager = RepositoryManager.getInstance();
        this.usuarioRepository = repositoryManager.getUsuarioRepository();
        this.validacaoCPF = new ValidacaoCPF();
        this.validacaoEmail = new ValidacaoEmail();
    }

    public long gerarId(){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();
        long maior = 0;
        for(Usuario usuario : listaUsuarios){
            if(usuario.getId() > maior){
                maior = usuario.getId();
            }
        }
        return maior + 1;
    }

    // ADICIONAR USUARIOS
    public String adicionarUsuario(String nome, String email, String cpf, FuncaoUsuario funcao){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();

        if(buscarUsuario(cpf) != null){
            return "Usuário já cadastrado";
        }

        if(!validacaoCPF.validarCPF(cpf)){
            return "CPF inválido";
        }

        if(!validacaoEmail.validarEmail(email)){
            return "E-mail inválido";
        }

        long id = gerarId();

        Usuario novoUsuario = new Usuario(
                id,
                nome,
                email,
                cpf,
                funcao
        );

        listaUsuarios.add(novoUsuario);
        repositoryManager.salvarTodos();

        return "Usuário adicionado com sucesso";
    }

    // DELETAR USUARIOS
    public String deletarUsuario(String cpf){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();

        Usuario u = buscarUsuario(cpf);

        if(u != null){
            listaUsuarios.remove(u);
            repositoryManager.salvarTodos();
            return "Usuario deletado com sucesso";
        }else{
            return "Usuário não encontrado";
        }
    }

    // MOSTRAR USUARIOS CADASTRADOS
    public void mostrarUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();

        if(listaUsuarios.isEmpty()){
            System.out.println("Nenhum usuário cadastrado");
            return;
        }

        for(Usuario usuario : listaUsuarios){
            System.out.println(usuario);
        }
    }

    // EDITAR USUÁRIO
    public String editarUsuario(String cpf, String novoNome, String novoEmail, FuncaoUsuario novaFuncao){
        Usuario usuario = buscarUsuario(cpf);

        if(usuario == null){
            return "Usuário não encontrado";
        }

        if(!validacaoEmail.validarEmail(novoEmail)){
            return "E-mail inválido";
        }

        usuario.setNome(novoNome);
        usuario.setEmail(novoEmail);
        usuario.setFuncao(novaFuncao);

        repositoryManager.salvarTodos();

        return "Usuário editado com sucesso";
    }

    // BUSCA POR UM USUÁRIO COM O MESMO CPF
    public Usuario buscarUsuario(String cpf){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();
        for(Usuario usuario : listaUsuarios){
            if(usuario.getCpf().equals(cpf)){
                return usuario;
            }
        }
        return null;
    }

    // PROCURA POR UM USUARIO DE ACORDO COM O NOME
    public Usuario buscarPorNome(String nome){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();
        for(Usuario usuario : listaUsuarios){
            if(usuario.getNome().equalsIgnoreCase(nome)){
                return usuario;
            }
        }
        return null;
    }

    // RANK DOS USUÁRIOS POR PONTOS
    public List<Usuario> rankingUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.getListaUsuarios();
        listaUsuarios.sort((u1, u2) ->
                Long.compare(u2.getPontos(), u1.getPontos())
        );
        return listaUsuarios;
    }

    // USUÁRIO QUE MAIS LEU
    public String usuarioMaisAtivo(){
        HistoricoLeituraRepository historicoLeituraRepository = repositoryManager.getHistoricoLeituraRepository();

        Map<String, Integer> contador = new HashMap<>();

        for(var historico : historicoLeituraRepository.getListaHistorico()){
            String nome = historico.getNomeUsuario();

            contador.put(
                    nome,
                    contador.getOrDefault(nome, 0) + 1
            );
        }

        String maisAtivo = null;
        int maior = 0;

        for(String nome : contador.keySet()){
            if(contador.get(nome) > maior){
                maior = contador.get(nome);
                maisAtivo = nome;
            }
        }

        return maisAtivo;
    }
}
