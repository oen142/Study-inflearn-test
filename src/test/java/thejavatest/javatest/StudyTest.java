package thejavatest.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;
import thejavatest.javatest.domain.Study;
import thejavatest.javatest.study.StudyStatus;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//언더스코어를 빈 공백으로 채워주는 전략
@ExtendWith(FindSlowTestExtension.class)//선언적 등록방
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    //junit5이상부터는 클래스나 메소드에 public일 필요가 없다.
    @Test
    @Tag("fast")
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(-10);
        assertNotNull(study);
        //기대값 , 나오는 값

        assertAll(
                () ->
                        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
                    @Override
                    public String get() {
                        //람다를 쓰면 테스트를 실패하면 코드를 실행한다
                        return "스터디를 처음 ....";
                    }
                }),

                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다"));

        assertTrue(1 < 2);


        //첫번 째 테스트가 깨지면 뒤로 가지않는다.
    }


    @Test
    @Tag("slow")
//    @Disabled //실행하고 싶지 않을때
    void create_new_study_again() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(10));
        String message = exception.getMessage();
        assertEquals("limit ..", message);
        Study study = new Study(10);
        System.out.println("create1");

        assertTimeout(Duration.ofSeconds(100), () -> {
            new Study(10);
            Thread.sleep(1000);
        });
        /*즉시 끝내고 싶을때
         *코드블럭을 별도의 쓰레드를 실행하기때문에
         * TODO ThreadLocal 예상치 못한 결과가 발생할수도 있다.
         *  스프링 트랜잭션 처리같은경우에는 쓰레드에서 공유가 되질 않기때문에
         *  스프링이 만든 트랜잭션 처리가 되지 않을수가 있다.
         * 실제로는 트랜잭셔널한 건 롤백을 하는데
         * 만약 이경우 디비에 반영이 될수도 있다.
         *
         * */
        assertTimeoutPreemptively(Duration.ofSeconds(100), () -> {
            new Study(10);
            Thread.sleep(1000);
        });

        assertThat(study.getLimit()).isEqualTo(-10);
    }

    /*
     * 특정한 자바변수 환경변수 OS같은데서 실행해야 한다 할때 만드는코드
     *
     * */
    @Test
    void testOs() {

        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            //TODO
        });
        assumingThat("TEST".equalsIgnoreCase(test_env), () -> {
            //TODO
        });

    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre({JRE.JAVA_8})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void annoTest() {
        //TODO OS , Jre 어노테이션에 따라 달라짐
    }

    /*
     * 테스트를 그룹화 하는것이 Tag이다.
     *
     * */


    /*
     * 테스트 반복
     *
     *
     * */

    @RepeatedTest(value = 10, name = "{displayName} , {currentRepetition}/{totalRepetition}")
    @DisplayName("테스트를 반복한다.")
    void repeatedTest(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + "  / " + repetitionInfo.getTotalRepetitions());

    }


    @DisplayName("파라메터 테스트")
    @ParameterizedTest(name = "{index}{displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "더워지고", "있네요"})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    /*
     * 개수가 늘어나는 형식
     * */
    @DisplayName("파라메터 테스트")
    @ParameterizedTest(name = "{index}{displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    @EmptySource
    @NullSource
    @NullAndEmptySource
    void parameterizedAnotherTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "")
    @CsvSource({"10,'자바 스터디'", "20, 스프링"})
    void csvTest(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "")
    @CsvSource({"10,'자바 스터디'", "20, 스프링"})
    void csvArgTest(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "")
    @CsvSource({"10,'자바 스터디'", "20, 스프링"})
    void csvArgImplTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    /*
     * 반드시 스태틱 이너 클래스
     * 또는
     * 퍼블릭 클래스
     * */
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {

            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    /*
     * 테스트 인스턴스
     * 기본 전략은 테스트 메소드마다 생성한다.
     * this 를 할때마다 값이 다른걸 알수있다.
     * 테스트 간의 의존성을 없애기 위해서
     * Junit 5부터는 테스트 마다 다른게아니라 공유할수도 있다.
     * TestInstance(Lifecycle.PER_CLASS)
     * */
    int value = 1;

    @Test
    public void testInstance1() {
        System.out.println("value = " + value++);
    }

    @Test

    public void testInstance2() {

        System.out.println("value = " + value++);
    }
    /*
     * 정해진 테스트 순서는 있다.
     * 그 순서에 의존해서는 안된다.
     * 순서를 일부러 명확히 드러내지 않은 의도는
     * 제대로 작성된 테스트는 다른 단위 테스트랑 독립적이게 영향을 주면 안된다.
     * 경우에 따라서는 원하는 순서대로 테스트를 하고 싶다.
     * @TestMethodOrder(OrderAnnotation)
     *
     * @Order(int)
     * */

    //테스트가 모두 실행될때 딱 한번 private x default o return type x static

    /*
     * 테스트 인스턴스가 클래스 단위이면 static일 필요가 없다.
     * */


    /*
     * 확장 모델
     * Runwith , testRule MethodRule
     *
     * Extension
     * 확장팩 등록방법
     * 선언적인 등록 @TxtendWith
     * 프로그래밍 등록 @RegisterExtension
     * 자동 등록 자바 ServiceLoader
     *
     * 인스턴스를 만드는 방법을 커스텀 할수가 없다. 기본 디폴트 생성자로 생성하는거임
     * 쓰레드 홀드를 매번 바꾸고싶으면, 생성자에서 받아오는거다.
     * */

    /*
    * 이 방법은 기본적으로 꺼져있다.
    * 프로퍼티로 등록이 가능하지만,
    * 명시적으로 선언해주는것이 좋다.
    * */
    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    /*
    * 스프링 부트를 기본적으로 만드면
    * 빈티지 엔진이 모듈이 빠져있다.
    * 의존성을 빼주면
    * Junit4로 가능하다.
    * */

    @Test
    public void slowTest() throws InterruptedException {
        Thread.sleep(1005L);
    }
    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }


    @AfterAll
    static void AfterAll() {
        System.out.println("AfterAll");
    }

    //각각의 테스트가 실행될때마다
    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void AfterEach() {
        System.out.println("AfterEach");
    }


}