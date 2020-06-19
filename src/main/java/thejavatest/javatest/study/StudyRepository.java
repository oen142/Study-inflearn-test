package thejavatest.javatest.study;

import org.springframework.data.jpa.repository.JpaRepository;
import thejavatest.javatest.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
