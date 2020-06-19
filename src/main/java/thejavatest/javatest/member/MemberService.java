package thejavatest.javatest.member;

import org.springframework.stereotype.Service;
import thejavatest.javatest.domain.Member;
import thejavatest.javatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study save);
}
