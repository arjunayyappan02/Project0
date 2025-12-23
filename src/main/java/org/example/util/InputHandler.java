package org.example.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static Integer getIntInput(String prompt){
        while(true){
            System.out.println(prompt);
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
    }

    public static String getStringInput(String prompt){
        while (true) {
            System.out.println(prompt);
            try{
                return scanner.nextLine();
            }catch(RuntimeException e){
                System.out.println("Invalid Input. Please enter a String.");
            }
        }
    }

    public static LocalDate getDateInput(String prompt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        while (true) {
            System.out.println(prompt);
            try {
                // Parse the input string into a LocalDate object
                LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);
                //Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
                //Date dueDate = Date.from(instant);

                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use mm/dd/yy");
            }
        }
    }
    //scanner.close();
}
