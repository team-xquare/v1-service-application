package io.github.v1serviceapplication.infrastructure.excel.service;

import io.github.v1serviceapplication.domain.stay.domain.repository.StayRepository;
import io.github.v1serviceapplication.domain.stay.mapper.StayMapper;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayApplyListResponse;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayStatus;
import io.github.v1serviceapplication.infrastructure.excel.usecase.GetStayApplyListUseCase;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StayExcelService implements GetStayApplyListUseCase {

    private final StayRepository stayRepository;
    private final StayMapper stayMapper;

    public StayApplyListResponse getStayApplyList() {
        return StayApplyListResponse.builder()
                .students(getStayStatusList())
                .build();
    }

    private List<StayStatus> getStayStatusList() {
        return stayRepository.findAll().stream()
                .map(stayMapper::mapToStayStatus)
                .collect(Collectors.toList());
    }
}