package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {

    JButton startButton;
    JButton helpButton;
    JButton exitButton;
    JLabel titleLabel;

    public MainMenu() {

        setTitle("Maze Solver Software");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("Maze Solver Software");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(95, 40, 300, 40);
        add(titleLabel);

        startButton = new JButton("Start");
        startButton.setBounds(170, 120, 150, 40);
        startButton.addActionListener(this);
        add(startButton);

        helpButton = new JButton("Help");
        helpButton.setBounds(170, 180, 150, 40);
        helpButton.addActionListener(this);
        add(helpButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(170, 240, 150, 40);
        exitButton.addActionListener(this);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {

            new MazeBoard();
            dispose();

        }

        else if (e.getSource() == helpButton) {

            JOptionPane.showMessageDialog(this,
                    "Use Arrow Keys to move from Start to Goal.");

        }

        else if (e.getSource() == exitButton) {

            System.exit(0);

        }

    }

}