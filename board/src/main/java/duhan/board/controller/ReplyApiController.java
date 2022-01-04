package duhan.board.controller;

import duhan.board.dto.ReplyDTO;
import duhan.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("bno={}", bno);
        return ResponseEntity.ok(replyService.getList(bno));
    }

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        log.info("replyDTO={}", replyDTO);
        Long rno = replyService.register(replyDTO);
        return ResponseEntity.ok(rno);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("rno:" + rno);
        replyService.remove(rno);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        log.info("replyDTO={}", replyDTO);
        replyService.modify(replyDTO);
        return ResponseEntity.ok("success");
    }
}
