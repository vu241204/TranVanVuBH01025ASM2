/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.asm;

import static com.mycompany.asm.StudentManagement.LastAction;
import static com.mycompany.asm.StudentManagement.addStudent;
import static com.mycompany.asm.StudentManagement.binarySearchByName;
import static com.mycompany.asm.StudentManagement.bubbleSort;
import static com.mycompany.asm.StudentManagement.deleteStudent;
import static com.mycompany.asm.StudentManagement.displayAllStudents;
import static com.mycompany.asm.StudentManagement.editStudent;
import static com.mycompany.asm.StudentManagement.inputExistingStudentId;
import static com.mycompany.asm.StudentManagement.inputStudentMarks;
import static com.mycompany.asm.StudentManagement.searchStudentById;
import static com.mycompany.asm.StudentManagement.sortStudentsByMarks;
import static com.mycompany.asm.StudentManagement.sortStudentsByName;
import java.util.InputMismatchException;
import java.util.Scanner;



/**
 *
 * @author Admin
 */
public class ASM {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n----- Student Management System -----");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students by Name (Bubble Sort)");
            System.out.println("5. Sort Students by Marks (Merge Sort)");
            System.out.println("6. Search Student by ID (Linear Search)");
            System.out.println("7. Search Student by Name (Binary Search) ");         
            System.out.println("8. Display All Students");
            System.out.println("9. Last Action");
            System.out.println("10. Exit");

            // Kiểm tra và yêu cầu nhập lại nếu người dùng nhập sai option
            int choice = -1;
            while (choice < 1 || choice > 10) {
                System.out.print("Select an option: ");
                try {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > 10) {
                        System.out.println("Invalid choice. Please choose a valid option (1-8).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please choose a valid option (1-8).");
                    scanner.nextLine(); // Clear buffer
                }
            }

            switch (choice) {
                case 1:
//                    int id = inputStudentId(scanner);
//                    scanner.nextLine();
                    String name;
                    // Kiểm tra tính hợp lệ của tên
                    while (true) {
                        System.out.print("Enter Student Name: ");
                        name = scanner.nextLine();
                        if (name.matches("[a-zA-Z\\s]+")) {
                            break;
                        } else {
                            System.out.println("Invalid name. Please use only letters and spaces, no numbers or special characters.");
                        }
                    }
                    double marks = inputStudentMarks(scanner);
                    addStudent(name, marks);
                    break;
                case 2:
                    int editId = inputExistingStudentId(scanner);
                    scanner.nextLine();  // Clear the newline character
                    String newName = "";
                    while (true) {
                        System.out.print("Enter New Name: ");
                        newName = scanner.nextLine();
                        if (newName.matches("[a-zA-Z\\s]+")) {
                            break;
                        } else {
                            System.out.println("Invalid name. Please use only letters and spaces, no numbers or special characters.");
                        }
                    }
                    double newMarks = inputStudentMarks(scanner);
                    if (!editStudent(editId, newName, newMarks)) {
                        System.out.println("Student with ID " + editId + " not found.");
                    } else {
                        System.out.println("Student updated successfully.");
                    }
                    break;

                case 3:
                    int deleteId = inputExistingStudentId(scanner);
                    deleteStudent(deleteId);
                    break;
                case 4:
                    System.out.println("Sorting Students by Name using Bubble Sort...");
                    sortStudentsByName();
                    break;

                case 5:
                    System.out.println("Sorting Students by Marks using Merge Sort...");
                    sortStudentsByMarks();
                    break;
                case 6:
                    int searchId = inputExistingStudentId(scanner);
                    Student student = searchStudentById(searchId);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student with ID " + searchId + " not found.");
                    }
                    break;
                case 7:
                    // Search by Name
                    System.out.print("Enter Student Name to search: ");
                    scanner.nextLine();  // Clear newline
                    String searchName = scanner.nextLine();

                    // Ensure the list is sorted before searching by name
                    bubbleSort();  // Sort the list by name before binary search
                    Student studentByName = binarySearchByName(searchName);
                    if (studentByName != null) {
                        System.out.println(studentByName);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 8:
                    displayAllStudents();
                    break;
                case 9:
                    LastAction();  // Thực hiện undo
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
                    break;
            }
        }
        scanner.close();
    }
    
}
