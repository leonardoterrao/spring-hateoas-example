package br.com.leonardoterrao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Book {

    private Long id;
    private String title;
    private Author author;
    private int stockLevel;

    public void borrowOne() {
        stockLevel = stockLevel - 1;
    }

}
