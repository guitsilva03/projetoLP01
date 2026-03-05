package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import library.generator.group.random.Library;
import lp.trabalho1.GroupInfo;
import lp.trabalho1.StudentInfo;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.loadStudentUCasString("src/files/loadStudentUCasString.txt");
        StudentInfo[] arr = library.getStudentUC();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + " -> " + arr[i]);
        }
        library.generateGroups();
        for (GroupInfo g : library.getGroupInfo()) {
            if (g.getSt2() == null) {
                System.out.println(g.getGroupID() + ", " + g.getSt1());
            } else {
                System.out.println(g.getGroupID() + ", " + g.getSt1() + ", " + g.getSt2());
            }
        }
    }
}
