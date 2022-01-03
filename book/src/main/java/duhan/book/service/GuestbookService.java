package duhan.book.service;

import duhan.book.dto.GuestbookDTO;
import duhan.book.dto.PageRequestDTO;
import duhan.book.dto.PageResultDTO;
import duhan.book.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    GuestbookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        return Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default GuestbookDTO entityToDto(Guestbook entity) {
        return GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .createDate(entity.getCreateDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}
