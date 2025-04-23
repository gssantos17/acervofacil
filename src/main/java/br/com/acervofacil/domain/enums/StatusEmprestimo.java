package br.com.acervofacil.domain.enums;

public enum StatusEmprestimo {
    ATIVO("Ativo"),
    CONCLUIDO("Concluido"),
    EM_ATRASO("Em Atraso"),
    CANCELADO("Cancelado"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
