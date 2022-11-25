package etc.groovy;

import etc.groovy.variable.SetVariableTest;

import static bin.apply.Repository.repository;

public class LoadJarFileTest {
    public static void main(String[] args) {
//        SetVariableTest.getInstance().start(repository, "");
        String line = "14 asdf";
        System.out.println(line.substring(line.indexOf(' ') + 1));
    }

    public LoadJarFileTest(String fileName) {

    }
}
