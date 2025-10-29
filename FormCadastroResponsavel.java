import java.awt.*;
import java.sql.*;
import javax.swing.*;
public class FormCadastroResponsavel extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JButton btnSalvar;
    private JButton btnAlterar; 
    private JButton btnExcluir; 
    private JButton btnPesquisar;
    private JButton btnSair;

    public FormCadastroResponsavel(){
        setTitle("Cadastro de Responsável");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridLayout(6, 2, 10, 10));
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

        //Campo Nome
        JLabel lblDescricao = new JLabel("Nome");
        gbc.gridx = 0; 
        gbc.gridy = 2;
        add(lblDescricao, gbc);
        txtNome = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 2;
        add(txtNome, gbc);

        //Painel de Botões
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

        btnSalvar.addActionListener(e -> salvarResponsavel());
        btnPesquisar.addActionListener(e -> pesquisarResponsavel());
        btnAlterar.addActionListener(e -> alterarResponsavel());
        btnExcluir.addActionListener(e -> excluirResponsavel());
        btnSair.addActionListener(e -> dispose());
    }

    private void salvarResponsavel() {
        try (Connection conn = Conexao.connect()) {
            String sql = "INSERT INTO responsavel (id, nome) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.setString(2, txtNome.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Responsável salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void alterarResponsavel() {
        try (Connection conn = Conexao.connect()) {
            String sql = "UPDATE responsavel SET nome = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtNome.getText());
            stmt.setInt(2, Integer.parseInt(txtId.getText()));
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "Responsável alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com esse ID.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
        }
    }

    private void excluirResponsavel() {
        try (Connection conn = Conexao.connect()) {
            String sql = "DELETE FROM responsavel WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "Responsável excluído com sucesso!");
                txtId.setText("");
                txtNome.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com esse ID.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void pesquisarResponsavel() {
        try (Connection conn = Conexao.connect()) {
            String sql = "SELECT * FROM responsavel WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
            } else {
                JOptionPane.showMessageDialog(this, "Responsável não encontrado!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCadastroResponsavel().setVisible(true));
    }
}