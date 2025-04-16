package br.com.acervofacil.external.dto.google;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleBookVolume {
    private String id;
    private GoogleBooksResponse.VolumeInfo volumeInfo;
}

