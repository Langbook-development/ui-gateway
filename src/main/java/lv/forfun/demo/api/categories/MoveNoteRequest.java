package lv.forfun.demo.api.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;
import lv.forfun.demo.api.notes.PositionDto;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MoveNoteRequest {
    private PositionDto source;
    private PositionDto destination;
}
