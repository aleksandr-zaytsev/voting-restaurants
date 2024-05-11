package ru.azaytsev.votingrestaurants.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.azaytsev.votingrestaurants.common.model.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // надо или нет ???
public class Menu extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // or LAZY ???
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Dish> dishes;
}
