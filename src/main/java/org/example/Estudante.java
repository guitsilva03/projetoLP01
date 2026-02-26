package org.example;

import java.util.ArrayList;
import java.util.List;

class Student {
    private long numMec;
    private String nome;
    private List<Student> estudanteParceiros = new ArrayList<>();

    public Student(long numMec, String nome){
        this.numMec = numMec;
        this.nome = nome;
    }

    public List<Student> getEstudanteParceiros() {
        return estudanteParceiros;
    }

    public static boolean isEqual(Student estudanteA, Student estudanteB){
        return estudanteA.numMec == estudanteB.numMec;
    }

    public void addToParceiros(Student estudanteA){
        estudanteParceiros.add(estudanteA);
    }
}



class Group{
    private Student estudanteA;
    private Student estudanteB;

    private Group(Student estudanteA, Student estudanteB){
        this.estudanteA = estudanteA;
        this.estudanteB = estudanteB;
        estudanteA.addToParceiros(estudanteB);
        estudanteB.addToParceiros(estudanteA);
    }
    public static boolean isGroupValid(Student estudanteA, Student estudanteB){
        for (Student estudanteAnterior : estudanteA.getEstudanteParceiros() ){
            if(Student.isEqual(estudanteAnterior, estudanteB)){
                return false;
            }
        }
        return true;
    }
    public static Group criarGrupo(Student estudanteA, Student estudanteB){
        if(isGroupValid(estudanteA, estudanteB)){
            return new Group(estudanteA, estudanteB);
        }
        return null;
    }
}
