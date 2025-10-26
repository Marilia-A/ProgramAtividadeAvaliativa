public class FormCadastroPrioridade {
    
}
JMenuBar menuBar = new JMenuBar();
JMenu menuCadastro = new JMenu("Cadastros");
JMenuItem itemTarefa = new JMenuItem("Lista de Tarefas");
JMenuItem itemPrioridade = new JMenuItem("Prioridade");
JMenuItem itemResponsavel = new JMenuItem("Responsável");
menuCadastro.add(itemTarefa);
menuCadastro.add(itemPrioridade);
menuCadastro.add(itemResponsavel);
menuBar.add(menuCadastro);
setJMenuBar(menuBar);


