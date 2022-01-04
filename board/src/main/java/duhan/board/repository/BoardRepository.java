package duhan.board.repository;

import duhan.board.entity.Board;
import duhan.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    // 엔티티 (Board) 내부에 연관관계가 있는 경우(writer)
    // 한개의 로우(object) 내에 Object[ ]로 나옴
    // left join 뒤에 on이 없음
    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bon);

    // 직접 관련이 없는 (Board는 Reply를 참조하고 있지 않다 현재) 상태
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bon);

    @Query(value = "select b, w, count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r " +
            "on r.board = b " +
            "group by b",
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // 목록 화면에 필요한 데이터 countQuery를 작성해준다 직접

    @Query("select b, w, count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r " +
            "on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno); // 실제 조회화면, 댓글은 Ajax를 통해 동적으로 가져온다
}
