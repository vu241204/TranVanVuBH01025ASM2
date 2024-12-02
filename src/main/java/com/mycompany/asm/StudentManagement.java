/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.asm;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Admin
 */
public class StudentManagement {
     private static ArrayList<Student> students = new ArrayList<>();
    
    private static Stack<Student> undoStack = new Stack<>();  // Stack to store deleted students
    
    private static int idCounter = 1; // Starts from 1 or any desired initial value

    public static void addStudent(String name, double marks) {
        int id = idCounter++; // Assign the current value and increment the counter
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully with ID: " + id);
    }

    public static boolean editStudent(int id, String newName, double newMarks) {
        for (Student student : students) {
            if (student.id == id) {
                student.name = newName;
                student.marks = newMarks;
                return true;
            }
        }
        return false;
    }

    public static void deleteStudent(int id) {
     for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id == id) {
                Student removedStudent = students.get(i);
                undoStack.push(removedStudent); // Lưu vào stack trước khi xóa
                students.remove(i); // Xóa sinh viên
                System.out.println("Student with ID " + id + " deleted successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }
    public static void sortStudentsByName() {
        bubbleSort();  // Sắp xếp theo tên
        displayAllStudents(); // Hiển thị danh sách đã sắp xếp
    }

    public static void sortStudentsByMarks() {
        mergeSort(0, students.size() - 1);  // Sắp xếp theo điểm
        displayAllStudents(); // Hiển thị danh sách đã sắp xếp
    }

    // Bubble Sort implementation: Sắp xếp theo tên
    public static void bubbleSort() {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (students.get(j).name.compareTo(students.get(j + 1).name) > 0) {
                    // Swap if the name is lexicographically greater
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        System.out.println("Sorted by Bubble Sort (Name).");
   
    }

    // Merge Sort implementation: Sắp xếp theo điểm
    public static void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Sort the left half
            mergeSort(left, mid);

            // Sort the right half
            mergeSort(mid + 1, right);

            // Merge the two halves
            merge(left, mid, right);
        }
    }

    private static void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Student> leftArray = new ArrayList<>(n1);
        ArrayList<Student> rightArray = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            leftArray.add(students.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            rightArray.add(students.get(mid + 1 + i));
        }

        int i = 0, j = 0;
        int k = left;// Merge the arrays, compare by marks (descending order)
        while (i < n1 && j < n2) {
            if (leftArray.get(i).marks >= rightArray.get(j).marks) {
                students.set(k, leftArray.get(i));
                i++;
            } else {
                students.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // Copy the remaining elements from leftArray
        while (i < n1) {
            students.set(k, leftArray.get(i));
            i++;
            k++;
        }

        // Copy the remaining elements from rightArray
        while (j < n2) {
            students.set(k, rightArray.get(j));
            j++;
            k++;
        }
        System.out.println("Sorted by Merge Sort (Marks).");
    }

    public static Student searchStudentById(int id) {
        for (Student student : students) {
            if (student.id == id) {
                return student;
            }
        }
        return null;
    }

    public static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }
        System.out.println("Student List:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static int inputStudentId(Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Enter ID (integer): ");
            try {
                id = scanner.nextInt();
                if (isIdUnique(id)) {
                    break;
                } else {
                    System.out.println("ID " + id + " already exists. Please enter a different ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. ID must be an integer.");
                scanner.nextLine();
            }
        }
        return id;
    }

    private static boolean isIdUnique(int id) {
        for (Student student : students) {
            if (student.id == id) {
                return false;
            }
        }
        return true;
    }

    public static int inputExistingStudentId(Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Enter ID to edit (must exist): ");
            try {
                id = scanner.nextInt();
                if (!isIdUnique(id)) {
                    break;
                } else {
                    System.out.println("ID " + id + " does not exist. Please enter a valid ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. ID must be an integer.");
                scanner.nextLine();
            }
        }
        return id;
    }

    public static double inputStudentMarks(Scanner scanner) {
        double marks;
        while (true) {
            System.out.print("Enter Marks (0 to 10): ");
            try {marks = scanner.nextDouble();
                if (marks >= 0 && marks <= 10) {
                    break;
                } else {
                    System.out.println("Invalid input. Marks must be between 0 and 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Marks must be a number from 0 to 10.");
                scanner.nextLine();
            }
        }
        return marks;
    }
    
    public static Student binarySearchByName(String name) {
        int left = 0;
        int right = students.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Student midStudent = students.get(mid);
            int compareResult = midStudent.name.compareToIgnoreCase(name);

            if (compareResult == 0) {
                return midStudent;  // Tìm thấy
            } else if (compareResult < 0) {
                left = mid + 1;  // Tìm kiếm phía phải
            } else {
                right = mid - 1;  // Tìm kiếm phía trái
            }
        }
        return null;  // Không tìm thấy
    }
    public static void LastAction() {
        if (!undoStack.isEmpty()) {
            Student lastStudent = undoStack.pop();
            System.out.println("Last student removed: " + lastStudent);
            students.remove(lastStudent);  // Loại bỏ học sinh khỏi danh sách
        } else {
            System.out.println("No action to undo.");
        }
    }

}
