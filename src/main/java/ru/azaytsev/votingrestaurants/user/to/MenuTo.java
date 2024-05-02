package ru.azaytsev.votingrestaurants.user.to;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class MenuTo {
    String dish;

    BigDecimal price;
}
