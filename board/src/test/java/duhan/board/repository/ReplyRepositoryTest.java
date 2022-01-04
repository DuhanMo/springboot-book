package duhan.board.repository;

import duhan.board.entity.Board;
import duhan.board.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void insertReply() {
        IntStream.rangeClosed(1, 300)
                .forEach(i -> {
                    long bno = (long)(Math.random() * 100) + 2;

                    Board board = Board.builder()
                            .bno(bno)
                            .build();

                    Reply reply = Reply.builder()
                            .text("Reply....."+i)
                            .board(board)
                            .replyer("guest")
                            .build();

                    replyRepository.save(reply);
                });
    }

    @Test
    @DisplayName("Eager Type left join 2번")
    void readReplyTest1() {
        Reply reply = replyRepository.findById(100L).orElseThrow(() -> new IllegalArgumentException());
        System.out.println("reply = " + reply);
        System.out.println("reply.getBoard() = " + reply.getBoard());
    }

    @Test
    void testListByBoard() {
        List<Reply> replies = replyRepository.findByBoardOrderByRno(Board.builder().bno(50L).build());
        replies.forEach(System.out::println);
    }
}