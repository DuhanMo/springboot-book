package duhan.book.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import duhan.book.entity.Guestbook;
import duhan.book.entity.QGuestbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    @DisplayName("엔티티업데이트 테스트")
    void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle("update title");
            guestbook.changeContent("update content");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    @DisplayName("단일항목 검색 테스트")
    void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));
        QGuestbook qGuestbook = QGuestbook.guestbook; // 동적으로 처리하기 위한 QClass
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); // where문에 들어가는 조건들을 넣어주는 컨테이너라고 생각
        BooleanExpression expression = qGuestbook.title.contains(keyword); // 원하는 조건을 필드값과 결합해서 생성
        builder.and(expression); // 만들어진 조건을 where문에 and나 or같은 키워드와 결합
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); // QuerydslPredicateExecutor 인터페이스 이용
        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("다중항목 검색 테스트")
    void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); // QuerydslPredicateExecutor 인터페이스 이용
        result.stream().forEach(System.out::println);
    }
}