package br.com.acervofacil.domain.service.autor;

import br.com.acervofacil.api.dto.response.LivroGoogleDTO;
import br.com.acervofacil.domain.entity.Autor;
import br.com.acervofacil.domain.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor // Lombok para injeção de dependências via construtor
public class AutorServiceImpl {

    private final AutorRepository autorRepository;

    /**
     * Salva os autores do livro a partir do DTO.
     *
     * @param livroGoogleDTO DTO com informações do livro
     * @return Set de autores
     */
    @Transactional
    public Set<Autor> salvar(LivroGoogleDTO livroGoogleDTO) {
        Set<Autor> autores = new HashSet<>();

        // Para cada autor no DTO, verifica se já existe ou cria um novo
        if (livroGoogleDTO.getAutores() != null) {
            for (String nomeAutor : livroGoogleDTO.getAutores()) {
                Autor autor = autorRepository.findByNome(nomeAutor)
                        .orElseGet(() -> criarAutor(nomeAutor));
                autores.add(autor);
            }
        }

        return autores;
    }

    /**
     * Cria um novo autor e persiste no banco.
     *
     * @param nomeAutor Nome do autor
     * @return Autor criado
     */
    private Autor criarAutor(String nomeAutor) {
        Autor autor = new Autor();
        autor.setNome(nomeAutor);
        return autorRepository.save(autor);
    }
}
