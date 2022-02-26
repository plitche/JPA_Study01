package Step29.basicType;

public class ValueMain {

    public static void main(String[] args) {

        int a = 10;
        int b = a;

        a = 20;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // int, double 같은 기본 타입은 절대 공유X
        // 기본 타입은 항상 값을 복사함
        // Integer같은 래퍼 클래스나 String 같은 특수한 클래스는 공유 가능한 객체이지만 변경X

        Integer c = new Integer(10);
        Integer d = a;

        a.setValue(20); // 이렇게 레퍼 클래스 값을 변경하면 둘다 20으로 변경된다.

        System.out.println("c = " + c);
        System.out.println("d = " + d);
    }

}
