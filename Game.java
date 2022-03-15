import javax.swing.*;
import java.util.List;


public class Game extends JFrame{

    private int x = 30;
    private int y = 70;
    private int h = 50;
    private int w = 50;
    JFrame frame;
    JPanel panel;
    JButton[][] buttonArray = new JButton[5][6];
    private static JLabel label;
    private static JLabel unused;
    private static JLabel info;
    private static JOptionPane winStatus;



    public void createGame() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(327, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setTitle("Wordle");
        frame.setLocation(797,50);

        for (int i = 0; i < buttonArray[0].length; i++) {
            for (int j = 0; j < buttonArray.length; j++) {
                buttonArray[j][i] = new JButton("0");
                buttonArray[j][i].setBounds(x, y, w, h);
                x += 50;
                panel.add(buttonArray[j][i]);
            }
            y += 50;
            x = 30;
        }

        info = new JLabel("Capital letters mean the letter is in the word but ");
        info.setBounds(x,5,300,25);
        panel.add(info);

        info = new JLabel("is in the wrong place, lowercase letters mean ");
        info.setBounds(x,20,300,25);
        panel.add(info);

        info = new JLabel("the letter is in the word and in the right place ");
        info.setBounds(x,35,300,25);
        panel.add(info);

        label = new JLabel("The letters that are not in the word are: ");
        label.setBounds(40,530,300,25);
        panel.add(label);

        unused = new JLabel("");
        unused.setBounds(40,560,300,25);
        panel.add(unused);



        frame.setVisible(true);
    }
    public void updateGame(String[][] asGrid) {
        for (int i = 0; i < buttonArray[0].length; i++) {
            for (int j = 0; j < buttonArray.length; j++) {
                buttonArray[j][i].setText(asGrid[j][i]);
            }
        }

    }
    public JFrame getFrame() {
        return frame;
    }
    public void unusedLetters(List<String> alUnusedLetters){
        unused.setText(String.valueOf(alUnusedLetters));
    }
    public void setWinStatus(String sWinStatus) {
        winStatus = new JOptionPane(sWinStatus);
        winStatus.setBounds(7,450,300,25);
//        panel.add(winStatus);
        JOptionPane.showMessageDialog(winStatus, sWinStatus);

    }

}
