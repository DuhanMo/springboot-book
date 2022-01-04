package duhan.board.repository;

import duhan.board.entity.Board;
import duhan.board.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void insertBoard() {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    Member member = Member.builder()
                            .email("user" + i + "@test.com")
                            .build();

                    Board board = Board.builder()
                            .title("Title..." + i)
                            .content("Content..." + i)
                            .writer(member)
                            .build();
                    boardRepository.save(board);
                });
    }

    @Test
    @Transactional
    @DisplayName("ManyToOne 기본 Eager 로딩 left-join")
    void testRead() {
        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.get();
        System.out.println("board = " + board);
        System.out.println("writer = " + board.getWriter());
    }

    @Test
    @DisplayName("연관관계가 있는 엔티티끼리의 조인 테스트")
    void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;
        System.out.println("-----------------");
        System.out.println("result = " + result);
        System.out.println("arr = " + Arrays.toString(arr));
    }

    @Test
    @DisplayName("연관관계가 없는 엔티티끼리의 조인 테스트")
    void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for (Object[] objects : result) {
            System.out.println("objects = " + Arrays.toString(objects));
        }
    }

    @Test
    @DisplayName("페이징을 위한 jpql 테스트")
    void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    @DisplayName("조회화면에서 필요한 JPQL 테스트")
    void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    @DisplayName("queryDSL custom repository 테스트")
    void testSearch1() {
        boardRepository.search1();
    }

    @Test
    void testSearchPage() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
        Page<Object[]> result = boardRepository.searchPage("tc", "1", pageable);

    }
}