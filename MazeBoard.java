package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class MazeBoard extends JFrame implements KeyListener {

    private MazePanel mazePanel;
    private Player player;

    private JLabel moveLabel;
    private JLabel timerLabel;

    private int moveCount;
    private int seconds;

    private Timer gameTimer;

    private JButton autoSolveButton;

    private boolean autoSolving;
    private boolean gameCompleted;

    public MazeBoard() {

        setTitle("Maze Solver Game");
        setSize(650, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel(
                "MAZE SOLVER GAME",
                JLabel.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        // =========================
        // STATUS PANEL
        // Move + Time Counter
        // =========================

        JPanel statusPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        25,
                        5
                )
        );

        moveLabel = new JLabel("Moves: 0");
        timerLabel = new JLabel("Time: 0 sec");

        moveLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        timerLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        statusPanel.add(moveLabel);
        statusPanel.add(timerLabel);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        5
                )
        );

        JButton restartButton = new JButton("Restart");

        autoSolveButton = new JButton("Auto Solve");

        JButton aboutButton = new JButton("About");

        restartButton.addActionListener(
                e -> restartGame()
        );

        autoSolveButton.addActionListener(
                e -> startAutoSolve()
        );

        aboutButton.addActionListener(
                e -> showAbout()
        );

        buttonPanel.add(restartButton);
        buttonPanel.add(autoSolveButton);
        buttonPanel.add(aboutButton);

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel = new JPanel(
                new BorderLayout()
        );

        topPanel.add(
                title,
                BorderLayout.NORTH
        );

        topPanel.add(
                statusPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // INITIALIZE GAME
        // =========================

        player = new Player(1, 1);

        moveCount = 0;
        seconds = 0;

        autoSolving = false;
        gameCompleted = false;

        mazePanel = new MazePanel();

        // =========================
        // BOTTOM LEGEND
        // =========================

        JPanel infoPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );

        JLabel startLabel =
                new JLabel("S = Start");

        JLabel goalLabel =
                new JLabel("G = Goal");

        JLabel wallLabel =
                new JLabel("# = Wall");

        JLabel pathLabel =
                new JLabel("Path = Empty Space");

        infoPanel.add(startLabel);
        infoPanel.add(goalLabel);
        infoPanel.add(wallLabel);
        infoPanel.add(pathLabel);

        // =========================
        // ADD COMPONENTS
        // =========================

        add(
                topPanel,
                BorderLayout.NORTH
        );

        add(
                mazePanel,
                BorderLayout.CENTER
        );

        add(
                infoPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // KEYBOARD
        // =========================

        addKeyListener(this);

        setFocusable(true);

        // =========================
        // TIMER
        // =========================

        startTimer();

        setVisible(true);

        requestFocusInWindow();
    }

    // =====================================================
    // PLAYER MOVEMENT
    // =====================================================

    @Override
    public void keyPressed(KeyEvent e) {

        if (autoSolving || gameCompleted) {
            return;
        }

        int newRow = player.getRow();
        int newCol = player.getCol();

        if (e.getKeyCode() == KeyEvent.VK_UP) {

            newRow--;

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            newRow++;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            newCol--;

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            newCol++;

        } else {

            return;
        }

        // Check valid movement
        if (isValidMove(newRow, newCol)) {

            player.setPosition(
                    newRow,
                    newCol
            );

            // Increase move count
            moveCount++;

            moveLabel.setText(
                    "Moves: " + moveCount
            );

            mazePanel.repaint();

            checkGoal();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "You cannot move through a wall!",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =====================================================
    // MOVE VALIDATION
    // =====================================================

    private boolean isValidMove(
            int row,
            int col
    ) {

        if (
                row < 0 ||
                row >= mazePanel.maze.length ||
                col < 0 ||
                col >= mazePanel.maze[0].length
        ) {

            return false;
        }

        return mazePanel.maze[row][col] == 0;
    }

    // =====================================================
    // TIMER
    // =====================================================

    private void startTimer() {

        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameTimer = new Timer(
                1000,
                e -> {

                    seconds++;

                    timerLabel.setText(
                            "Time: " + seconds + " sec"
                    );
                }
        );

        gameTimer.start();
    }

    // =====================================================
    // RESTART GAME
    // =====================================================

    private void restartGame() {

        autoSolving = false;
        gameCompleted = false;

        autoSolveButton.setEnabled(true);

        // Reset player position
        player.setPosition(1, 1);

        // Reset moves
        moveCount = 0;

        moveLabel.setText(
                "Moves: 0"
        );

        // Reset timer
        seconds = 0;

        timerLabel.setText(
                "Time: 0 sec"
        );

        // Remove solution path
        mazePanel.solutionPath = null;

        // Start timer again
        startTimer();

        mazePanel.repaint();

        requestFocusInWindow();
    }

    // =====================================================
    // GOAL DETECTION
    // =====================================================

    private void checkGoal() {

        if (
                player.getRow() == 8 &&
                player.getCol() == 8
        ) {

            gameCompleted = true;

            if (gameTimer != null) {
                gameTimer.stop();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Congratulations!\n"
                    + "You reached the goal!\n\n"
                    + "Moves: " + moveCount + "\n"
                    + "Time: " + seconds + " sec",
                    "You Win!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =====================================================
    // AUTO SOLVER
    // =====================================================

    private void startAutoSolve() {

        if (autoSolving || gameCompleted) {
            return;
        }

        autoSolving = true;

        autoSolveButton.setEnabled(false);

        if (gameTimer != null) {
            gameTimer.stop();
        }

        AutoSolver solver =
                new AutoSolver(
                        mazePanel.maze,
                        1,
                        1,
                        8,
                        8
                );

        List<Point> path = solver.solve();

        if (
                path == null ||
                path.isEmpty()
        ) {

            autoSolving = false;

            autoSolveButton.setEnabled(true);

            startTimer();

            JOptionPane.showMessageDialog(
                    this,
                    "No solution path found!",
                    "Auto Solver",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        mazePanel.solutionPath = path;

        animateSolution(path);
    }

    // =====================================================
    // AUTO SOLVER ANIMATION
    // =====================================================

    private void animateSolution(
            List<Point> path
    ) {

        final int[] index = {1};

        Timer solverTimer =
                new Timer(
                        300,
                        null
                );

        solverTimer.addActionListener(
                e -> {

                    if (index[0] >= path.size()) {

                        solverTimer.stop();

                        autoSolving = false;

                        gameCompleted = true;

                        JOptionPane.showMessageDialog(
                                this,
                                "Auto Solve Completed!\n"
                                + "The goal has been reached.",
                                "Auto Solver",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    Point current =
                            path.get(index[0]);

                    player.setPosition(
                            current.x,
                            current.y
                    );

                    index[0]++;

                    moveCount++;

                    moveLabel.setText(
                            "Moves: " + moveCount
                    );

                    mazePanel.repaint();
                }
        );

        solverTimer.start();
    }

    // =====================================================
    // ABOUT
    // =====================================================

    private void showAbout() {

        JOptionPane.showMessageDialog(
                this,

                "Maze Solver Software\n\n"
                + "A Java Swing based maze solving game.\n\n"
                + "Features:\n"
                + "- Player Movement\n"
                + "- Wall Validation\n"
                + "- Move Counter\n"
                + "- Game Timer\n"
                + "- Restart Option\n"
                + "- Auto Solver\n"
                + "- Goal Detection\n"
                + "- Winning Message\n\n"
                + "Developed for Software Development I.",

                "About Maze Solver",

                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // KEY LISTENER METHODS
    // =====================================================

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // =====================================================
    // MAZE PANEL
    // =====================================================

    class MazePanel extends JPanel {

        private final int[][] maze = {

            {1,1,1,1,1,1,1,1,1,1},

            {1,0,0,0,1,0,0,0,0,1},

            {1,0,1,0,1,0,1,1,0,1},

            {1,0,1,0,0,0,0,1,0,1},

            {1,0,1,1,1,1,0,1,0,1},

            {1,0,0,0,0,0,0,1,0,1},

            {1,1,1,1,1,0,1,1,0,1},

            {1,0,0,0,1,0,0,0,0,1},

            {1,0,1,0,0,0,1,1,0,1},

            {1,1,1,1,1,1,1,1,1,1}
        };

        private final int cellSize = 55;

        private List<Point> solutionPath;

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            // Draw maze
            for (
                    int row = 0;
                    row < maze.length;
                    row++
            ) {

                for (
                        int col = 0;
                        col < maze[row].length;
                        col++
                ) {

                    int x =
                            col * cellSize;

                    int y =
                            row * cellSize;

                    if (maze[row][col] == 1) {

                        g.fillRect(
                                x,
                                y,
                                cellSize,
                                cellSize
                        );

                    } else {

                        g.drawRect(
                                x,
                                y,
                                cellSize,
                                cellSize
                        );
                    }
                }
            }

            // Draw solution path
            if (solutionPath != null) {

                for (
                        Point point :
                        solutionPath
                ) {

                    int x =
                            point.y * cellSize;

                    int y =
                            point.x * cellSize;

                    g.drawOval(
                            x + 20,
                            y + 20,
                            15,
                            15
                    );
                }
            }

            // Start and Goal
            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            25
                    )
            );

            g.drawString(
                    "S",
                    75,
                    90
            );

            g.drawString(
                    "G",
                    470,
                    475
            );

            // Draw player
            int playerX =
                    player.getCol()
                    * cellSize;

            int playerY =
                    player.getRow()
                    * cellSize;

            g.fillOval(
                    playerX + 10,
                    playerY + 10,
                    cellSize - 20,
                    cellSize - 20
            );
        }
    }
}