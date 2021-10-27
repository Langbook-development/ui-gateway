package lv.forfun.demo.features.notes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.domain.NoteRepository;
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

    public Note findById(Long noteId) {
        return repository.findById(noteId).orElseThrow();
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
