import javax.swing.*;
import java.awt.*;

public class MazeBoard extends JFrame {

    public MazeBoard() {

        setTitle("Maze Board");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Maze Board Coming in Week 4", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));

        add(label);

        setVisible(true);

    }

}