package library.generator.group.random;

import lp.trabalho1.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private IODataClass ioDataClass = new IODataClass();

    private List<GroupInfo> groupInfo;
    private List<GroupInfo> groupHistoricos;
    private StudentInfo[] studentUC;

    public Library(){
        groupHistoricos = new ArrayList<GroupInfo>();
        groupInfo = new ArrayList<GroupInfo>();
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
    }

    public List<GroupInfo> getHistorico(){
        return groupHistoricos;
    }

    public StudentInfo[] getStudentUC(){
        return studentUC;
    }

    public List<GroupInfo> getGroupInfo(){
        return groupInfo;
    }

    public void outputGroups(String filepath){
        String[] studentGroups = new String[groupInfo.size()];

        for(int i = 0; i < studentGroups.length; i++){
            if(groupInfo.get(i).getSt2() == null){
                studentGroups[i] = ("%d, %s").formatted(groupInfo.get(i).getGroupID(), groupInfo.get(i).getSt1());
            }else{
                studentGroups[i] = groupInfo.get(i).toString();
            }
        }

        //addiciona ao historico quando grava
        for(int i = 0; i  < studentGroups.length; i++){
            groupHistoricos.add(groupInfo.get(i));
        }


        ioDataClass.outputGroups(filepath, studentGroups);
    }

    public void generateGroups(){
        List<StudentInfo> studentInfoList = new ArrayList<>(Arrays.asList(studentUC));
        groupInfo.clear();

        for(int j = 0; !studentInfoList.isEmpty(); j++) {
            GroupInfo group = new GroupInfo();
            group.setGroupID(j + 1);

            int position = selectStudent(studentInfoList.size());
            int studentAID = studentInfoList.get(position).getStudentID();

            group.setSt1(studentInfoList.get(position));
            studentInfoList.remove(position);

            if(studentInfoList.isEmpty()) {
                groupInfo.add(group);
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

            if(noHistorico(studentAID, studentBID)) {
                groupInfo.add(group);
                continue;
            }

            group.setSt2(studentInfoList.get(position));
            studentInfoList.remove(position);

            groupInfo.add(group);

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
