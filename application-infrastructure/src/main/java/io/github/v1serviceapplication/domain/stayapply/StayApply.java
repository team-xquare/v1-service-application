package io.github.v1serviceapplication.domain.stayapply;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "tbl_stay_apply")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayApply {
    @Id
    private UUID userId;

    @NotNull
    @Column(columnDefinition = "CHAR(6)")
    private String code;
}
