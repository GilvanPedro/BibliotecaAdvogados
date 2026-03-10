package br.com.Biblioteca;

import br.com.Biblioteca.controller.DashboardController;
import br.com.Biblioteca.controller.HistoricoLeituraController;
import br.com.Biblioteca.controller.LivroController;
import br.com.Biblioteca.controller.UsuarioController;
import br.com.Biblioteca.model.entity.HistoricoLeitura;
import br.com.Biblioteca.model.entity.Livro;
import br.com.Biblioteca.model.entity.Usuario;
import br.com.Biblioteca.model.enums.FuncaoUsuario;
import br.com.Biblioteca.model.enums.TipoLeitura;
import br.com.Biblioteca.model.enums.TipoLivro;
import br.com.Biblioteca.repository.RepositoryManager;
import br.com.Biblioteca.service.EmprestimoService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Inicializa o RepositoryManager para carregar os dados
        RepositoryManager.getInstance();

        UsuarioController usuarioController = new UsuarioController();
        LivroController livroController = new LivroController();
        EmprestimoService emprestimoService = new EmprestimoService();
        HistoricoLeituraController historicoLeituraController = new HistoricoLeituraController();
        DashboardController dashboardController = new DashboardController();

        System.out.println("\n--- Teste de Usuários ---");
        // Adicionar usuários
        System.out.println(usuarioController.adicionarUsuario("Gilvan", "gilvan@email.com", "06531492120", FuncaoUsuario.GERENTE));
        System.out.println(usuarioController.adicionarUsuario("Maria", "maria@email.com", "11122233344", FuncaoUsuario.ADVOGADO));
        System.out.println(usuarioController.adicionarUsuario("João", "joao@email.com", "55566677788", FuncaoUsuario.ESTAGIARIO));
        System.out.println(usuarioController.adicionarUsuario("Gilvan", "gilvan@email.com", "06531492120", FuncaoUsuario.GERENTE)); // Tentativa de adicionar duplicado

        System.out.println("\nUsuários cadastrados:");
        usuarioController.mostrarUsuarios();

        // Editar usuário
        System.out.println(usuarioController.editarUsuario("11122233344", "Maria Silva", "maria.silva@email.com", FuncaoUsuario.SOCIO));
        System.out.println("\nUsuários após edição:");
        usuarioController.mostrarUsuarios();

        // Buscar usuário
        Usuario usuarioBuscado = usuarioController.buscarUsuario("06531492120");
        System.out.println("\nUsuário buscado (Gilvan): " + (usuarioBuscado != null ? usuarioBuscado.getNome() : "Não encontrado"));

        System.out.println("\n--- Teste de Livros ---");
        // Adicionar livros
        System.out.println(livroController.adicionarLivro("Direito Civil", "Autor A", "Editora X", TipoLivro.DOUTRINA, TipoLeitura.EMPRESTIMO, 5));
        System.out.println(livroController.adicionarLivro("Direito Penal", "Autor B", "Editora Y", TipoLivro.DICIONARIO_JURIDICO, TipoLeitura.EMPRESTIMO, 1));
        System.out.println(livroController.adicionarLivro("Constituição Federal", "Vários", "Editora Z", TipoLivro.VADE_MECUM, TipoLeitura.PRESENCIAL, 3));

        System.out.println("\nLivros cadastrados:");
        livroController.mostrarLivros();

        // Editar livro
        System.out.println(livroController.editarLivro(1, "Novo Direito Civil", "Novo Autor A", "Nova Editora X", TipoLivro.LEGISLACAO));
        System.out.println("\nLivros após edição:");
        livroController.mostrarLivros();

        // Buscar livro
        Livro livroBuscado = livroController.buscarLivro(2);
        System.out.println("\nLivro buscado (Direito Penal): " + (livroBuscado != null ? livroBuscado.getTitulo() : "Não encontrado"));

        System.out.println("\n--- Teste de Empréstimos e Devoluções ---");
        // Empréstimo de livro
        System.out.println(emprestimoService.pegarLivro("06531492120", 1)); // Gilvan pega Direito Civil
        System.out.println(emprestimoService.pegarLivro("11122233344", 3)); // Maria pega Constituição Federal (presencial)
        System.out.println(emprestimoService.pegarLivro("55566677788", 2)); // João pega Direito Penal (última cópia, presencial)
        System.out.println(emprestimoService.pegarLivro("06531492120", 1)); // Gilvan tenta pegar outro livro (já tem um)

        System.out.println("\nHistórico de Leituras após empréstimos:");
        historicoLeituraController.mostrarHistorico();

        // Devolução de livro
        System.out.println(emprestimoService.devolverLivro(1)); // Gilvan devolve Direito Civil

        System.out.println("\nHistórico de Leituras após devolução:");
        historicoLeituraController.mostrarHistorico();

        System.out.println("\n--- Teste do Dashboard ---");
        System.out.println("Total de usuários: " + dashboardController.totalUsuarios());
        System.out.println("Total de livros: " + dashboardController.totalLivros());
        System.out.println("Total de livros únicos: "+ dashboardController.totalLivrosUnicos());
        System.out.println("Total de empréstimos: " + dashboardController.totalEmprestimos());
        System.out.println("Livro mais lido: " + dashboardController.livroMaisLido());
        System.out.println("Usuário mais ativo: " + dashboardController.usuarioMaisAtivo());

        System.out.println("\n--- Teste de Ranking de Usuários ---");
        List<Usuario> ranking = usuarioController.rankingUsuarios();
        System.out.println("Ranking de Usuários por Pontos:");
        for (Usuario u : ranking) {
            System.out.println("  " + u.getNome() + " - Pontos: " + u.getPontos());
        }

        // Deletar usuário e livro
        System.out.println("\n--- Teste de Deleção ---");
        System.out.println(usuarioController.deletarUsuario("55566677788"));
        System.out.println(livroController.deletarLivro(2));

        System.out.println("\nUsuários após deleção:");
        usuarioController.mostrarUsuarios();
        System.out.println("\nLivros após deleção:");
        livroController.mostrarLivros();

        System.out.println("\n--- Fim dos Testes ---");
    }
}
