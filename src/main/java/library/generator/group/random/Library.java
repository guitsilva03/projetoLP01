package library.generator.group.random;

import lp.trabalho1.*;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private IODataClass ioDataClass = new IODataClass();

    private GroupInfo[] groupInfo;
    private List<GroupInfo> groupHistoricos;
    private StudentInfo[] studentUC;

    public Library(){
        groupHistoricos = new ArrayList<GroupInfo>();
    }

    public void loadHistorico(String filepath){
        String[] groupsasString = ioDataClass.loadGroupsasString(filepath);

        for(int i = 0; i < groupsasString.length; i++){
            GroupInfo grupo = new GroupInfo();
            String[] grupos = groupsasString[i].split(",");

            grupo.setGroupID(Integer.parseInt(grupos[0]));
            StudentInfo studentA = new StudentInfo(Integer.parseInt(grupos[1].trim()), grupos[2].trim());

            grupo.setSt1(studentA);

            if(grupos.length == 5){
                StudentInfo studentB = new StudentInfo(Integer.parseInt(grupos[3].trim()), grupos[4].trim());
                grupo.setSt2(studentB);
            }

            groupHistoricos.add(grupo);
        }

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

    public List<GroupInfo> getHistorico(){
        return groupHistoricos;
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

        //addiciona ao historico quando grava
        for(int i = 0; i  < studentGroups.length; i++){
            groupHistoricos.add(groupInfo[i]);
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

                int startingPosition = position;

                //comparar no historico
                while(noHistorico(studentAID, studentBID)){

                    if(position + 1 == studentInfoList.size()){
                        position = 0;
                        studentBID = studentInfoList.get(position).getStudentID();
                    }else{
                        position++;
                        studentBID = studentInfoList.get(position).getStudentID();
                    }

                    if(position == startingPosition){
                        break;
                    }
                }

                if(noHistorico(studentAID, studentBID))
                    continue;

                groupInfo[j].setSt2(studentInfoList.get(position));
                studentInfoList.remove(position);
            }
        }
    }

    private int selectStudent(int max){
        return (int) (Math.random() * max);
    }

    private boolean noHistorico(int studentIDA, int studentIDB) {
        for (GroupInfo g : groupHistoricos) {
            if (g.getSt2() == null) {
                continue;
            }

            if(g.getSt1().getStudentID() == studentIDA && g.getSt2().getStudentID() == studentIDB
                || g.getSt2().getStudentID() == studentIDA && g.getSt1().getStudentID() == studentIDB){
                return true;
            }
        }

        return false;
    }
}
