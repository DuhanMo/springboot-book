package duhan.board.repository;

import duhan.board.entity.Board;
import duhan.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying // JPQL을 이용해서 update, delete를 하기 위해서 선언
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);

    List<Reply> findByBoardOrderByRno(Board board);
}
