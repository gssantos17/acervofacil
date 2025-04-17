package br.com.acervofacil.domain.service.livro;

import br.com.acervofacil.api.dto.request.RequisicaoLivroDTO;
import br.com.acervofacil.api.dto.response.LivroDTO;
import br.com.acervofacil.api.utils.ServiceUtils;
import br.com.acervofacil.configuration.mapper.GoogleBooksMapper;
import br.com.acervofacil.configuration.mapper.LivroMapper;
import br.com.acervofacil.domain.entity.Autor;
import br.com.acervofacil.domain.entity.Livro;
import br.com.acervofacil.domain.repository.LivroRepository;
import br.com.acervofacil.domain.service.autor.AutorServiceImpl;
import br.com.acervofacil.external.dto.google.GoogleBooksResponse;
import br.com.acervofacil.external.service.google.GoogleBooksService;
import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final GoogleBooksService googleBooksService;
    private final GoogleBooksMapper googleBooksMapper;
    private final LivroRepository livroRepository;
    private final AutorServiceImpl autorService;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO salvar(@NotNull RequisicaoLivroDTO input) {
        LivroGoogleDTO livroGoogleDTO = this.obterDetalhesLivroPorIdOuISBN(input);
        Livro livro = livroMapper.toEntity(livroGoogleDTO);
        livro.setQuantidadeDisponivel(input.quantidadeDisponivel());
        livro.setQuantidadeTotal(input.quantidadeTotal());
        Set<Autor> autores = autorService.salvar(livroGoogleDTO);
        livro.setAutores(autores);
        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toDTO(livroSalvo);
    }

    private LivroGoogleDTO obterDetalhesLivroPorIdOuISBN(RequisicaoLivroDTO input){
        if( input.existeISBN() ) {
            return this.buscarLivroPeloISBNExterno(input.isbn());
        }
        return this.buscarLivroPeloIdExterno(input.googleBooksId());
    }

    public List<LivroGoogleDTO> buscarLivroPeloTituloExterno(@NotNull String titulo) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorTitulo(titulo);
        ServiceUtils.seNaoExisteLancaExterno(googleBooksResponse, titulo);
        return googleBooksMapper.toLivroGoogleDTOList(googleBooksResponse.getItems());
    }

    public LivroGoogleDTO buscarLivroPeloISBNExterno(@NotNull String isbn) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorIsbn(isbn);
        ServiceUtils.seNaoExisteLancaExterno(googleBooksResponse, isbn);
        return googleBooksMapper.toLivroGoogleDTO(googleBooksResponse.getItems().get(0));
    }

    @Override
    public Page<LivroDTO> buscarLivroPeloTituloExterno() {
        return null;
    }

    public LivroGoogleDTO buscarLivroPeloIdExterno(@NotNull String idGoogle) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorId(idGoogle);
        ServiceUtils.seNaoExisteLancaExterno(googleBooksResponse, idGoogle);
        return googleBooksMapper.toLivroGoogleDTO(googleBooksResponse.getItems().get(0));
    }

    public List<LivroGoogleDTO> buscarLivroPeloAutor(String autor) {
        GoogleBooksResponse googleBooksResponse = googleBooksService.buscarLivroPorAutor(autor);
        ServiceUtils.seNaoExisteLancaExterno(googleBooksResponse, autor);
        return googleBooksMapper.toLivroGoogleDTOList(googleBooksResponse.getItems());
    }

    @Override
    public LivroDTO buscarLivroPeloId(UUID id) {
        Livro livro = ServiceUtils.obterOuLancar(livroRepository.findById(id), "Livro", id.toString());
        return livroMapper.toDTO(livro);
    }

    @Override
    public LivroDTO buscarLivroPeloISBN(String isbn) {
        Livro livro = ServiceUtils.obterOuLancar(livroRepository.findByIsbn(isbn), "Livro", isbn);
        return livroMapper.toDTO(livro);
    }
}