package library.generator.group.random;

import lp.trabalho1.*;

import java.lang.classfile.constantpool.IntegerEntry;

public class Library {
    private IODataClass ioDataClass = new IODataClass();

    private GroupInfo[] groupInfo;
    private String[] groupsasString;
    private StudentInfo[] studentUC;
    // private String[] studentUCasString;

    private String[] studentGroups;

    public Library(){
    }

    public void loadGroups(String filepath){
        groupInfo = ioDataClass.loadGroups(filepath);
    }

    public void loadGroupsasString(String filepath){
        groupsasString = ioDataClass.loadGroupsasString(filepath);
    }

    public void loadStudentUC(String filepath){
        studentUC = ioDataClass.loadStudentUC(filepath);
    }

    public void loadStudentUCasString(String filepath){
        String[] studentUCasString = ioDataClass.loadStudentUCasString(filepath);
        studentUC = new StudentInfo[studentUCasString.length];

        for(int i = 0; i < studentUC.length; i++){
            String[] studentRow = studentUCasString[i].split(",");
            int studentId = Integer.parseInt(studentRow[0]);
            studentUC[i] = new StudentInfo(studentId, studentRow[1]);
        }

        //Definir tamanho do array do GroupInfo baseado no número de alunos
        int numGrupos = (studentUC.length % 2 == 0) ? studentUC.length/2 : studentUC.length/2 + 1;
        groupInfo = new GroupInfo[numGrupos];

    }

    public StudentInfo[] getStudentUC(){
        return studentUC;
    }

    public GroupInfo[] getGroupInfo(){
        return groupInfo;
    }

    public void outputGroups(String filepath){
        ioDataClass.outputGroups(filepath, studentGroups);
    }

    public void generateGroups(){
        for(int i = 0; i < groupInfo.length; i++){
            groupInfo[i] = new GroupInfo();
            groupInfo[i].setGroupID(i + 1);
        }
        boolean[] validPosition = validPosition();

        for(int i = 0; i<groupInfo.length; i++){
            int student1 = selectStudent();
            int student2 = selectStudent();
            while (!validPosition[student1]){
                student1 = nextStudent(student1);
            }
            while (!validPosition[student2]){
                student2 = nextStudent(student2);
            }
            groupInfo[i].setSt1(studentUC[student1]);
            groupInfo[i].setSt2(studentUC[student2]);

        }
    }

    private int nextStudent(int currentStudent){
        if(currentStudent == groupInfo.length - 1){
            return 0;
        }
        return currentStudent + 1;
    }

    private int selectStudent(){
        return (int) (Math.random() * studentUC.length);
    }

    public boolean[] validPosition(){
        boolean[] validPosition = new boolean[studentUC.length];
        for(int i = 0; i < validPosition.length; i++){
            validPosition[i] = true;
        }
        return validPosition;
    }
}
