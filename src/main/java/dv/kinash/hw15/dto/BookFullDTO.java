package dv.kinash.hw15.dto;

import lombok.Data;

@Data
public class BookFullDTO {
    private Long id;
    private String description;
    private String author;
    private Long ISBN;
}
