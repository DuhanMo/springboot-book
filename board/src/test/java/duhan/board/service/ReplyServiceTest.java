package duhan.board.service;

import duhan.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void getListTest() {
        Long bno = 50L;
        List<ReplyDTO> list = replyService.getList(bno);
        list.forEach(System.out::println);
    }
}