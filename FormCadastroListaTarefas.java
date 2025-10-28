import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class FormCadastroListaTarefas extends JFrame {
    private JTextField txtId, txtDataTarefa, txtDescricao, txtObservacao;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnBuscarResponsavel, btnBuscarPrioridade;

    public FormCadastroListaTarefas(){
        setTitle("Cadastro de Lista de Tarefas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,250);
        setLocationRelativeTo(null);

        iniciarComponentes();
        setVisible(true);
    }

    private void iniciarComponentes(){
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        //Campo ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"),gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        painelPrincipal.add(txtId,gbc);

        //Campo DataTarefa
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Data da Tarefa:"),gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDataTarefa = new JTextField(15);
        painelPrincipal.add(txtDataTarefa,gbc);

        //Campo Descricao
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Descrição:"),gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDescricao = new JTextField(15);
        painelPrincipal.add(txtDescricao,gbc);

        //Campo Observacao
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Observação:"),gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtObservacao = new JTextField(15);
        painelPrincipal.add(txtObservacao,gbc);

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnBuscarResponsavel = new JButton("Buscar Responsável");
        btnBuscarPrioridade = new JButton("Buscar Prioridade");

        //Adicionar listeners aos botões
        btnSalvar.addActionListener(e -> salvarTarefa());
        btnAlterar.addActionListener(e -> alterarTarefa());
        btnExcluir.addActionListener(e -> excluirTarefa());
        btnPesquisar.addActionListener(e -> pesquisarTarefa());
        btnBuscarResponsavel.addActionListener(e -> buscarResponsavel());
        btnBuscarPrioridade.addActionListener(e -> buscarPrioridade());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnBuscarResponsavel);
        painelBotoes.add(btnBuscarPrioridade);

        // Adicionar painel de botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        add(painelPrincipal);
    }

private void salvarTarefa() {
    try (Connection conn = Conexao.connect()) {   // Usa sua classe de conexão
        String sql = "INSERT INTO lista_tarefas (datatarefa, descricao, observacao) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, java.sql.Date.valueOf(txtDataTarefa.getText())); // yyyy-MM-dd
        stmt.setString(2, txtDescricao.getText());
        stmt.setString(3, txtObservacao.getText());
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
    }
}

private void alterarTarefa() {
    try (Connection conn = Conexao.connect()) {
        String sql = "UPDATE lista_tarefas SET datatarefa = ?, descricao = ?, observacao = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, java.sql.Date.valueOf(txtDataTarefa.getText()));
        stmt.setString(2, txtDescricao.getText());
        stmt.setString(3, txtObservacao.getText());
        stmt.setInt(4, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Tarefa alterada com sucesso.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
    }
}

private void excluirTarefa() {
    try (Connection conn = Conexao.connect()) {
        String sql = "DELETE FROM lista_tarefas WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Tarefa excluída.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
    }
}

private void pesquisarTarefa() {
    try (Connection conn = Conexao.connect()) {
        String sql = "SELECT * FROM lista_tarefas WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            txtDataTarefa.setText(rs.getString("datatarefa"));
            txtDescricao.setText(rs.getString("descricao"));
            txtObservacao.setText(rs.getString("observacao"));
        } else {
            JOptionPane.showMessageDialog(this, "Tarefa não encontrada.");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + ex.getMessage());
    }
}

private void buscarResponsavel() {
    // Pode abrir uma nova tela/lista ou realizar uma busca simples.
    JOptionPane.showMessageDialog(this, "Buscar Responsável: implementar seleção/lista.");
}

private void buscarPrioridade() {
    JOptionPane.showMessageDialog(this, "Buscar Prioridade: implementar seleção/lista.");
}

}
