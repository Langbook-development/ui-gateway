package lv.forfun.demo.features.notes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.api.notes.NotesResponse;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.domain.NoteRepository;
import lv.forfun.demo.features.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelectNoteHandler {

    private final NoteMapper mapper;
    private final NoteRepository repository;

    public NotesResponse execute(Long categoryId) {
        log.info("Requesting notes. categoryId:[{}]", categoryId);
        List<Note> all = repository.findAllByCategoryId(categoryId);
        List<NoteDto> noteDTOs = all.stream()
                .map(mapper::toNoteDTO)
                .map(enrichByChildIds(all))
                .collect(Collectors.toList());
        noteDTOs.add(mapper.getRootNoteDto(all));
        return new NotesResponse()
                .setNotes(noteDTOs);
    }

    private Function<NoteDto, NoteDto> enrichByChildIds(List<Note> all) {
        return it -> mapper.enrichByChildIds(it, all);
    }
}
