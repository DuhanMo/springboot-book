package duhan.book.service;

import duhan.book.dto.GuestbookDTO;
import duhan.book.dto.PageRequestDTO;
import duhan.book.dto.PageResultDTO;
import duhan.book.entity.Guestbook;
import duhan.book.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("dto={}",dto);
        Guestbook entity = dtoToEntity(dto);
        log.info("entity={}", entity);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno"));
        Page<Guestbook> result = repository.findAll(pageable);
        Function<Guestbook, GuestbookDTO> fn = (this::entityToDto);
        return new PageResultDTO<>(result, fn);
    }

}
