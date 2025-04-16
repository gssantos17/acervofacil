package br.com.acervofacil.api.controller;

import br.com.acervofacil.api.dto.request.LivroInputDTO;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.api.dto.response.RespostaPadronizada;
import br.com.acervofacil.api.utils.ApiUtils;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.service.livro.LivroServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Livros", description = "Endpoints para operações com livros")
public class LivroController {

    private final LivroServiceImpl livroServiceImpl;

    @Operation(
            summary = "Criar um novo livro",
            description = "Cadastra um novo livro no sistema a partir dos dados informados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                            content = @Content(schema = @Schema(implementation = RespostaPadronizada.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPadronizada<Livro>> criarLivro(
            @RequestBody @Valid LivroInputDTO input) {

        Livro livroCriado = livroServiceImpl.salvar(input);
        URI uri = ApiUtils.getURI("/livros/", livroCriado.getId());
        return ApiUtils.obterResponseEntityCreated(livroCriado, null, uri);
    }

    @Operation(
            summary = "Buscar livro por título, ISBN ou ID do Google",
            description = "Busca informações de um livro em fontes externas (Google Books) com base em título, ISBN ou ID do Google. Pelo menos um parâmetro é obrigatório.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                            content = @Content(schema = @Schema(implementation = LivroGoogleDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida - nenhum parâmetro informado",
                            content = @Content(schema = @Schema(implementation = RespostaPadronizada.class)))
            }
    )
    @GetMapping("/busca-externo")
    public ResponseEntity<RespostaPadronizada<List<LivroGoogleDTO>>> buscarPorParametros(
            @Parameter(description = "Título do livro para busca") @RequestParam(required = false) String titulo,
            @Parameter(description = "ISBN do livro para busca") @RequestParam(required = false) String isbn,
            @Parameter(description = "ID do Google Books para busca") @RequestParam(required = false) String idGoogle,
            @Parameter(description = "nome do autor para busca") @RequestParam(required = false) String autor){

        if (titulo == null && isbn == null && idGoogle == null && autor == null) {
            return ResponseEntity.badRequest().body(
                    RespostaPadronizada.badRequest("Pelo menos um parâmetro deve ser informado: titulo, autor, isbn ou idGoogle",
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
        } else if( autor != null ){
            resposta = livroServiceImpl.buscarLivroPeloAutor(autor);
        }

        return ApiUtils.obterResponseEntityOk(resposta, null);
    }
}