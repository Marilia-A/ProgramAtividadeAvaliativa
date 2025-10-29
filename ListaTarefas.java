import java.util.Date;
public class ListaTarefas {
    private int id;
    private Date dataTarefa;
    private String descricao;
    private String observacao;

    //get e set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDataTarefa() {
        return dataTarefa;
    }
    public void setDataTarefa(Date dataTarefa) {
        this.dataTarefa = dataTarefa;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    //construtores
    public ListaTarefas() {
    }
    public ListaTarefas(int id, Date dataTarefa, String descricao, String observacao) {
        this.id = id;
        this.dataTarefa = dataTarefa;
        this.descricao = descricao;
        this.observacao = observacao;
    }
    
}