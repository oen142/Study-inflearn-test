package thejavatest.javatest.study;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import thejavatest.javatest.domain.Member;
import thejavatest.javatest.domain.Study;
import thejavatest.javatest.member.MemberNotFoundException;
import thejavatest.javatest.member.MemberService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() throws MemberNotFoundException {

        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        memberService.validate(2L);

        Study study = new Study(10, "java");
        studyService.createNewStudy(1L, study);
        assertNotNull(studyService);

        when(memberService.findById(1L)).thenThrow(new RuntimeException());
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        when(memberService.findById(1L))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("kim", byId.get().getClass());
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));

        memberService.notify(study);

    }

    /*
     * Mock Stubbing
     * */


    @Test
    void verify() {


    }
}