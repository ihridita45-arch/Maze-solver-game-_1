package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeBoard extends JFrame implements KeyListener {

    private MazePanel mazePanel;
    private Player player;

    // Week 7 features
    private JLabel moveLabel;
    private JLabel timerLabel;

    private int moveCount;
    private int seconds;

    private Timer gameTimer;

    public MazeBoard() {

        setTitle("Maze Solver Game");
        setSize(650, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Game title
        JLabel title = new JLabel("MAZE SOLVER GAME", JLabel.CENTER);

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 15, 10
                )
        );

        // Player starts at row 1, column 1
        player = new Player(1, 1);

        // Initial values
        moveCount = 0;
        seconds = 0;

        // Maze panel
        mazePanel = new MazePanel();

        // Bottom information panel
        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new FlowLayout());

        JLabel startLabel = new JLabel("S = Start");
        JLabel goalLabel = new JLabel("G = Goal");
        JLabel wallLabel = new JLabel("# = Wall");
        JLabel pathLabel = new JLabel("Path = Empty Space");

        moveLabel = new JLabel("Moves: 0");
        timerLabel = new JLabel("Time: 0 sec");

        infoPanel.add(startLabel);
        infoPanel.add(goalLabel);
        infoPanel.add(wallLabel);
        infoPanel.add(pathLabel);
        infoPanel.add(moveLabel);
        infoPanel.add(timerLabel);

        // Restart button
        JButton restartButton = new JButton("Restart");

        restartButton.addActionListener(e -> restartGame());

        infoPanel.add(restartButton);

        add(title, BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        // Keyboard control
        addKeyListener(this);
        setFocusable(true);

        // Start timer
        startTimer();

        setVisible(true);

        requestFocusInWindow();
    }

    // Keyboard input
    @Override
    public void keyPressed(KeyEvent e) {

        int newRow = player.getRow();
        int newCol = player.getCol();

        // Calculate new position
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

        // Check whether the new position is valid
        if (isValidMove(newRow, newCol)) {

            // Move player only if the path is free
            player.setPosition(newRow, newCol);

            // Count valid moves
            moveCount++;

            moveLabel.setText(
                    "Moves: " + moveCount
            );

            mazePanel.repaint();

        } else {

            // Warning message
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot move through a wall!",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // Validate player movement
    private boolean isValidMove(int row, int col) {

        // Check maze boundary
        if (row < 0 || row >= mazePanel.maze.length ||
                col < 0 || col >= mazePanel.maze[0].length) {

            return false;
        }

        // 0 = Path, 1 = Wall
        return mazePanel.maze[row][col] == 0;
    }

    // Start game timer
    private void startTimer() {

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

    // Restart game
    private void restartGame() {

        // Reset player position
        player.setPosition(1, 1);

        // Reset move counter
        moveCount = 0;

        moveLabel.setText(
                "Moves: 0"
        );

        // Reset timer
        seconds = 0;

        timerLabel.setText(
                "Time: 0 sec"
        );

        mazePanel.repaint();

        // Bring keyboard focus back
        requestFocusInWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Panel used to display the maze
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

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            for (int row = 0; row < maze.length; row++) {

                for (int col = 0; col < maze[row].length; col++) {

                    int x = col * cellSize;
                    int y = row * cellSize;

                    // Wall
                    if (maze[row][col] == 1) {

                        g.fillRect(
                                x,
                                y,
                                cellSize,
                                cellSize
                        );
                    }

                    // Path
                    else {

                        g.drawRect(
                                x,
                                y,
                                cellSize,
                                cellSize
                        );
                    }
                }
            }

            // Start position
            g.setFont(
                    new Font("Arial", Font.BOLD, 25)
            );

            g.drawString(
                    "S",
                    75,
                    90
            );

            // Goal position
            g.drawString(
                    "G",
                    470,
                    530
            );

            // Draw Player
            int playerX =
                    player.getCol() * cellSize;

            int playerY =
                    player.getRow() * cellSize;

            g.fillOval(
                    playerX + 10,
                    playerY + 10,
                    cellSize - 20,
                    cellSize - 20
            );
        }
    }
}