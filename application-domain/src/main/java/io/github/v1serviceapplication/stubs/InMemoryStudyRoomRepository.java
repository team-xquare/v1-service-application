package io.github.v1serviceapplication.stubs;

import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryStudyRoomRepository implements StudyRoomRepositorySpi {

    private final HashMap<UUID, StudyRoom> studyRoomMap = new HashMap<>();
    private final HashMap<UUID, Extension> extensionMap = new HashMap<>();

    public void saveStudyRoom(StudyRoom studyRoom) {
        studyRoomMap.put(studyRoom.getId(), studyRoom);
    }

    public void saveExtension(Extension extension) {
        extensionMap.put(extension.getUserId(), extension);
    }

    @Override
    public List<StudyRoomModel> findAll() {
        return studyRoomMap.values()
                .stream()
                .map(studyRoom ->
                        StudyRoomModel.builder()
                                .id(studyRoom.getId())
                                .name(studyRoom.getName())
                                .applicationCount(findExtensionById(studyRoom.getId()).size())
                                .maxPeopleCount(studyRoom.getMaxPeopleCount())
                                .studentList(findExtensionById(studyRoom.getId()))
                                .build()
                )
                .collect(Collectors.toList());
    }

    private List<UUID> findExtensionById(UUID studyRoomId) {
        return extensionMap.values()
                .stream()
                .filter(extension -> extension.getStudyRoomId().equals(studyRoomId))
                .map(Extension::getUserId)
                .collect(Collectors.toList());
    }
}
