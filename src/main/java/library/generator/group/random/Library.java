package library.generator.group.random;

import lp.trabalho1.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private IODataClass ioDataClass = new IODataClass();

    private GroupInfo[] groupInfo;
    private String[] groupsasString;
    private StudentInfo[] studentUC;

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
        String[] studentGroups = new String[groupInfo.length];

        for(int i = 0; i < studentGroups.length; i++){
            if(groupInfo[i].getSt2() == null){
                studentGroups[i] = ("%d, %s").formatted(groupInfo[i].getGroupID(), groupInfo[i].getSt1());
            }else{
                studentGroups[i] = groupInfo[i].toString();
            }
        }

        ioDataClass.outputGroups(filepath, studentGroups);
    }

    public void generateGroups(){
        for(int i = 0; i < groupInfo.length; i++){
            groupInfo[i] = new GroupInfo();
            groupInfo[i].setGroupID(i + 1);
        }
        List<StudentInfo> studentInfoList = new ArrayList<>(Arrays.asList(studentUC));

        while(!studentInfoList.isEmpty()) {
            for (int  j = 0;  j < groupInfo.length; j++) {
                int position = selectStudent(studentInfoList.size());
                int studentAID = studentInfoList.get(position).getStudentID();

                groupInfo[j].setSt1(studentInfoList.get(position));
                studentInfoList.remove(position);

                if(studentInfoList.isEmpty()) {
                    break;
                }

                position = selectStudent(studentInfoList.size());
                int studentBID = studentInfoList.get(position).getStudentID();

                //acrestar

                groupInfo[j].setSt2(studentInfoList.get(position));
                studentInfoList.remove(position);
            }
        }
    }

    private int selectStudent(int max){
        return (int) (Math.random() * max);
    }

}
