import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SearchSortGUI extends JFrame {
    private JTextField inputField;
    private JTextField valueField;
    private JTextArea outputArea;
    private JComboBox<String> algorithmBox;
    private JButton executeButton;
    private JButton restartButton;

    public SearchSortGUI() {
        setTitle("Search and Sort Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));

        topPanel.add(new JLabel("Enter list of integers (space-separated):"));
        inputField = new JTextField();
        topPanel.add(inputField);

        topPanel.add(new JLabel("Enter value to search (for search operations):"));
        valueField = new JTextField();
        topPanel.add(valueField);

        topPanel.add(new JLabel("Choose algorithm:"));
        algorithmBox = new JComboBox<>(new String[] { "Linear Search", "Binary Search", "Bubble Sort", "Quick Sort" });
        topPanel.add(algorithmBox);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        executeButton = new JButton("Execute");
        executeButton.addActionListener(new ExecuteListener());
        bottomPanel.add(executeButton);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            inputField.setText("");
            valueField.setText("");
            outputArea.setText("");
        });
        bottomPanel.add(restartButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private class ExecuteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] input = inputField.getText().split(" ");
            int[] array = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();
            String algorithm = (String) algorithmBox.getSelectedItem();

            long startTime = System.nanoTime();
            String result = "";
            if (algorithm != null) {
                switch (algorithm) {
                    case "Linear Search":
                        int value = Integer.parseInt(valueField.getText());
                        int searchResult = linearSearch(array, value);
                        result = "Search result: "
                                + (searchResult != -1 ? "Found at index " + searchResult : "Not found");
                        result += "\nTime complexity: O(n)";
                        break;
                    case "Binary Search":
                        Arrays.sort(array); // Binary search requires sorted array
                        value = Integer.parseInt(valueField.getText());
                        searchResult = binarySearch(array, value);
                        result = "Search result: "
                                + (searchResult != -1 ? "Found at index " + searchResult : "Not found");
                        result += "\nTime complexity: O(log n)";
                        break;
                    case "Bubble Sort":
                        bubbleSort(array);
                        result = "Sorted list: " + Arrays.toString(array);
                        result += "\nTime complexity: O(n^2)";
                        break;
                    case "Quick Sort":
                        quickSort(array, 0, array.length - 1);
                        result = "Sorted list: " + Arrays.toString(array);
                        result += "\nTime complexity: O(n log n)";
                        break;
                }
            }
            long endTime = System.nanoTime();
            result += "\nTime taken: " + (endTime - startTime) + " ns";
            outputArea.setText(result);
        }
    }

    private int linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private int binarySearch(int[] array, int value) {
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

    private void bubbleSort(int[] array) {
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

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SearchSortGUI app = new SearchSortGUI();
            app.setVisible(true);
        });
    }
}
