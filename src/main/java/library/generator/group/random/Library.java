package library.generator.group.random;

import lp.trabalho1.*;

public class Library {
    private IODataClass ioDataClass = new IODataClass();

    private GroupInfo[] groupInfo;
    private String[] groupsasString;
    private StudentInfo[] studentUC;
    private String[] studentUCasString;

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
        studentUCasString = ioDataClass.loadStudentUCasString(filepath);
    }

    public void outputGroups(String filepath){
        ioDataClass.outputGroups(filepath, studentGroups);
    }

    public void generateGroups(){

    }

}
