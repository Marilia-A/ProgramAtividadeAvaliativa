import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private JButton btnTarefa, btnResponsavel, btnPrioridade;

    public TelaPrincipal() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,200);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new FlowLayout());

        btnTarefa = new JButton("Cadastro de Tarefa");
        btnResponsavel = new JButton("Cadastro de Responsável");
        btnPrioridade = new JButton("Cadastro de Prioridade");

        btnTarefa.addActionListener(e -> new FormCadastroListaTarefas());
        btnResponsavel.addActionListener(e -> new FormCadastroResponsavel());
        btnPrioridade.addActionListener(e -> new FormCadastroPrioridade());

        painel.add(btnTarefa);
        painel.add(btnResponsavel);
        painel.add(btnPrioridade);

        add(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
