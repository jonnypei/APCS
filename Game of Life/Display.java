import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class Display extends JComponent implements MouseListener, MouseMotionListener {
    public static final int ROWS = 80;
    public static final int COLS = 100;
    public static Cell[][] cell = new Cell[ROWS][COLS];
    private final int X_GRID_OFFSET = 25; // 25 pixels from left
    private final int Y_GRID_OFFSET = 40; // 40 pixels from top
    private final int CELL_WIDTH = 5;
    private final int CELL_HEIGHT = 5;

    private final int DISPLAY_WIDTH;
    private final int DISPLAY_HEIGHT;
    private StartButton startStop;
    private ClearButton clear;
    private CloseButton close;
    private StepButton step;
    private ToggleWrapButton toggleWrap;
    private boolean paintloop = false;
    private boolean wrapping = false;


    public Display(int width, int height) {
        DISPLAY_WIDTH = width;
        DISPLAY_HEIGHT = height;
        init();
    }


    public void init() {
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        initCells();

        addMouseListener(this);
        addMouseMotionListener(this);

        startStop = new StartButton();
        startStop.setBounds(50, 550, 100, 36);
        add(startStop);
        startStop.setVisible(true);

        clear = new ClearButton();
        clear.setBounds(150, 550, 100, 36);
        add(clear);
        clear.setVisible(true);

        close = new CloseButton();
        close.setBounds(450, 550, 100, 36);
        add(close);
        close.setVisible(true);

        step = new StepButton();
        step.setBounds(250, 550, 100, 36);
        add(step);
        step.setVisible(true);

        toggleWrap = new ToggleWrapButton();
        toggleWrap.setBounds(350, 550, 100, 36);
        add(toggleWrap);
        toggleWrap.setVisible(true);

        repaint();
    }


    public void paintComponent(Graphics g) {
        final int TIME_BETWEEN_REPLOTS = 75; // change to your liking

        g.setColor(Color.BLACK);
        drawGrid(g);
        drawCells(g);
        drawButtons();

        if (paintloop && wrapping) {
            try {
                Thread.sleep(TIME_BETWEEN_REPLOTS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextGenerationWrap();

            if (step.getText().equals("Stepping") && startStop.getText().equals("Start")) {
                togglePaintLoop();
                step.setText("Step");
            }

            if (clear.getText().equals("Clearing"))  {
                togglePaintLoop();
                clear.setText("Clear");
            }

            repaint();
        } else if (paintloop && !wrapping) {
            try {
                Thread.sleep(TIME_BETWEEN_REPLOTS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextGeneration();

            if (step.getText().equals("Stepping") && startStop.getText().equals("Start")) {
                togglePaintLoop();
                step.setText("Step");
            }

            if (clear.getText().equals("Clearing"))  {
                togglePaintLoop();
                clear.setText("Clear");
            }

            repaint();
        }
    }


    public void initCells() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cell[row][col] = new Cell(row, col);
            }
        }

        int shift = 20;

        cell[36][22 + shift].setAlive(true);
        cell[36][23 + shift].setAlive(true);
        cell[35][23 + shift].setAlive(true);
        cell[35][22 + shift].setAlive(true);

        cell[36][32 + shift].setAlive(true);
        cell[35][32 + shift].setAlive(true);
        cell[37][32 + shift].setAlive(true);
        cell[34][33 + shift].setAlive(true);
        cell[33][34 + shift].setAlive(true);
        cell[33][35 + shift].setAlive(true);
        cell[38][33 + shift].setAlive(true);
        cell[39][34 + shift].setAlive(true);
        cell[39][35 + shift].setAlive(true);
        cell[36][36 + shift].setAlive(true);
        cell[34][37 + shift].setAlive(true);
        cell[38][37 + shift].setAlive(true);
        cell[36][38 + shift].setAlive(true);
        cell[35][38 + shift].setAlive(true);
        cell[37][38 + shift].setAlive(true);
        cell[36][39 + shift].setAlive(true);

        cell[35][42 + shift].setAlive(true);
        cell[34][42 + shift].setAlive(true);
        cell[33][42 + shift].setAlive(true);
        cell[35][43 + shift].setAlive(true);
        cell[34][43 + shift].setAlive(true);
        cell[33][43 + shift].setAlive(true);
        cell[32][44 + shift].setAlive(true);
        cell[36][44 + shift].setAlive(true);
        cell[36][46 + shift].setAlive(true);
        cell[37][46 + shift].setAlive(true);
        cell[31][46 + shift].setAlive(true);
        cell[32][46 + shift].setAlive(true);

        cell[33][56 + shift].setAlive(true);
        cell[34][56 + shift].setAlive(true);
        cell[33][57 + shift].setAlive(true);
        cell[34][57 + shift].setAlive(true);
    }


    public void togglePaintLoop() {
        paintloop = !paintloop;
    }


    public void setPaintLoop(boolean value) {
        paintloop = value;
    }


    void drawGrid(Graphics g) {
        for (int row = 0; row <= ROWS; row++) {
            g.drawLine(X_GRID_OFFSET,
                    Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
                            + COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
                            + (row * (CELL_HEIGHT + 1)));
        }
        for (int col = 0; col <= COLS; col++) {
            g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
                    X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
                            + ROWS * (CELL_HEIGHT + 1));
        }
    }


    void drawCells(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
            }
        }
    }


    private void drawButtons() {
        startStop.repaint();
    }

    // Moves all cells over to the right by one; I made this method when I just started this assignment
    private void moveOverByOne() {
        boolean[][] grid = new boolean[80][100];

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                if (cell[i][j].getAlive()) {
                    grid[i][j] = true;
                }
            }
        }

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                cell[i][j].setAlive(false);

                if (grid[i][j]) {
                    cell[i][j + 1].setAlive(true);
                }
            }
        }
    }

    private void nextGenerationWrap() {

        boolean[][] grid = new boolean[80][100];

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                int n = cell[i][j].getNeighborsWrap(cell);

                if (cell[i][j].getAlive()) { // Cell is currently alive
                    if (n == 2 || n == 3) // Cell survives
                        grid[i][j] = true;
                } else { // Cell is currently dead
                    if (n == 3) // Becomes populated
                        grid[i][j] = true;
                }
            }
        }

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                cell[i][j].setAlive(false);

                if (grid[i][j])
                    cell[i][j].setAlive(true);
            }
        }
    }

    private void nextGeneration() {

        boolean[][] grid = new boolean[80][100];

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                int n = cell[i][j].getNeighbors(cell);

                if (cell[i][j].getAlive()) { // Cell is currently alive
                    if (n == 2 || n == 3) // Cell survives
                        grid[i][j] = true;
                } else { // Cell is currently dead
                    if (n == 3) // Becomes populated
                        grid[i][j] = true;
                }
            }
        }

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                cell[i][j].setAlive(false);

                if (grid[i][j])
                    cell[i][j].setAlive(true);
            }
        }
    }

    private void clearAllCells() {
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 100; j++) {
                cell[i][j].setAlive(false);
            }
        }
    }


    public void mouseClicked(MouseEvent arg0) {

    }


    public void mouseEntered(MouseEvent arg0) {

    }


    public void mouseExited(MouseEvent arg0) {

    }


    public void mousePressed(MouseEvent arg0) {

    }


    public void mouseReleased(MouseEvent arg0) {

    }


    public void mouseDragged(MouseEvent arg0) {
        if (startStop.getText().equals("Start")) {
            int mouseX = arg0.getX();
            int mouseY = arg0.getY();

            if ((mouseX >= 25 && mouseX < 625) && (mouseY >= 40 && mouseY < 520)) {
                int cellX = (mouseX - 25) / 6;
                int cellY = (mouseY - 40) / 6;

                if (cell[cellY][cellX].getAlive())
                    cell[cellY][cellX].setAlive(false);
                else
                    cell[cellY][cellX].setAlive(true);

                repaint();
            }
        } else {
            // Do nothing
        }
    }


    public void mouseMoved(MouseEvent arg0) {

    }


    private class StartButton extends JButton implements ActionListener {
        StartButton() {
            super("Start");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            if (this.getText().equals("Start")) {
                togglePaintLoop();
                setText("Stop");
            } else {
                togglePaintLoop();
                setText("Start");
            }
            repaint();
        }
    }

    private class ClearButton extends JButton implements ActionListener {
        ClearButton() {
            super("Clear");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            if (startStop.getText().equals("Start")) {
                this.setText("Clearing");

                togglePaintLoop();

                clearAllCells();

                repaint();
            }
        }
    }

    private class CloseButton extends JButton implements ActionListener {
        CloseButton() {
            super("Close");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            System.exit(0);
        }
    }


    private class StepButton extends JButton implements ActionListener {
        StepButton() {
            super("Step");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            if (startStop.getText().equals("Start")) {
                this.setText("Stepping");

                togglePaintLoop();

                repaint();
            }
        }
    }

    private class ToggleWrapButton extends JButton implements ActionListener {
        ToggleWrapButton() {
            super("Not Wrapping");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent arg0) {
            if (this.getText().equals("Not Wrapping") && startStop.getText().equals("Start")) {
                setText("Wrapping");
                wrapping = true;
            } else if (this.getText().equals("Wrapping") && startStop.getText().equals("Start")) {
                setText("Not Wrapping");
                wrapping = false;
            }
        }
    }
}
