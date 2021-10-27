package lv.forfun.demo.api.notes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

import static lv.forfun.demo.Constants.ROOT_ID;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoteDto {

    String id;
    String title;
    String content;
    String parentId;
    List<String> children;

    @JsonIgnore
    public Long getIdAsLong() {
        return Long.parseLong(id);
    }

    public static NoteDto root() {
        return new NoteDto().setId(ROOT_ID);
    }
}
