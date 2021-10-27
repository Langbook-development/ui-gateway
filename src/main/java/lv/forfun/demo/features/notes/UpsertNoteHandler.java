package lv.forfun.demo.features.notes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.api.notes.UpsertNoteResponse;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.domain.NoteRepository;
import lv.forfun.demo.features.notes.services.NoteService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpsertNoteHandler {

    private final NoteService service;
    private final NoteRepository repository;

    public UpsertNoteResponse execute(Long categoryId, NoteDto noteDTO) {
        if (noteDTO.getId() == null) {
            return createNote(noteDTO, categoryId);
        }
        return updateNote(noteDTO);
    }

    private UpsertNoteResponse updateNote(NoteDto noteDTO) {
        Note note = repository.findById(noteDTO.getIdAsLong()).orElseThrow();
        note.setTitle(noteDTO.getTitle())
                .setContent(noteDTO.getContent());
        repository.save(note);
        return getUpsertNoteResponse(note);
    }

    private UpsertNoteResponse createNote(NoteDto noteDTO, Long categoryId) {
        Long sortIdx = repository.findMaxSortIdxByParentId(noteDTO.getInternalParentId())
                .map(idx -> idx + 1)
                .orElse(0L);
        Note note = new Note()
                .setTitle(noteDTO.getTitle())
                .setContent(noteDTO.getContent())
                .setCategoryId(categoryId)
                .setParentId(noteDTO.getInternalParentId())
                .setSortIdx(sortIdx);
        repository.save(note);
        return getUpsertNoteResponse(note);
    }

    private UpsertNoteResponse getUpsertNoteResponse(Note note) {
        Long categoryId = note.getCategoryId();
        NoteDto noteDto = service.getNoteDto(note.getId(), categoryId);
        NoteDto parentDto = service.getNoteDto(note.getParentId(), categoryId);
        return new UpsertNoteResponse()
                .setNote(noteDto)
                .setParentNote(parentDto);
    }
}
