import javax.swing.*;
import java.awt.*;

public class SortingVisualizer extends JFrame {
    private int[] array;
    private final int WIDTH = 800;
    private final int HEIGHT = 500;
    private final int SIZE = 100;
    private boolean isSorting = false;

    private JButton bubbleSortBtn;
    private JButton selectionSortBtn;
    private JButton shuffleBtn;

    public SortingVisualizer() {
        array = new int[SIZE];
        generateArray();

        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SortingPanel panel = new SortingPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 50));
        add(panel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        bubbleSortBtn = new JButton("Bubble Sort");
        selectionSortBtn = new JButton("Selection Sort");
        shuffleBtn = new JButton("Shuffle");

        bubbleSortBtn.addActionListener(e -> {
            if (!isSorting) new Thread(() -> bubbleSort(panel)).start();
        });

        selectionSortBtn.addActionListener(e -> {
            if (!isSorting) new Thread(() -> selectionSort(panel)).start();
        });

        shuffleBtn.addActionListener(e -> {
            if (!isSorting) {
                generateArray();
                panel.repaint();
            }
        });

        buttons.add(bubbleSortBtn);
        buttons.add(selectionSortBtn);
        buttons.add(shuffleBtn);

        add(buttons, BorderLayout.SOUTH);

        pack(); // Use preferred size
    }

    private void generateArray() {
        for (int i = 0; i < SIZE; i++) {
            array[i] = (int) (Math.random() * (HEIGHT - 100)) + 20; // visible range
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        bubbleSortBtn.setEnabled(enabled);
        selectionSortBtn.setEnabled(enabled);
        shuffleBtn.setEnabled(enabled);
    }

    private void bubbleSort(SortingPanel panel) {
        isSorting = true;
        setButtonsEnabled(false);
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    panel.repaint();
                    sleep();
                }
            }
        }
        isSorting = false;
        setButtonsEnabled(true);
    }

    private void selectionSort(SortingPanel panel) {
        isSorting = true;
        setButtonsEnabled(false);
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
            panel.repaint();
            sleep();
        }
        isSorting = false;
        setButtonsEnabled(true);
    }

    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class SortingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int gap = 2; // gap between bars
            int barWidth = (panelWidth - (SIZE * gap)) / SIZE;

            for (int i = 0; i < SIZE; i++) {
                g.setColor(Color.BLUE);
                int barHeight = array[i];
                int x = i * (barWidth + gap);
                int y = panelHeight - barHeight - 20; // adjust to fit nicely

                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingVisualizer visualizer = new SortingVisualizer();
            visualizer.setVisible(true);
        });
    }
}




