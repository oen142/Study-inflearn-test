package thejavatest.javatest.study;

import org.springframework.stereotype.Service;
import thejavatest.javatest.domain.Member;
import thejavatest.javatest.domain.Study;
import thejavatest.javatest.member.MemberNotFoundException;
import thejavatest.javatest.member.MemberService;

import java.util.Optional;

@Service
public class StudyService {

    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) throws MemberNotFoundException {
        Optional<Member> member = memberService.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("");
        }
        Study save = studyRepository.save(study);

        memberService.notify(save);
        return save;
    }
}
