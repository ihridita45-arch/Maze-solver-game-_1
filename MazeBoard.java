package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeBoard extends JFrame implements KeyListener {

    private MazePanel mazePanel;
    private Player player;

    public MazeBoard() {

        setTitle("Maze Solver Game");
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "MAZE SOLVER GAME",
                JLabel.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 15, 10
                )
        );

        add(title, BorderLayout.NORTH);

        // Player starts at row 1, column 1
        player = new Player(1, 1);

        mazePanel = new MazePanel();

        add(mazePanel, BorderLayout.CENTER);

        JLabel instruction = new JLabel(
                "Use Arrow Keys to Move",
                JLabel.CENTER
        );

        instruction.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        instruction.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        add(instruction, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);

        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {

            player.moveUp();

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            player.moveDown();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            player.moveLeft();

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            player.moveRight();
        }

        mazePanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


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

            // Draw Start
            g.setFont(
                    new Font("Arial", Font.BOLD, 25)
            );

            g.drawString(
                    "S",
                    75,
                    90
            );

            // Draw Goal
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