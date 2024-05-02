package ru.azaytsev.votingrestaurants.user.to;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RestaurantListTo {
    List<RestaurantTo> restaurantList;
}
