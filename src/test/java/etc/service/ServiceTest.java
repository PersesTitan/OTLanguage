package etc.service;

import bin.apply.Setting;
import work.StartWork;
import work.v3.StartWorkV3;

import java.util.ServiceLoader;

public class ServiceTest {
    public static void main(String[] args) {
        ServiceLoader<StartWorkV3> test = ServiceLoader.load(StartWorkV3.class);
        ServiceLoader<StartWork> servce = ServiceLoader.load(StartWork.class);
        System.out.println(servce);

        test.forEach(System.out::println);
        servce.forEach(System.out::println);
    }
}
