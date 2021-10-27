package lv.forfun.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

import javax.persistence.*;

@With
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column Long id;

    @Column String title;
    @Column String content;

    @Column Long parentId;
    @Column Long sortIdx;

    @Column Long categoryId;

    public String getIdAsString() {
        return id.toString();
    }
}
