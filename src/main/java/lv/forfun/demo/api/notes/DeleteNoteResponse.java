package lv.forfun.demo.api.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;
import lv.forfun.demo.api.notes.NoteDto;

import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeleteNoteResponse {
    private NoteDto note;
    private List<String> deletedIds;
}
