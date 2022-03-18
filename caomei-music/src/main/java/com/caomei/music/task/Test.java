package com.caomei.music.task;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Scanner;


/**
 * @author hope
 * @since 2022/3/18
 */
public class Test {

    public static void main(String[] args) throws AWTException {
        System.out.println("程序执行  请将鼠标移动到需要进行群发的群列表上");
        Robot robot = new Robot();// 创建Robot对象
        System.out.println("对几个群群发");
        int n= new Scanner(System.in).nextInt();
//        int times = new Scanner(System.in).nextInt();//轰炸的次数，可以自己修改
        System.out.println("开始执行程序了哦  请不要移动鼠标");
        int time = 900; //两次轰炸之间相隔的时间，单位为毫秒

        robot.delay(3000);// 延迟三秒，主要是为了预留出打开窗口的时间，括号内的单位为毫秒
        PointerInfo pinfo = MouseInfo.getPointerInfo();
        int mx = pinfo.getLocation().x;
        int my = pinfo.getLocation().y;

        System.out.println("x:"+mx+",y:"+my);
//x:1700,y:423  x:1644,y:442   x:1785,y:448   x:1673,y:480
//        robot.mousePress(KeyEvent.BUTTON1_MASK);
        if(n>=4) n+=1;
        for (int i=1;i<=n;i++) {
            robot.mouseMove(mx, my);
            robot.mousePress(InputEvent.BUTTON1_MASK);//按下左键
            robot.mousePress(InputEvent.BUTTON1_MASK);//按下左键
            robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键
            my = my + 35;

//           System.out.println("正在进行第"+(i)+"个任务");
            for (int j = 0; j < 2; j++) {//循环次数

                // 以下两行按下了ctrl+v，完成粘贴功能
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);// 释放ctrl按键，像ctrl，退格键，删除键这样的功能性按键，在按下后一定要释放，不然会出问题。crtl如果按住没有释放，在按其他字母按键是，敲出来的回事ctrl的快捷键。
                robot.delay(time);// 延迟发送，不然会一次性全发布出去，因为电脑的处理速度很快，每次粘贴发送的速度几乎是一瞬间，所以给人的感觉就是一次性发送了全部。这个时间可以自己改，想几秒发送一条都可以
                robot.keyPress(KeyEvent.VK_ENTER);// 回车
                robot.keyRelease(KeyEvent.VK_ENTER);
            }


        }


    }

}
