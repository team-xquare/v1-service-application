package io.github.v1serviceapplication.domain.stayapply.domain;

import io.github.v1serviceapplication.global.entity.BaseUUIDEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_stay_apply")
@Entity
public class StayApplyEntity extends BaseUUIDEntity {
    @NotNull
    @Column(columnDefinition = "CHAR(6)")
    private String code;
}
