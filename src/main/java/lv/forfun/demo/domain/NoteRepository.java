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
    List<Note> findAll();

    @Modifying
    @Query("DELETE FROM Note n WHERE n.id IN :ids")
    void deleteNotesWithIds(@Param("ids") List<String> ids);

    List<Note> findAllByParentIdAndCategoryId(String parentId, Long categoryId);

    @Query("UPDATE Note n SET n.sortIdx = n.sortIdx + 1 " +
            "WHERE n.parentId = :parentId               " +
            "  AND n.categoryId =:categoryId            " +
            "  AND n.sortIdx >= :sortIdxFrom            "
    )
    @Modifying
    void shiftNotePositionsDown(@Param("categoryId") Long categoryId,
                                @Param("parentId") String parentId,
                                @Param("sortIdxFrom") Long sortIdxFrom);

    @Query("UPDATE Note n SET n.sortIdx = n.sortIdx - 1 " +
            "WHERE n.parentId = :parentId              " +
            "  AND n.categoryId =:categoryId           " +
            "  AND n.sortIdx >= :sortIdxFrom           "
    )
    @Modifying
    void shiftNotePositionsUp(@Param("categoryId") Long categoryId,
                              @Param("parentId") String parentId,
                              @Param("sortIdxFrom") Long sortIdxFrom);

    @Query("UPDATE Note n SET n.sortIdx = :sortIdx, n.parentId = :parentId WHERE n.id = :noteId")
    @Modifying
    void updateParentIdAndSortIdx(@Param("noteId") Long noteId,
                                  @Param("parentId") String parentId,
                                  @Param("sortIdx") Long sortIdx);

    @Query("SELECT n.id FROM Note n           " +
           " WHERE n.sortIdx = :sortIdx       " +
           "   AND n.parentId = :parentId     " +
           "   AND n.categoryId = :categoryId "
    )
    Optional<Long> findNoteId(@Param("categoryId") Long categoryId,
                              @Param("parentId") String parentId,
                              @Param("sortIdx") Long sortIdx);

    @Query("SELECT MAX(n.sortIdx) FROM Note n WHERE n.parentId = :parentId ")
    Optional<Long> findMaxSortIdxByParentId(@Param("parentId") String parentId);
}
