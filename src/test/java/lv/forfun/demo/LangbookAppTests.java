package lv.forfun.demo;

import lv.forfun.demo.domain.NoteDTO;
import lv.forfun.demo.domain.Note;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LangbookAppTests {

    @Autowired
    MapperFacade mapperFacade;

    @Test
    void contextLoads() {
        NoteDTO source = new NoteDTO()
                .setId(null)
                .setTitle("Title")
                .setContent("Content")
                .setSortId(1L)
                .setParentId(null);

        Note dest = mapperFacade.map(source, Note.class);
        NoteDTO after = mapperFacade.map(dest, NoteDTO.class);

        System.out.println(after);
    }

}
