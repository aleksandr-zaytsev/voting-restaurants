package ru.azaytsev.votingrestaurants.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.azaytsev.votingrestaurants.common.HasId;
import ru.azaytsev.votingrestaurants.common.model.BaseEntity;
import ru.azaytsev.votingrestaurants.common.model.NamedEntity;
import ru.azaytsev.votingrestaurants.common.validation.NoHtml;

import java.io.Serializable;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class Restaurant extends NamedEntity implements Serializable {

//    public Restaurant(Restaurant restaurant) {
//        this(restaurant.id, restaurant.name);
//    }
//
//    public Restaurant(Integer id, String name) {
//        super(id, name);
//    }
}