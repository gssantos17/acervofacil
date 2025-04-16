package br.com.acervofacil.domain.service.livro;
import br.com.acervofacil.api.dto.request.LivroInputDTO;
import br.com.acervofacil.configuration.mapper.GoogleBooksMapper;
import br.com.acervofacil.configuration.mapper.LivroMapper;
import br.com.acervofacil.domain.entity.Autor;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.exception.NaoEncontradoException;
import br.com.acervofacil.domain.repository.LivroRepository;
import br.com.acervofacil.domain.service.autor.AutorServiceImpl;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;
import br.com.acervofacil.external.service.google.GoogleBooksService;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO ;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final GoogleBooksService googleBooksService;
    private final GoogleBooksMapper googleBooksMapper;
    private final LivroRepository livroRepository;
    private final AutorServiceImpl autorService;
    private final LivroMapper livroMapper;

    /**
     * Método para salvar um livro no sistema.
     * O método realiza a busca dos detalhes do livro via ID ou ISBN,
     * mapeia para a entidade Livro, e persiste no banco junto com seus autores.
     *
     * @param input DTO com as informações do livro para criação ou atualização
     * @return Livro salvo no banco de dados
     */
    @Transactional
    public Livro salvar(@NotNull LivroInputDTO input) {
        // Obter detalhes do livro (com base no ID ou ISBN)
        LivroGoogleDTO livroGoogleDTO = this.obterDetalhesLivroPorIdOuISBN(input);

        // Mapear o DTO para a entidade Livro
        Livro livro = livroMapper.toEntity(livroGoogleDTO);

        // Definir quantidade disponível e total com base no input
        livro.setQuantidadeDisponivel(input.getQuantidadeDisponivel());
        livro.setQuantidadeTotal(input.getQuantidadeTotal());

        // Salvar ou obter autores para o livro
        Set<Autor> autores = autorService.salvar(livroGoogleDTO);
        livro.setAutores(autores);

        // Persistir o livro no banco de dados
        Livro livroSalvo = livroRepository.save(livro);

        // Retornar o livro salvo
        return livroSalvo;
    }

    private LivroGoogleDTO obterDetalhesLivroPorIdOuISBN(LivroInputDTO input){
        if( input.existeISBN() ) {
            return this.buscarLivroPeloISBN(input.getIsbn());
        }
        return this.buscarLivroPeloId( input.getGoogleBooksId() );
    }

    public List<LivroGoogleDTO> buscarLivroPeloTitulo(@NotNull String titulo) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorTitulo(titulo);
        this.seNaoExisteLanca(googleBooksResponse, titulo);
        return googleBooksMapper.toLivroGoogleDTOList(googleBooksResponse.getItems());
    }

    public LivroGoogleDTO buscarLivroPeloISBN(@NotNull String isbn) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorIsbn(isbn);
        this.seNaoExisteLanca(googleBooksResponse, isbn);
        return googleBooksMapper.toLivroGoogleDTO(googleBooksResponse.getItems().get(0));
    }

    public LivroGoogleDTO buscarLivroPeloId(@NotNull String idGoogle) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorId(idGoogle);
        this.seNaoExisteLanca(googleBooksResponse, idGoogle);
        return googleBooksMapper.toLivroGoogleDTO(googleBooksResponse.getItems().get(0));
    }

    private void seNaoExisteLanca(GoogleBooksResponse googleBooksResponse, String parametro){
        if( !googleBooksResponse.existeItem() )
            throw new NaoEncontradoException("Não existe dados para retornar, parametro informado: " + parametro);
    }
}
