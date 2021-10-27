package lv.forfun.demo.features.notes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.domain.NoteRepository;
import lv.forfun.demo.features.Mapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;
    private final Mapper mapper;

    public NoteDto getRootNoteDto(Long categoryId) {
        return NoteDto.root()
                .withChildren(findChildPageIdsAsString(null, categoryId));
    }

    public NoteDto getRootNoteDto(List<Note> all) {
        return NoteDto.root()
                .setChildren(findChildPageIdsAsString(null, all));
    }

    public NoteDto getNoteDto(Long noteId, Long categoryId) {
        return noteId == null
                ? getRootNoteDto(categoryId)
                : getLeafNoteDto(noteId);
    }

    private NoteDto getLeafNoteDto(Long noteId) {
        Note note = repository.findById(noteId).orElseThrow();
        List<String> childIds = findChildPageIdsAsString(noteId, note.getCategoryId());
        return mapper.toNoteDTO(note).withChildren(childIds);
    }

    public List<String> findChildPageIdsAsString(Long noteId, Long categoryId) {
        return findChildPageIds(noteId, categoryId)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> findChildPageIdsAsString(Long noteId, List<Note> all) {
        return findChildPageIds(noteId, all)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<Long> findChildPageIds(Long noteId, Long categoryId) {
        List<Note> all = repository.findAllByParentIdAndCategoryId(noteId, categoryId);
        return findChildPageIds(noteId, all);
    }

    public List<Long> findChildPageIds(Long noteId, List<Note> all) {
        return all.stream()
                .filter(it -> Objects.equals(noteId, it.getParentId()))
                .sorted(Comparator.comparing(Note::getSortIdx))
                .map(Note::getId)
                .collect(Collectors.toList());
    }
}
