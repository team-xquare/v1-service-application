package io.github.v1serviceapplication.domain.picnicapply;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_picnic_apply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
public class PicnicApply {
    @Id
    private UUID userId;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    private LocalTime endTime;

    @NotNull
    private String reason;

    @NotNull
    private String arrangement;
}
