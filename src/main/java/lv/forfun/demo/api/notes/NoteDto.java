package lv.forfun.demo.api.notes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
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

    @JsonIgnore
    public Long getInternalParentId() {
        return ROOT_ID.equals(parentId) ? null : Long.parseLong(parentId);
    }

    public static NoteDto root() {
        return new NoteDto().setId(ROOT_ID);
    }
}
