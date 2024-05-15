package ru.azaytsev.votingrestaurants.to;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RestVoteResult {

    @NotBlank
    String name;

    @NotBlank
    Integer voteSummury;

}
