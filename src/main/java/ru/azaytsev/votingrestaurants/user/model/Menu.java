package ru.azaytsev.votingrestaurants.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.azaytsev.votingrestaurants.common.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "day_of_week"}, name = "menu_unique_restaurant_id_day_of_week_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @NotNull
    @Column(name = "date_modify", nullable = false)
    private LocalDateTime dateModify = LocalDateTime.now();
}
