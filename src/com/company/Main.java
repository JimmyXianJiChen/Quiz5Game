package com.company;

import com.company.gametest9th.utils.GameKernel;

import javax.swing.*;
import java.awt.event.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("飛機射擊遊戲");
        frame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //NONE(4),
        //        UP(3),
        //        DOWN(0),
        //        LEFT(1),
        //        RIGHT(2);
        int[][] commands = {
//                {KeyEvent.VK_A, 1},
//                {KeyEvent.VK_W, 3},
//                {KeyEvent.VK_S, 0},
//                {KeyEvent.VK_D, 2},
                {KeyEvent.VK_LEFT, Global.LEFT},
                {KeyEvent.VK_RIGHT, Global.RIGHT},
//                {KeyEvent.VK_SPACE, Global.SPACE},
//                {KeyEvent.VK_UP, Global.UP},
                {KeyEvent.VK_SPACE, Global.COMMAND_SPACE}
        };

        GameCenter gi = new GameCenter();
        GameKernel kernel = new GameKernel.Builder(gi, Global.LIMIT_DELTA_TIME, Global.NANOSECOUND_PER_UPDATE)
                .initListener(commands)
                .enableMouseTrack(gi)
                .enableKeyboardTrack(gi)
                .trackChar()
                .gen();


        frame.add(kernel);
        frame.setVisible(true);
        kernel.run(Global.IS_DEBUG);
    }
}
