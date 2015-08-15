
package br.senac.tads.pi3.lucasbarbosa.exercicio1;

import java.util.Scanner;

public class Exec01 {
   
    public static void main (String[]args){
       Scanner sc = new Scanner(System.in);
        String Nome;
        String Telefone;
        String Email;
        String Data;
        
        System.out.println("Digite o Nome");
        Nome = sc.nextLine();
        
        System.out.println("Digite sua data de nascimento");
        Data = sc.nextLine();
        
        System.out.println("Digite o Telefone");
        Telefone = sc.nextLine();
        
        System.out.println("Digite seu E-mail");
        Email = sc.nextLine();
        
        System.out.println("Seu nome = " + Nome);
        System.out.println("Sua data é = " + Data);
        System.out.println("Seu Telefone é = " + Telefone);
        System.out.println("Seu email é = " + Email);
    }
    
    
}
