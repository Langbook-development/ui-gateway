package lv.forfun.demo.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    @Modifying
    @Query("DELETE FROM Note n WHERE n.id IN :ids")
    void deleteNotesWithIds(@Param("ids") List<Long> ids);

    List<Note> findAllByParentIdAndCategoryId(Long parentId, Long categoryId);

    List<Note> findAllByCategoryId(Long categoryId);

    @Query("UPDATE Note n SET n.sortIdx = n.sortIdx + 1        " +
            "WHERE n.categoryId =:categoryId                   " +
            "  AND n.sortIdx >= :sortIdxFrom                   " +
            "  AND (n.parentId = :parentId OR                  " +
            "      (n.parentId IS NULL AND :parentId IS NULL)) "
    )
    @Modifying
    void shiftNotePositionsDown(@Param("categoryId") Long categoryId,
                                @Param("parentId") Long parentId,
                                @Param("sortIdxFrom") Long sortIdxFrom);

    @Query("UPDATE Note n SET n.sortIdx = n.sortIdx - 1        " +
            "WHERE n.categoryId =:categoryId                   " +
            "  AND n.sortIdx >= :sortIdxFrom                   " +
            "  AND (n.parentId = :parentId OR                  " +
            "      (n.parentId IS NULL AND :parentId IS NULL)) "
    )
    @Modifying
    void shiftNotePositionsUp(@Param("categoryId") Long categoryId,
                              @Param("parentId") Long parentId,
                              @Param("sortIdxFrom") Long sortIdxFrom);

    @Query("UPDATE Note n SET n.sortIdx = :sortIdx, n.parentId = :parentId WHERE n.id = :noteId")
    @Modifying
    void updateParentIdAndSortIdx(@Param("noteId") Long noteId,
                                  @Param("parentId") Long parentId,
                                  @Param("sortIdx") Long sortIdx);

    @Query("SELECT n.id FROM Note n                            " +
           " WHERE n.sortIdx = :sortIdx                        " +
           "   AND n.categoryId = :categoryId                  " +
           "   AND (n.parentId = :parentId OR                  " +
           "       (n.parentId IS NULL AND :parentId IS NULL)) "
    )
    Optional<Long> findNoteId(@Param("categoryId") Long categoryId,
                              @Param("parentId") Long parentId,
                              @Param("sortIdx") Long sortIdx);

    @Query("SELECT MAX(n.sortIdx) FROM Note n                  " +
           " WHERE n.categoryId = :categoryId                  " +
            "  AND (n.parentId = :parentId OR                  " +
            "      (n.parentId IS NULL AND :parentId IS NULL)) "
    )
    Optional<Long> findMaxSortIdxByParentId(@Param("categoryId") Long categoryId,
                                            @Param("parentId") Long parentId);
}
