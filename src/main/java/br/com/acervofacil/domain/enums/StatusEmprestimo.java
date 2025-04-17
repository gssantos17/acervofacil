package br.com.acervofacil.domain.enums;

public enum StatusEmprestimo {
    ATIVO("Ativo"),
    FINALIZADO("Finalizado"),
    EM_ATRASO("Em Atraso"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}