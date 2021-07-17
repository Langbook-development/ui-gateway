package lv.forfun.demo.features.notes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.api.notes.DeleteNoteResponse;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.domain.NoteRepository;
import lv.forfun.demo.features.Mapper;
import lv.forfun.demo.features.notes.services.NoteService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static lv.forfun.demo.Constants.ROOT_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteNoteHandler {

    private final NoteService service;

    private final NoteRepository repository;
    private final Mapper mapper;

    @Transactional
    public DeleteNoteResponse execute(Long id, Long categoryId) {
        List<Note> all = repository.findAllByParentIdAndCategoryId(ROOT_ID, categoryId);
        Note note = findById(id, all);
        List<String> idsToDelete = getTreeIds(id, all);
        repository.deleteNotesWithIds(idsToDelete);
        NoteDto noteDto = mapper.toNoteDTO(note);
        return new DeleteNoteResponse()
                .setDeletedIds(idsToDelete)
                .setNote(noteDto);
    }

    public Note findById(Long id, List<Note> all) {
        return all.stream()
                .filter(it -> Objects.equals(id, it.getId()))
                .findFirst()
                .orElseThrow();
    }

    public List<String> getTreeIds(Long id, List<Note> all) {
        List<String> treeIds = new ArrayList<>();
        Stack<String> idsToFetch = new Stack<>();
        idsToFetch.add(id.toString());
        treeIds.add(id.toString());
        while (!idsToFetch.isEmpty()) {
            String idToFetch = idsToFetch.pop();
            List<String> childIds = service.findChildPageIds(idToFetch, all);
            treeIds.addAll(childIds);
            childIds.forEach(idsToFetch::push);
        }
        return treeIds;
    }
}
