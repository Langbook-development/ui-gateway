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
import java.util.stream.Collectors;

import static lv.forfun.demo.Constants.ROOT_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;
    private final Mapper mapper;

    public NoteDto getRootNoteDto(Long categoryId) {
        return NoteDto.root()
                .withChildren(findChildPageIdsAsString(ROOT_ID, categoryId));
    }

    public NoteDto getRootNoteDto(List<Note> all) {
        return NoteDto.root()
                .setChildren(findChildPageIdsAsString(ROOT_ID, all));
    }

    public NoteDto getNoteDto(String noteId, Long categoryId) {
        return ROOT_ID.equals(noteId)
                ? getRootNoteDto(categoryId)
                : getLeafNoteDto(noteId);
    }

    private NoteDto getLeafNoteDto(String noteId) {
        Note note = repository.findById(Long.parseLong(noteId)).orElseThrow();
        List<String> childIds = findChildPageIdsAsString(noteId, note.getCategoryId());
        return mapper.toNoteDTO(note).withChildren(childIds);
    }

    public List<String> findChildPageIdsAsString(String noteId, Long categoryId) {
        return findChildPageIds(noteId, categoryId)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> findChildPageIdsAsString(String noteId, List<Note> all) {
        return findChildPageIds(noteId, all)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<Long> findChildPageIds(String noteId, Long categoryId) {
        List<Note> all = repository.findAllByParentIdAndCategoryId(noteId, categoryId);
        return findChildPageIds(noteId, all);
    }

    public List<Long> findChildPageIds(String noteId, List<Note> all) {
        return all.stream()
                .filter(it -> noteId.equals(it.getParentId()))
                .sorted(Comparator.comparing(Note::getSortIdx))
                .map(Note::getId)
                .collect(Collectors.toList());
    }
}
