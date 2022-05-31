package io.github.v1serviceapplication.domain.studyroom;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = {StudyRoom.class},
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = DomainService.class
                )
        }
)
public class StudyRoomConfig {
}
