package thejavatest.javatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//언더스코어를 빈 공백으로 채워주는 전략
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    //junit5이상부터는 클래스나 메소드에 public일 필요가 없다.
    @Test
    @DisplayName("스터디 만들기")
    void create_new_study(){
        Study study = new Study();
        assertNotNull(study);
    }


    @Test
//    @Disabled //실행하고 싶지 않을때
    void create_new_study_again(){
        System.out.println("create1");
    }

    //테스트가 모두 실행될때 딱 한번 private x default o return type x static
    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }


    @AfterAll
    static void AfterAll(){
        System.out.println("AfterAll");
    }

    //각각의 테스트가 실행될때마다
    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach

    void AfterEach(){
        System.out.println("AfterEach");
    }

}