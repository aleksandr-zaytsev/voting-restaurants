package ru.azaytsev.votingrestaurants.user.to;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RestaurantTo {
    @NotBlank
    String name;

    Integer todayCountVoting;

    List<MenuTo> menuToList;

}
