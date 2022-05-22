package run;

import sample.HelloWorld;

public class RunTransformed {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clz = Class.forName("sample.HelloWorld");
        HelloWorld helloWorld = (HelloWorld) clz.newInstance();
        helloWorld.test();
    }
}
