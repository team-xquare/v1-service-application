package io.github.v1serviceapplication.domain.weekendmealapply;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "tbl_weekend_meal_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class WeekendMealApply {
    @Id
    private UUID userId;

    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isApplied;
}
