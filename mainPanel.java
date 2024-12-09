package saper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class mainPanel extends JFrame{
    private JPanel MainPanel;
    private JPanel inner;
    private JPanel bolder_inner;
    private JPanel bolder_top;
    private JPanel top;
    private JRadioButton flagRadioButton;
    private JLabel TimeLabel;
    private JLabel FlagsLabel;
    private JButton replayButton;
    private Font font = new Font("Arial",Font.BOLD, 12);

    JButton [][] buttons;

    short kol_vo = 10;

    int [][] mins;

    boolean RadioButton1 = false;
    boolean start = false;
    Timer timer;
    int seconds = 0, victory = 0, kol_vo_but_close = 0;

    boolean [][] st_map = new boolean[9][9];

    public mainPanel(){
        inner.setLayout(new GridLayout(9,9));
        inner.setPreferredSize(new Dimension(270,270));
        inner.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(MainPanel);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("main");
        mins = new int[9][9];
        buttons = new JButton[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
               buttons[i][j] = new JButton();
               buttons[i][j].addMouseListener(new MoveButton(i, j));
               buttons[i][j].setFont(font);
               buttons[i][j].setFocusable(false);
               buttons[i][j].setMargin(new Insets(0,0,0,0));
               st_map[i][j] = true;
               inner.add(buttons[i][j]);
            }
        }
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kol_vo = 10;
                start = false;
                TimeLabel.setText("Time: 0");
                FlagsLabel.setText("Flags: 10");
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        buttons[i][j].setText("");
                        buttons[i][j].setForeground(new Color(0,0,0));
                        st_map[i][j] = true;
                        mins[i][j] = 0;
                    }
                }
                if(timer != null)
                    timer.cancel();
                seconds = 0;
                kol_vo_but_close = 0;
                victory = 0;
            }
        });
        TimeLabel.setText("Time: 0");
        FlagsLabel.setText("Flags: 10");
        pack();
        setVisible(true);
    }
    public static void main(String[] args){
        new mainPanel();
    }

    public void clear(int i, int j){
        buttons[i][j].setText(""+mins[i][j]);
        st_map[i][j] = false;
        kol_vo_but_close++;
        if(mins[i][j] != 0)
            return;
        if(j == 0)
            st_map[i][j] = false;
        if(i<8){
            if(st_map[i+1][j] != false)
                clear(i+1, j);
        }
        if(i > 0)
            if(st_map[i-1][j] != false)
                clear(i-1, j);
        if(i < 8 && j < 8)
            if(st_map[i+1][j+1] != false)
                clear(i+1, j+1);
        if(i < 8 && j > 0)
            if(st_map[i+1][j-1]!= false)
                clear(i+1, j-1);
        if(i > 0 && j < 8)
            if(st_map[i-1][j+1] != false)
                clear(i-1, j+1);
        if(i>0 && j > 0)
            if(st_map[i-1][j-1] != false)
                clear(i-1, j-1);
        if(j<8){
            if(st_map[i][j+1] != false)
                clear(i, j+1);
        }
        if(j > 0) {
            if(st_map[i][j-1] != false)
                clear(i, j-1);}
    }


    private class MoveButton implements MouseListener {

        int i;
        int j;

        public MoveButton(int i, int j){
            this.i = i;
            this.j = j;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!start){
                mins = new int[9][9];
                Random rnd = new Random();
                for (int k = 0; k < 9; k++) {
                    for (int l = 0; l < 9; l++) {
                        mins[k][l] = 0;
                    }
                }
                while(kol_vo > 0){
                    int k = rnd.nextInt(9);
                    int l = rnd.nextInt(9);
                    if(k == i || l == j || k==i-1 || k == i+1 || l == j-1 || l == j+1 || mins[k][l]==-1)
                        continue;
                    mins[k][l] = -1;
                    kol_vo--;
                }
                for (int k = 0; k <9; k++) {
                    for (int l = 0; l < 9; l++) {
                        if (mins[k][l] == -1) {

                            //buttons[k][l].setText(""+mins[k][l]);
                            continue;
                        }
                        if (k != 0) {
                            if (mins[k - 1][l] == -1)
                                mins[k][l]++;
                        }
                        if (k != 8) {
                            if (mins[k + 1][l] == -1)
                                mins[k][l]++;
                        }
                        if (l != 0) {
                            if (mins[k][l - 1] == -1)
                                mins[k][l]++;
                        }
                        if (l != 8) {
                            if (mins[k][l + 1] == -1)
                                mins[k][l]++;
                        }
                        if (k != 0 && l != 0) {
                            if (mins[k - 1][l - 1] == -1)
                                mins[k][l]++;
                        }
                        if (k != 0 && l != 8) {
                            if (mins[k - 1][l + 1] == -1)
                                mins[k][l]++;
                        }
                        if (k != 8 && l != 0) {
                            if (mins[k + 1][l - 1] == -1)
                                mins[k][l]++;
                        }
                        if (k != 8 && l != 8) {
                            if (mins[k + 1][l + 1] == -1)
                                mins[k][l]++;
                        }
                    }
                }
                start = true;
                kol_vo = 10;
                clear(i, j);
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if(seconds != 999) {
                            seconds++;
                            TimeLabel.setText("Time: " + seconds);
                        }
                    }
                }, 0, 1000);

            }
            if(victory == 0)
            {
                RadioButton1 = flagRadioButton.isSelected();
                if (RadioButton1) {
                    if(buttons[i][j].getText().equals("flag")) {
                        buttons[i][j].setText("");
                        kol_vo++;
                    }
                    else
                    {
                        buttons[i][j].setText("flag");
                        kol_vo--;
                    }
                    FlagsLabel.setText("Flags: " + kol_vo);
                } else {
                    if(st_map[i][j] == false)
                        kol_vo_but_close--;
                    st_map[i][j] = false;
                    if(!buttons[i][j].getText().equals("flag"))
                        buttons[i][j].setText("" + mins[i][j]);
                    kol_vo_but_close++;

                    if (mins[i][j] == -1) {
                        FlagsLabel.setText("DEFEAT");
                        victory = -1;
                        timer.cancel();
                        for (int k = 0; k < 9; k++) {
                            for (int l = 0; l < 9; l++) {
                                if(buttons[k][l].getText().equals("flag") && mins[k][l] == -1) {
                                    buttons[k][l].setForeground(new Color(82, 194, 6));
                                    continue;
                                }
                                if(buttons[k][l].getText().equals("flag") && mins[k][l] != -1) {
                                    buttons[k][l].setForeground(new Color(222, 6, 0));
                                    continue;
                                }
                                if(mins[k][l] == -1) {
                                    buttons[k][l].setForeground(new Color(232, 201, 0));
                                    buttons[k][l].setText("flag");
                                }
                            }
                        }
                        return;
                    }
                    if(kol_vo_but_close == 71){
                        victory = 1;
                        FlagsLabel.setText("Victory");
                        timer.cancel();
                        for (int k = 0; k < 9; k++) {
                            for (int l = 0; l < 9; l++) {
                                if(mins[k][l]==-1)
                                    buttons[k][l].setText("flag");
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
