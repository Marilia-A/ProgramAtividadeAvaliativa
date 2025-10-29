import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;


public class FormCadastroListaTarefas extends JFrame {
    private JTextField txtId;
    private JTextField txtDescricao;
    private JTextField txtObservacao;
    private JTextField txtDataTarefa;
    private JComboBox<String> cmbPrioridade;
    private JComboBox<String> cmbResponsavel;
    private JButton btnSalvar;
    private JButton btnPesquisar;
    private JButton btnAlterar;
    private JButton btnExcluir; 
    private JButton btnLimpar;
    private JButton btnSair;

    public FormCadastroListaTarefas(){
        setTitle("Cadastro de Lista de Tarefas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridBagLayout());

     //retirei o iniciar componentes

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
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

        //Campo DataTarefa
        JLabel lblData = new JLabel("Data (dd/MM/yyyy):");
        gbc.gridx = 0; 
        gbc.gridy = 1;
        add(lblData, gbc);
        txtDataTarefa = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 1;
        add(txtDataTarefa, gbc);

        //Campo Descricao
        JLabel lblDescricao = new JLabel("Descrição:");
        gbc.gridx = 0; 
        gbc.gridy = 2;
        add(lblDescricao, gbc);
        txtDescricao = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 2;
        add(txtDescricao, gbc);


        //Campo Observacao
        JLabel lblObservacao = new JLabel("Observação:");
        gbc.gridx = 0; 
        gbc.gridy = 3;
        add(lblObservacao, gbc);
        txtObservacao = new JTextField();
        gbc.gridx = 1; 
        gbc.gridy = 3;
        add(txtObservacao, gbc);

        // --- Combo Prioridade ---
        JLabel lblPrioridade = new JLabel("Prioridade:");
        gbc.gridx = 0; 
        gbc.gridy = 4;
        add(lblPrioridade, gbc);
        cmbPrioridade = new JComboBox<>();
        gbc.gridx = 1; 
        gbc.gridy = 4;
        add(cmbPrioridade, gbc);

        // --- Combo Responsável ---
        JLabel lblResponsavel = new JLabel("Responsável:");
        gbc.gridx = 0; 
        gbc.gridy = 5;
        add(lblResponsavel, gbc);
        cmbResponsavel = new JComboBox<>();
        gbc.gridx = 1; 
        gbc.gridy = 5;
        add(cmbResponsavel, gbc);


        //Painel de botões
        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnPesquisar = new JButton("Pesquisar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridwidth = 2;
        gbc.gridx = 0; 
        gbc.gridy = 6;
        add(painelBotoes, gbc);
        carregarComboPrioridades();
        carregarComboResponsaveis();

        btnSalvar.addActionListener(e -> salvar());
        btnPesquisar.addActionListener(e -> pesquisar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());
        btnSair.addActionListener(e -> dispose());
    }

    // --- Carregar Prioridades ---
    private void carregarComboPrioridades() {
        try (Connection con = Conexao.connect()) {
            cmbPrioridade.removeAllItems();
            String sql = "SELECT id, descricao FROM prioridade ORDER BY id";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cmbPrioridade.addItem(rs.getInt("id") + " - " + rs.getString("descricao"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar prioridades: " + e.getMessage());
        }
    }

    // --- Carregar Responsáveis ---
    private void carregarComboResponsaveis() {
        try (Connection con = Conexao.connect()) {
            cmbResponsavel.removeAllItems();
            String sql = "SELECT id, nome FROM responsavel ORDER BY id";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cmbResponsavel.addItem(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar responsáveis: " + e.getMessage());
        }
    }

        // --- Salvar ---
    private void salvar() {
        String dataTexto = txtDataTarefa.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String observacao = txtObservacao.getText().trim();

        if (descricao.isEmpty() || dataTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.connect()) {
            java.sql.Date dataSql = converterData(dataTexto);
            int idPrioridade = Integer.parseInt(cmbPrioridade.getSelectedItem().toString().split(" - ")[0]);
            int idResponsavel = Integer.parseInt(cmbResponsavel.getSelectedItem().toString().split(" - ")[0]);

            String sql = "INSERT INTO lista_tarefas (id, data_tarefa, descricao_tarefa, observacao, id_prioridade, id_responsavel) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.setDate(2, dataSql);
            stmt.setString(3, descricao);
            stmt.setString(4, observacao);
            stmt.setInt(5, idPrioridade);
            stmt.setInt(6, idResponsavel);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso!");
            limpar();

        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Pesquisar ---
    private void pesquisar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID da tarefa!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.connect()) {
            String sql = """
                SELECT t.id, t.data_tarefa, t.descricao_tarefa, t.observacao,
                       p.id AS id_prioridade, r.id AS id_responsavel
                FROM lista_tarefas t
                JOIN prioridade p ON p.id = t.id_prioridade
                JOIN responsavel r ON r.id = t.id_responsavel
                WHERE t.id = ?
            """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtDataTarefa.setText(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("data_tarefa")));
                txtDescricao.setText(rs.getString("descricao_tarefa"));
                txtObservacao.setText(rs.getString("observacao"));
                selecionarItemCombo(cmbPrioridade, rs.getInt("id_prioridade"));
                selecionarItemCombo(cmbResponsavel, rs.getInt("id_responsavel"));
            } else {
                JOptionPane.showMessageDialog(this, "Tarefa não encontrada!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Alterar ---
    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID da tarefa!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.connect()) {
            java.sql.Date dataSql = converterData(txtDataTarefa.getText().trim());
            int idPrioridade = Integer.parseInt(cmbPrioridade.getSelectedItem().toString().split(" - ")[0]);
            int idResponsavel = Integer.parseInt(cmbResponsavel.getSelectedItem().toString().split(" - ")[0]);

            String sql = "UPDATE lista_tarefas SET data_tarefa=?, descricao_tarefa=?, observacao=?, id_prioridade=?, id_responsavel=? WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, dataSql);
            stmt.setString(2, txtDescricao.getText().trim());
            stmt.setString(3, txtObservacao.getText().trim());
            stmt.setInt(4, idPrioridade);
            stmt.setInt(5, idResponsavel);
            stmt.setInt(6, Integer.parseInt(txtId.getText()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0)
                JOptionPane.showMessageDialog(this, "Tarefa alterada com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "ID não encontrado!");

        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Excluir ---
    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID da tarefa!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.connect()) {
            String sql = "DELETE FROM lista_tarefas WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0)
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "ID não encontrado!");

            limpar();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Limpar campos ---
    private void limpar() {
        txtId.setText("");
        txtDataTarefa.setText("");
        txtDescricao.setText("");
        txtObservacao.setText("");
        cmbPrioridade.setSelectedIndex(-1);
        cmbResponsavel.setSelectedIndex(-1);
    }

    // --- Converter data para SQL ---
    private java.sql.Date converterData(String dataTexto) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(dataTexto);
        return new java.sql.Date(data.getTime());
    }

    // --- Selecionar item no combo pelo ID ---
    private void selecionarItemCombo(JComboBox<String> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(String.valueOf(id) + " -")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCadastroListaTarefas().setVisible(true));
   }
}