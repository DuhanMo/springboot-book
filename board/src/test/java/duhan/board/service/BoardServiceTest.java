package duhan.board.service;

import duhan.board.dto.BoardDTO;
import duhan.board.dto.PageRequestDTO;
import duhan.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void register() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test..")
                .content("Content..")
                .writerEmail("user55@test.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    void getList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println("boardDTO = " + boardDTO);
        }
    }

    @Test
    void testGet() {
        BoardDTO boardDTO = boardService.get(100L);
        System.out.println("boardDTO = " + boardDTO);
    }

    @Test
    void testRemove() {
        Long bno = 2L;
        boardService.removeWithReplies(bno);
    }

    @Test
    void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3L)
                .title("modify test")
                .content("modify test")
                .build();
        boardService.modify(boardDTO);
    }
}