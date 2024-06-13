import java.util.Arrays;
import java.util.Scanner;

public class SearchSortApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Search");
            System.out.println("2. Sort");
            int choice = scanner.nextInt();

            System.out.println("Enter the list of integers (space-separated):");
            scanner.nextLine(); // consume newline
            String[] input = scanner.nextLine().split(" ");
            int[] array = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

            if (choice == 1) {
                searchOperation(scanner, array);
            } else if (choice == 2) {
                sortOperation(scanner, array);
            } else {
                System.out.println("Invalid choice. Try again.");
            }

            System.out.println("Do you want to restart? (yes/no)");
            String restart = scanner.next();
            if (!restart.equalsIgnoreCase("yes")) {
                break;
            }
        }
        scanner.close();
    }

    private static void searchOperation(Scanner scanner, int[] array) {
        System.out.println("Choose a search algorithm:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        int choice = scanner.nextInt();

        System.out.println("Enter the value to search:");
        int value = scanner.nextInt();

        long startTime = System.nanoTime();
        int result = -1;
        if (choice == 1) {
            result = linearSearch(array, value);
        } else if (choice == 2) {
            Arrays.sort(array); // Binary search requires sorted array
            result = binarySearch(array, value);
        } else {
            System.out.println("Invalid choice.");
        }
        long endTime = System.nanoTime();

        System.out.println("Search result: " + (result != -1 ? "Found at index " + result : "Not found"));
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
        System.out.println("Time complexity: " + (choice == 1 ? "O(n)" : "O(log n)"));
    }

    private static int linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private static int binarySearch(int[] array, int value) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == value) {
                return mid;
            } else if (array[mid] < value) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private static void sortOperation(Scanner scanner, int[] array) {
        System.out.println("Choose a sort algorithm:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Quick Sort");
        int choice = scanner.nextInt();

        long startTime = System.nanoTime();
        if (choice == 1) {
            bubbleSort(array);
        } else if (choice == 2) {
            quickSort(array, 0, array.length - 1);
        } else {
            System.out.println("Invalid choice.");
        }
        long endTime = System.nanoTime();

        System.out.println("Sorted list: " + Arrays.toString(array));
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
        System.out.println("Time complexity: " + (choice == 1 ? "O(n^2)" : "O(n log n)"));
    }

    private static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
