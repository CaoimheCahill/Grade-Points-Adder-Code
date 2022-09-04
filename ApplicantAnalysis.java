/**
 * Assignment 2022 Part4 (ApplicantAnalysis Class)
 *
 * Student Number: 21331308
 * Name: Caoimhe Cahill
 *
 * 30th April 2022
 */

import java.util.*;
import java.io.*;

public class ApplicantAnalysis {

    public static void main(String[] args) {

        if(args.length == 2) {
            // File containing applicant information
            String filePath = args[0];
            // Course points cutoff - passed in the command line as a String and converted to an integer
            int cutoff = Integer.parseInt(args[1]);
            // TreeMap stores the applicant Number and associated points total (i.e. ID ---> Points)
            TreeMap<String,Integer> candidateScores = calculateApplicantScores(filePath);
            if(candidateScores != null) {
                // LinkedList stores a list of applicantNumbers containing the applicants with Points >= cutoff
                LinkedList<String> chosenApplicants = select(candidateScores,cutoff);
                // LinkedList toString method will automatically be used to display list of successful applicantNumbers
                if(chosenApplicants != null) {
                    System.out.println(chosenApplicants);
                } else {
                    System.out.println("There are no applicants with sufficient points for the course!");
                }
            } else {
                System.out.println("There are no applicants for the course!");
            }
        } else {
            // Program command line is incorrect
            System.out.println("Command Line format error.");
            System.out.println("Use 'ApplicantAnalysis filepath cutoff'");
            System.out.println("For example - ApplicantAnalysis LM999.CSV 390'");
        }
    }

    public static TreeMap<String,Integer> calculateApplicantScores(String filePath) {
        try {
            // Create a File object to access the file
            File fileHandle = new File(filePath);
            // Create an instance of the Scanner to actually read the file
            Scanner csvFile = new Scanner(fileHandle);
            // TreeMap stores the applicant applicantNumber and associated points total (i.e. ID ---> Points)
            TreeMap<String,Integer> candidates = new TreeMap<String,Integer>();
            // Read through the CSV file of Applicant Numbers and  LCE grades
            // and calculate the applicant points scores
            while(csvFile.hasNext()){
                // Read the next applicant data line (applicantNumber followed by grades - comma separated)
                String applicantDetails = csvFile.nextLine();
                // Find end of applicant Number (i.e. first comma)
                int posFirstComma = applicantDetails.indexOf(",");
                // Extract the applicant Exam Number
                String applicantID = applicantDetails.substring(0,posFirstComma);
                // Extract the part of the CSV line that contains the grades (i.e. from position after first comma)
                String applicantGrades = applicantDetails.substring(posFirstComma+1);
                // Use String split operation to create array from grades
                String[] grades = applicantGrades.split(",");
                // For testing purposes we might want to display the data
                //System.out.printf("\nThis applicant : %s - %s\n",applicantID, Arrays.toString(applicantGrades));
                // Use the "pointsScore" method to calculate the applicants points total and
                // add the applicantNumber and points score to the TreeMap
                candidates.put(applicantID,pointsScore(grades));
            }
            // Return the TreeMap
            return candidates;
        } catch (IOException e) {
            // If there is some problem with the file we just report it
            System.out.printf("Cannot access the file named '%s'!\n",filePath);
            return null;
        }
    }

    public static LinkedList<String> select(TreeMap<String,Integer> candidateScores,int cutoff) {

        // creating a new linked list called scores for applicant ID numbers to be added.
        LinkedList<String> scores = new LinkedList<>();


         for(Map.Entry<String, Integer> candidate: candidateScores.entrySet()){ // loops through the list of candidates and their scores (Key = ID) (Value = overall score)

             if(candidate.getValue() >= cutoff){ // checks if the candidates score is greater than or equal to the specified cutoff point number.
                 scores.add(candidate.getKey()); // if candidates score is greater than or equal to the specified cutoff point number, the candidates ID number will be added to the 'scores' linked list.
             }
         }

         // if score size is equal to 0, return nothing.
         if(scores.size() == 0){
             return null;
         }

         return scores;
    }

    private static HashMap<String, Integer> setGradeScores(){

        // creating a new hashmap called grades
        HashMap<String, Integer> grades = new HashMap<>();

        // insert specific keys and values into 'grades' hashmap
        // honours level grades
        grades.put("H1", 100);
        grades.put("H2", 88);
        grades.put("H3", 77);
        grades.put("H4", 66);
        grades.put("H5", 56);
        grades.put("H6", 46);
        grades.put("H7", 37);
        grades.put("H8", 0);

        // ordinary level grades
        grades.put("O1", 56);
        grades.put("O2", 46);
        grades.put("O3", 37);
        grades.put("O4", 28);
        grades.put("O5", 20);
        grades.put("O6", 12);
        grades.put("O7", 0);
        grades.put("O8", 0);

        // if no grade, give 0
        grades.put("", 0);

        return grades;
    }

    public static int pointsScore(String[] subjectGrades) {
        ArrayList<Integer> results = new ArrayList<>(); // creating new array list called results
        HashMap<String, Integer> gradeScores = setGradeScores(); // getting list of grades from setGradeScores hashmap

        for(String grade: subjectGrades){ // loop through students grades
            results.add(gradeScores.get(grade)); // gets students grades and adds them to 'results' array list
        }

        // reverses the order so that it goes from highest to lowest grades
        results.sort(Collections.reverseOrder());

        int result = 0; // setting result to 0
        int minSubjects = 6; // setting 6 as the minimum number of subjects needed

        for(int i=0; i<minSubjects; i++){ // goes through result list until i=6
            result += results.get(i); // gets highest 6 grades from student from result list and adds number of points together
        }

        return result;
    }

}
