package duhan.board.service;

import duhan.board.dto.BoardDTO;
import duhan.board.dto.PageRequestDTO;
import duhan.board.dto.PageResultDTO;
import duhan.board.entity.Board;
import duhan.board.entity.Member;
import duhan.board.repository.BoardRepository;
import duhan.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info("dto={}", dto);
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO={}", pageRequestDTO);
        Function<Object[], BoardDTO> fn = (en ->
                entityToDto((Board) en[0],
                        (Member) en[1],
                        (Long) en[2]));
//        Page<Object[]> result = boardRepository.getBoardWithReplyCount(
//                pageRequestDTO.getPageable(Sort.by("bno")
//                        .descending()) );
        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional // 두개에 repository를 한번에 이용하는 것이기 때문에 Transactional 추가
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBno()).orElseThrow(() -> new IllegalArgumentException());
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getTitle());
        boardRepository.save(board);
    }
}
