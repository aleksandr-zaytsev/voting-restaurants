package ru.azaytsev.votingrestaurants.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ru.azaytsev.votingrestaurants.common.HasId;
import ru.azaytsev.votingrestaurants.common.model.NamedEntity;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity implements Serializable {

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 100000)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonIgnore
    private Menu menu;

//    public Dish(Integer id, String name, BigDecimal price) {
//        super(id, name);
//        this.price = price;
//        this.menu = null;
//    }
}