public class Prioridade {
    private int id;
    private String descricao;
    
    //get e set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    // construtores
    public Prioridade() {
    }

    public Prioridade(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

}