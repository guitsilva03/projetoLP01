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
        for(StudentInfo si : library.getStudentUC()){
            System.out.println(si.toString());
        }
        library.generateGroups();
        for(GroupInfo si : library.getGroupInfo()){
            System.out.println(si.toString());
        }
    }
}
