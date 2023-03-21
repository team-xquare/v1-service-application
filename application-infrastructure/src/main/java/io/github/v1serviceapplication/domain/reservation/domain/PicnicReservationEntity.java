package io.github.v1serviceapplication.domain.reservation.domain;

import io.github.v1serviceapplication.global.entity.BaseUserInfoEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "tbl_picnic_reservation")
@Entity
public class PicnicReservationEntity extends BaseUserInfoEntity {
}
