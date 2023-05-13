package io.github.v1serviceapplication.domain.picnic.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_picnic_apply")
@Entity
public class PicnicEntity extends BaseEntity {

    @NotNull
    @CreatedDate
    private LocalDateTime createDateTime;

    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private String reason;

    @NotNull
    private String arrangement;

    private LocalTime dormitoryReturnCheckTime;

    public void updatePicnic(LocalTime startTime, LocalTime endTime, String reason, String arrangement) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.arrangement = arrangement;
    }
}
