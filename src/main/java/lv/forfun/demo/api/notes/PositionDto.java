package lv.forfun.demo.api.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

import static lv.forfun.demo.Constants.ROOT_ID;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PositionDto {
    private String parentId;
    private Long index;
}
