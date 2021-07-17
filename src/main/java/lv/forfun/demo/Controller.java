package lv.forfun.demo;

import lombok.RequiredArgsConstructor;
import lv.forfun.demo.api.categories.MoveNoteRequest;
import lv.forfun.demo.api.notes.NoteDto;
import lv.forfun.demo.api.categories.CategoriesResponse;
import lv.forfun.demo.api.notes.DeleteNoteResponse;
import lv.forfun.demo.api.notes.NotesResponse;
import lv.forfun.demo.api.notes.UpsertNoteResponse;
import lv.forfun.demo.features.categories.CategoryService;
import lv.forfun.demo.features.notes.SelectNoteHandler;
import lv.forfun.demo.features.notes.DeleteNoteHandler;
import lv.forfun.demo.features.notes.MoveNoteHandler;
import lv.forfun.demo.features.notes.UpsertNoteHandler;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class Controller {

    private final UpsertNoteHandler upsertNoteHandler;
    private final DeleteNoteHandler deleteNoteHandler;
    private final MoveNoteHandler moveNoteHandler;
    private final SelectNoteHandler selectNoteHandler;
    private final CategoryService categoryService;

    @GetMapping("/categories")
    CategoriesResponse getCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/categories/{categoryId}/notes/")
    NotesResponse getNotes(@PathVariable("categoryId") Long categoryId) {
        return selectNoteHandler.execute(categoryId);
    }

    @DeleteMapping("/categories/{categoryId}/notes/{noteId}")
    DeleteNoteResponse deleteNotes(@PathVariable("categoryId") Long categoryId,
                                   @PathVariable("noteId") Long noteId) {
        return deleteNoteHandler.execute(noteId, categoryId);
    }

    @PutMapping("/categories/{categoryId}/notes")
    UpsertNoteResponse upsertNote(@PathVariable("categoryId") Long categoryId,
                                  @RequestBody NoteDto noteDTO) {
        return upsertNoteHandler.execute(categoryId, noteDTO);
    }

    @PutMapping("/categories/{categoryId}/notes/move")
    MoveNoteRequest moveNote(@PathVariable("categoryId") Long categoryId,
                               @RequestBody MoveNoteRequest moveNoteRequest) {
        moveNoteHandler.execute(categoryId, moveNoteRequest);
        return moveNoteRequest;
    }
}
