package lv.forfun.demo.features.notes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.forfun.demo.api.categories.MoveNoteRequest;
import lv.forfun.demo.api.notes.PositionDto;
import lv.forfun.demo.domain.NoteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoveNoteHandler {

    private final NoteRepository repository;

    @Transactional
    public void execute(Long categoryId, MoveNoteRequest moveNoteRequest) {
        final PositionDto from = moveNoteRequest.getSource();
        final PositionDto to = moveNoteRequest.getDestination();
        log.info("Moving note. from:[{},{}], to:[{}, {}]",
                from.getIndex(), from.getParentId(),
                to.getIndex(), to.getParentId());

        final Long noteId = findNoteIdIfExists(categoryId, from);
        ensureValidMovementDestination(to);
        repository.shiftNotePositionsUp(categoryId, from.getInternalParentId(), from.getIndex());
        repository.shiftNotePositionsDown(categoryId, to.getInternalParentId(), to.getIndex());
        repository.updateParentIdAndSortIdx(noteId, to.getInternalParentId(), to.getIndex());
    }

    private void ensureValidMovementDestination(PositionDto to) {
        long maxSortIdx = repository.findMaxSortIdxByParentId(to.getInternalParentId()).orElse(0L) + 1;
        if (to.getIndex() > maxSortIdx) {
            throw new IllegalArgumentException("Could not move note. SortIdx exceeds max available." +
                    " sortIdx:["+ to.getIndex() +"], maxAvailable:["+ maxSortIdx +"], parentId:["+ to.getParentId() +"]");
        }
    }

    private Long findNoteIdIfExists(Long categoryId, PositionDto from) {
        final Long parentId = from.getInternalParentId();
        final Long sortIdx = from.getIndex();
        return repository.findNoteId(categoryId, parentId, sortIdx)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Could not find note." +
                        " categoryId:[" + categoryId + "]," +
                        " parentId:[" + parentId + "]," +
                        " sortIdx:[" + sortIdx + "]"));
    }
}
