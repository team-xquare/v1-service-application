package io.github.v1serviceapplication.domain.stayapply;

import io.github.v1serviceapplication.global.entity.BaseUUIDEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_stay_apply")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayApply extends BaseUUIDEntity {
    @NotNull
    @Column(columnDefinition = "CHAR(6)")
    private String code;
}
