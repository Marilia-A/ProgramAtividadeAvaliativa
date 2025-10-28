import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class FormCadastroPrioridade extends JFrame {
    private JTextField txtId, txtDescricao;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;

    public FormCadastroPrioridade(){
        setTitle("Cadastro de Prioridade");
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
        painelPrincipal.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDescricao = new JTextField(15);
        painelPrincipal.add(txtDescricao, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> salvarPrioridade());
        btnAlterar.addActionListener(e -> alterarPrioridade());
        btnExcluir.addActionListener(e -> excluirPrioridade());
        btnPesquisar.addActionListener(e -> pesquisarPrioridade());

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

    private void salvarPrioridade() {
    try (Connection conn = conexao.connect()) {
        String sql = "INSERT INTO prioridade (descricao) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, txtDescricao.getText());
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Prioridade salva com sucesso.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
    }
}

private void alterarPrioridade() {
    try (Connection conn = conexao.connect()) {
        String sql = "UPDATE prioridade SET descricao = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, txtDescricao.getText());
        stmt.setInt(2, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Prioridade alterada.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
    }
}

private void excluirPrioridade() {
    try (Connection conn = conexao.connect()) {
        String sql = "DELETE FROM prioridade WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Prioridade excluída.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
    }
}

private void pesquisarPrioridade() {
    try (Connection conn = conexao.connect()) {
        String sql = "SELECT * FROM prioridade WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            txtDescricao.setText(rs.getString("descricao"));
        } else {
            JOptionPane.showMessageDialog(this, "Prioridade não encontrada.");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + ex.getMessage());
    }
}

}

