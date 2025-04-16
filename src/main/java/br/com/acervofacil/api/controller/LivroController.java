package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.LivroInputDTO;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.service.livro.LivroServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroServiceImpl livroServiceImpl;

    /**
     * Endpoint para criar um novo livro no sistema.
     *
     * @param input DTO contendo os dados do livro a ser criado
     * @return Resposta contendo o livro recém-criado
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<Livro>> criarLivro(
            @RequestBody @Valid LivroInputDTO input) {

        Livro livroCriado = livroServiceImpl.salvar(input);
        URI uri = ApiUtils.getURI("/livros/", livroCriado.getId());
        return ApiUtils.obterResponseEntityCreated(livroCriado, null, uri);
    }

    @GetMapping("/busca-externo")
    public ResponseEntity<RespostaPadronizada<List<LivroGoogleDTO>>> buscarPorParametros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String idGoogle) {

        if (titulo == null && isbn == null && idGoogle == null) {
            return ResponseEntity.badRequest().body(
                    RespostaPadronizada.badRequest("Pelo menos um parâmetro deve ser informado: titulo, isbn ou idGoogle",
                            null, null)
            );
        }

        List<LivroGoogleDTO> resposta = List.of();

        if (titulo != null) {
            resposta = livroServiceImpl.buscarLivroPeloTitulo(titulo);
        } else if (isbn != null) {
            var livro = livroServiceImpl.buscarLivroPeloISBN(isbn);
            resposta = List.of(livro);
        } else if (idGoogle != null) {
            var livro = livroServiceImpl.buscarLivroPeloId(idGoogle);
            resposta = List.of(livro);
        }

        return ApiUtils.obterResponseEntityOk(resposta, null);
    }
}
