/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;

import java.util.Scanner;

/**
 *
 * @author AliNa
 */
public class RoundRobin {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the number of teams: ");
        double index = scanner.nextDouble();
        
        ////if(index % 2 == 0){ 
        double calculated = (index / 2) * (index - 1);
        System.out.println("The number of matches would be " + calculated);
        //}
        //else {
        //    int calculated = (index - 1) / 2;
        //System.out.println("The number of matches would be " + calculated);
        }
    //}
    
}
