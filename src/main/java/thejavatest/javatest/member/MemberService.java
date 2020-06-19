package thejavatest.javatest.member;

import org.springframework.stereotype.Service;
import thejavatest.javatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
