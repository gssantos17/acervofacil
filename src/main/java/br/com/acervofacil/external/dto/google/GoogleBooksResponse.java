package br.com.acervofacil.external.dto.google;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GoogleBooksResponse {

    private int totalItems;
    private List<Item> items;

    @Data
    @NoArgsConstructor
    public static class Item {
        private String id;
        private VolumeInfo volumeInfo;
    }

    @Data
    @NoArgsConstructor
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private List<IndustryIdentifier> industryIdentifiers;
        private String publisher;
        private String publishedDate;
        private ImageLinks imageLinks;
    }

    @Data
    @NoArgsConstructor
    public static class IndustryIdentifier {
        private String type;
        private String identifier;
    }

    @Data
    @NoArgsConstructor
    public static class ImageLinks {
        private String thumbnail;
    }

    public boolean existeItem(){
        return items != null && !items.isEmpty();
    }
}
