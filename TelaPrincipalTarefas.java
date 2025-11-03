import java.awt.*;
import javax.swing.*;

public class TelaPrincipalTarefas extends JFrame {

    public TelaPrincipalTarefas() {
        setTitle("Sistema de Cadastro - Lista de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); //centralizar

        criarMenu(); //criar menu
        criarConteudoPrincipal(); //criar os conteudos 

        setVisible(true);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar(); // barra do menu

        JMenu menuCadastros = new JMenu("Cadastros"); // menu cadastro

        JMenuItem menuItemTarefa = new JMenuItem("Lista de Tarefas"); //submenu item
        menuItemTarefa.addActionListener( e -> abrirFormularioTarefas()); //faz o mesmo do codigo extenso mas de forma direta

        JMenuItem menuItemPrioridade = new JMenuItem("Prioridade"); //submenu item2
        menuItemPrioridade.addActionListener(e -> abrirFormularioPrioridade());

        JMenuItem menuItemResponsavel = new JMenuItem("Responsável"); //submenu item 3
        menuItemResponsavel.addActionListener(e -> abrirFormularioResponsavel());

        menuCadastros.add(menuItemTarefa); //adiciona itens ao submenu
        menuCadastros.add(menuItemPrioridade);
        menuCadastros.add(menuItemResponsavel);

        JMenu menuAjuda = new JMenu("Ajuda"); //menu com submenu item
        JMenuItem menuItemSobre = new JMenuItem("Sobre"); //cria item submenu e adiciona diretamente
        menuItemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(menuItemSobre);

        menuBar.add(menuCadastros); //adiciona os submenus ao menu maior pela barra
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar); //define a barra do menu
    }

    private void criarConteudoPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout()); //criar menu principal

        JPanel painelBoasVindas = new JPanel(new GridBagLayout());
        painelBoasVindas.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel lblTitulo = new JLabel("Sistema de Cadastro de Tarefas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelBoasVindas.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Bem-vindo ao sistema de gerenciamento");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(70, 70, 70));
        gbc.gridy = 1;
        painelBoasVindas.add(lblSubtitulo, gbc);

        JLabel lblInstrucoes = new JLabel(
                "<html><center>Use o menu 'Cadastros' para acessar:<br>• Lista de Tarefas<br>• Prioridades<br>• Responsáveis</center></html>");
        lblInstrucoes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstrucoes.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 20, 20, 20);
        painelBoasVindas.add(lblInstrucoes, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painelBotoes.setBackground(new Color(240, 248, 255));

        JButton btnCadastroTarefa = new JButton("Cadastrar Tarefa");
        btnCadastroTarefa.setPreferredSize(new Dimension(150, 40));
        btnCadastroTarefa.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastroTarefa.setBackground(new Color(70, 130, 180));
        btnCadastroTarefa.setForeground(Color.WHITE);
        btnCadastroTarefa.setFocusPainted(false);
        btnCadastroTarefa.addActionListener(e -> abrirFormularioTarefas());

        JButton btnCadastroPrioridade = new JButton("Cadastrar Prioridade");
        btnCadastroPrioridade.setPreferredSize(new Dimension(150, 40));
        btnCadastroPrioridade.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastroPrioridade.setBackground(new Color(60, 179, 113));
        btnCadastroPrioridade.setForeground(Color.WHITE);
        btnCadastroPrioridade.setFocusPainted(false);
        btnCadastroPrioridade.addActionListener(e -> abrirFormularioPrioridade());

        JButton btnCadastroResponsavel = new JButton("Cadastrar Responsável");
        btnCadastroResponsavel.setPreferredSize(new Dimension(150, 40));
        btnCadastroResponsavel.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastroResponsavel.setBackground(new Color(255, 165, 0));
        btnCadastroResponsavel.setForeground(Color.WHITE);
        btnCadastroResponsavel.setFocusPainted(false);
        btnCadastroResponsavel.addActionListener(e -> abrirFormularioResponsavel());

        painelBotoes.add(btnCadastroTarefa);
        painelBotoes.add(btnCadastroPrioridade);
        painelBotoes.add(btnCadastroResponsavel);

        gbc.gridy = 3;
        gbc.insets = new Insets(40, 20, 20, 20);
        painelBoasVindas.add(painelBotoes, gbc);

        painelPrincipal.add(painelBoasVindas, BorderLayout.CENTER);

        JPanel painelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelStatus.setBackground(new Color(220, 220, 220));
        painelStatus.setBorder(BorderFactory.createEtchedBorder());
        JLabel lblStatus = new JLabel("Sistema pronto para uso");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));
        painelStatus.add(lblStatus);

        painelPrincipal.add(painelStatus, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void abrirFormularioTarefas() {
        SwingUtilities.invokeLater(() -> new FormCadastroListaTarefas());
    }

    private void abrirFormularioPrioridade() {
        SwingUtilities.invokeLater(() -> new FormCadastroPrioridade());
    }

    private void abrirFormularioResponsavel() {
        SwingUtilities.invokeLater(() -> new FormCadastroResponsavel());
    }

    private void mostrarSobre() {
        String mensagem = "Sistema de Cadastro de Lista de Tarefas\n" +
                "Versão 1.0\n\n" +
                "Desenvolvido para gerenciar cadastros de:\n" +
                "• Tarefas\n" +
                "• Prioridades\n" +
                "• Responsáveis\n\n" +
                "© 2025 - Sistema de Cadastro de Tarefas";
        JOptionPane.showMessageDialog(this, mensagem, "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new TelaPrincipalTarefas());
}
}
