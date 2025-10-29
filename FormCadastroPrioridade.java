import java.awt.*;
import java.sql.*;
import javax.swing.*;
public class FormCadastroPrioridade extends JFrame {
    private JTextField txtId;
    private JTextField txtDescricao;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JButton btnSair;

    public FormCadastroPrioridade(){
        setTitle("Cadastro de Prioridade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,250);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Campo ID
        JLabel lblId = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblId, gbc);
        txtId = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 0;
        add(txtId, gbc);

        // Campo Descrição
        JLabel lblDescricao = new JLabel("Descrição:");
        gbc.gridx = 0; 
        gbc.gridy = 1;
        add(lblDescricao, gbc);
        txtDescricao = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 1;
        add(txtDescricao, gbc);

        // Painel de Botões
        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnPesquisar = new JButton("Pesquisar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnSair);

        gbc.gridwidth = 2;
        gbc.gridx = 0; 
        gbc.gridy = 6;
        add(painelBotoes, gbc);

        btnSalvar.addActionListener(e -> salvarPrioridade());
        btnPesquisar.addActionListener(e -> pesquisarPrioridade());
        btnAlterar.addActionListener(e -> alterarPrioridade());
        btnExcluir.addActionListener(e -> excluirPrioridade());
        btnSair.addActionListener(e -> dispose());

    }

    private void salvarPrioridade() {
    try (Connection conn = Conexao.connect()) {
        String sql = "INSERT INTO prioridade (id, descricao) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(txtId.getText()));
        stmt.setString(2, txtDescricao.getText());
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Prioridade salva com sucesso.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
    }
}

private void alterarPrioridade() {
    try (Connection conn = Conexao.connect()) {
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
    try (Connection conn = Conexao.connect()) {
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
    try (Connection conn = Conexao.connect()) {
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCadastroPrioridade().setVisible(true));
    }
}

