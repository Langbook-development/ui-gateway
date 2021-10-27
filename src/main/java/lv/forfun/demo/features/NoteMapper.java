package lv.forfun.demo.features;

import lombok.RequiredArgsConstructor;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.features.notes.services.NoteService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;
import static lv.forfun.demo.Constants.ROOT_ID;

@Service
@RequiredArgsConstructor
public class NoteMapper {

    private final MapperFacade mapper;
    private final NoteService service;

    public NoteDto toNoteDTO(Note it) {
        return mapper.map(it, NoteDto.class)
                .withParentId(it.getParentId() == null ? ROOT_ID : it.getParentId().toString());
    }

    public NoteDto enrichByChildIds(NoteDto dto, List<Note> all) {
        return dto.withChildren(toString(service.findChildPageIds(parseLong(dto.getId()), all)));
    }

    public NoteDto getRootNoteDto(List<Note> all) {
        return NoteDto.root()
                .setChildren(toString(service.findChildPageIds(null, all)));
    }

    public NoteDto getNoteDto(Long noteId, Long categoryId) {
        return noteId == null
                ? getRootNoteDto(categoryId)
                : getLeafNoteDto(noteId);
    }

    private NoteDto getRootNoteDto(Long categoryId) {
        return NoteDto.root()
                .withChildren(toString(service.findChildPageIds(null, categoryId)));
    }

    private NoteDto getLeafNoteDto(Long noteId) {
        Note note = service.findById(noteId);
        List<String> childIds = toString(service.findChildPageIds(noteId, note.getCategoryId()));
        return toNoteDTO(note).withChildren(childIds);
    }

    private List<String> toString(List<Long> ids) {
        return ids.stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
