package ru.azaytsev.votingrestaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.azaytsev.votingrestaurants.common.model.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(name = "menu_restaurant_id_menu_date_idx",
        columnNames = {"restaurant_id", "menu_date"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Menu(Integer id, Restaurant restaurant, LocalDate menuDate, List<Dish> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.menuDate = menuDate;
        this.dishes = dishes;
    }
}
