package io.github.v1serviceapplication.domain.picnicdatetime.domain;

import io.github.v1serviceapplication.picnicdatetime.DayType;
import io.github.v1serviceapplication.picnicdatetime.TimeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_picnic_time")
@Entity
public class PicnicTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private LocalTime picnicTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TimeType timeType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DayType day;
}
