package hello.core.singleton;

public class SingletonService {
    // 자기 자신 인스턴스를 생성해서 instance라는 변수(상수)에 참조를 넣어 놓음
    private static final SingletonService instance = new SingletonService();

    // 인스턴스를 조회(사용)하는 메서드 -> 항상 같은 인스턴스를 반환
    public static SingletonService getInstance() {
        return instance;
    }

    // private으로 생성자를 정의 -> 외부에서 new 키워드로 인스턴스 생성을 못하도록
    private SingletonService() {}
    // -> private 생성자라는 것만 봐도, 싱글톤이라는 것을 알 수 있음

}
