package library.generator.group.random;

import lp.trabalho1.*;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private IODataClass ioDataClass;

    private List<GroupInfo> groupInfo;
    private List<GroupInfo> groupHistoricos;
    private List<StudentInfo> studentUC;
    private List<StudentInfo> copystudentUC;

    public Library(){
        ioDataClass = new IODataClass();
        groupHistoricos = new ArrayList<GroupInfo>();
        groupInfo = new ArrayList<GroupInfo>();
        studentUC = new ArrayList<StudentInfo>();
        copystudentUC = new ArrayList<StudentInfo>();
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

        for(int i = 0; i < studentUCasString.length; i++){
            String[] studentRow = studentUCasString[i].split(",");
            int studentId = Integer.parseInt(studentRow[0]);
            studentUC.add(new StudentInfo(studentId, studentRow[1]));
        }

        copystudentUC = new ArrayList<StudentInfo>(studentUC);
    }

    public void clearGrupos(){
        groupInfo.clear();
    }

    public List<GroupInfo> getHistorico(){
        return groupHistoricos;
    }

    public List<StudentInfo> getStudentUC(){
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
        groupInfo.clear();
    }

    public void createGroup(int stdAID, String stdAname, int stdBID, String stdBname){
        GroupInfo group = new GroupInfo();
        group.setGroupID(groupInfo.size() + 1);

        StudentInfo studentA = new StudentInfo(stdAID, stdAname);
        group.setSt1(studentA);

        StudentInfo studentB = new StudentInfo(stdBID, stdBname);
        group.setSt2(studentB);

        for(int i = 0; i < copystudentUC.size(); i++){
            if(copystudentUC.get(i).getStudentID() == studentA.getStudentID()
                || copystudentUC.get(i).getStudentID() == studentB.getStudentID()){
                copystudentUC.remove(i);
            }
        }

        groupInfo.add(group);
    }

    public void generateGroups(){

        while(!copystudentUC.isEmpty()) {
            GroupInfo group = new GroupInfo();
            group.setGroupID(groupInfo.size() + 1);

            int position = selectStudent(copystudentUC.size());
            int studentAID = copystudentUC.get(position).getStudentID();

            group.setSt1(copystudentUC.get(position));
            copystudentUC.remove(position);

            if(copystudentUC.isEmpty()) {
                groupInfo.add(group);
                break;
            }

            position = selectStudent(copystudentUC.size());
            int studentBID = copystudentUC.get(position).getStudentID();

            int startingPosition = position;

            //comparar no historico
            while(noHistorico(studentAID, studentBID)){

                if(position + 1 == copystudentUC.size()){
                    position = 0;
                    studentBID = copystudentUC.get(position).getStudentID();
                }else{
                    position++;
                    studentBID = copystudentUC.get(position).getStudentID();
                }

                if(position == startingPosition){
                    break;
                }
            }

            if(noHistorico(studentAID, studentBID)) {
                groupInfo.add(group);
                continue;
            }

            group.setSt2(copystudentUC.get(position));
            copystudentUC.remove(position);

            groupInfo.add(group);
        }

        copystudentUC = new ArrayList<StudentInfo>(studentUC);
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
