package lv.forfun.demo.features;

import lombok.RequiredArgsConstructor;
import lv.forfun.demo.domain.Note;
import lv.forfun.demo.api.notes.NoteDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mapper {

    private final MapperFacade mapper;

    public NoteDto toNoteDTO(Note it) {
        return mapper.map(it, NoteDto.class)
                .withParentId(it.getParentId() == null ? "root" : it.getParentId().toString());
    }

    public Note fromNoteDTO(NoteDto it) {
        return mapper.map(it, Note.class);
    }
}
