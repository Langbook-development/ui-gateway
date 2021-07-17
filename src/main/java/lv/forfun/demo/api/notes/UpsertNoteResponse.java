package lv.forfun.demo.api.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;
import lv.forfun.demo.api.notes.NoteDto;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpsertNoteResponse {
    private NoteDto note;
    private NoteDto parentNote;
}
