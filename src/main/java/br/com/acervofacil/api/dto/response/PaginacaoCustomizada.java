package br.com.acervofacil.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaginacaoCustomizada<T> {
    private List<T> conteudo;
    private int totalPaginas;
    private long totalElementos;
    private int paginaAtual;
    private int tamanhoPagina;
    private boolean temProxima;
    private boolean temAnterior;

    public PaginacaoCustomizada(Page<T> page) {
        this.conteudo = page.getContent();
        this.totalPaginas = page.getTotalPages();
        this.totalElementos = page.getTotalElements();
        this.paginaAtual = page.getNumber();
        this.tamanhoPagina = page.getSize();
        this.temProxima = page.hasNext();
        this.temAnterior = page.hasPrevious();
    }
}
