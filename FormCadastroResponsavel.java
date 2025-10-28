import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class FormCadastroResponsavel extends JFrame {
    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;

    public FormCadastroResponsavel(){
        setTitle("Cadastro de Responsável");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350,180);
        setLocationRelativeTo(null);

        iniciarComponentes();
        setVisible(true);
    }

    private void iniciarComponentes(){
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        painelPrincipal.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNome = new JTextField(15);
        painelPrincipal.add(txtNome, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> salvarResponsavel());
        btnAlterar.addActionListener(e -> alterarResponsavel());
        btnExcluir.addActionListener(e -> excluirResponsavel());
        btnPesquisar.addActionListener(e -> pesquisarResponsavel());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        add(painelPrincipal);
    }

    private void salvarResponsavel() {
    try (Connection conn = conexao.connect()) {
        String sql = "INSERT INTO responsavel (nome) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, txtNome.getText());
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Responsável salvo com sucesso.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
    }
}

private void alterarResponsavel() {
    try (Connection conn = conexao.connect()) {
        String sql = "UPDATE responsavel SET nome = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, txtNome.getText());
        stmt.setInt(2, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Responsável alterado.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
    }
}

private void excluirResponsavel() {
    try (Connection conn = conexao.connect()) {
        String sql = "DELETE FROM responsavel WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Responsável excluído.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
    }
}

private void pesquisarResponsavel() {
    try (Connection conn = conexao.connect()) {
        String sql = "SELECT * FROM responsavel WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            txtNome.setText(rs.getString("nome"));
        } else {
            JOptionPane.showMessageDialog(this, "Responsável não encontrado.");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + ex.getMessage());
    }
}

}
