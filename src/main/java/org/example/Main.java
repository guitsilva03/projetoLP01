package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import library.generator.group.random.Library;
import lp.trabalho1.GroupInfo;
import lp.trabalho1.StudentInfo;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Library library = new Library();
        boolean isRunning = true;

        while(isRunning){
            printMenu();
            String input = scanner.nextLine();
            switch(input){
                case "0" : isRunning = false; break;
                case "1" : importarEstudantes(library); break;
                case "2" : printEstudantes(library); break;
                case "3" : gerarGrupos(library); break;
                case "4" : printGrupos(library); break;
                case "5" : salvarGrupos(library); break;
                case "6" : carregarHistorico(library); break;
                case "7" : printHistorico(library); break;
                default:
                    System.out.println("Input Invalid");
            }
        }


    }

    private static void printMenu(){
        System.out.print("""
                Menu:
                0 - Exit
                1 - Importar Estudantes
                2 - Print Estudantes
                3 - Gerar Grupos
                4 - Print Grupos
                5 - Salvar Grupos
                6 - Carregar Historico
                7 - Print Historico
                Input: """);
    }

    private static void printEstudantes(Library library){
        StudentInfo[] arr = library.getStudentUC();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + " -> " + arr[i]);
        }
    }

    private static void importarEstudantes(Library library){
        //System.out.print("Input caminho: ");
        //String path = scanner.nextLine();
        String path = "src/files/loadStudentUCasString.txt";
        library.loadStudentUCasString(path);
    }

    private static void salvarGrupos(Library library){
        String path = "src/files/GruposGerados";
        File pasta = new File(path);

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File[] files = pasta.listFiles();

        int numberOfGrupos = 0;

        if (files != null) {
            numberOfGrupos  = files.length;
        }

        String filePath = path + "/grupo_gerado_%d".formatted(numberOfGrupos+1) + ".txt";
        library.outputGroups(filePath);


    }

    private static void gerarGrupos(Library library){
        library.generateGroups();
    }

    private static void printGrupos(Library library){
        for (GroupInfo g : library.getGroupInfo()) {
            if (g.getSt2() == null) {
                System.out.println(g.getGroupID() + ", " + g.getSt1());
            } else {
                System.out.println(g.getGroupID() + ", " + g.getSt1() + ", " + g.getSt2());
            }
        }
    }

    private static void carregarHistorico(Library library){
        String path = "src/files/GruposGerados";
        File pasta = new File(path);

        if (!pasta.exists()) {
            return;
        }

        File[] files = pasta.listFiles();

        if (files != null) {
            for(int i = 0; i < files.length; i++){
                String filePath = path + "/grupo_gerado_%d".formatted(i+1) + ".txt";
                library.loadHistorico(filePath);
            }
        }
    }

    public static void printHistorico(Library library){
        for(GroupInfo g : library.getHistorico()){
            if (g.getSt2() == null) {
                System.out.println(g.getGroupID() + ", " + g.getSt1());
            } else {
                System.out.println(g.getGroupID() + ", " + g.getSt1() + ", " + g.getSt2());
            }
        }

    }
}
