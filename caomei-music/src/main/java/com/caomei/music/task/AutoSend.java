package com.caomei.music.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AutoSend {


    public static void main(String[] args) throws AWTException {

        String s = readFileContent("config.txt");
        JSONObject jsonObject = JSON.parseObject(s);

        System.out.println("程序执行 请将鼠标移动到需要进行群发的群列表上");
        System.out.println("开始执行程序了哦 请不要移动鼠标");
        Robot robot = new Robot();// 创建Robot对象

        robot.delay(jsonObject.getInteger("startDelayTime"));// 启动后 延迟时间

        PointerInfo pinfo = MouseInfo.getPointerInfo();
        int bashMx = pinfo.getLocation().x;
        int bashMy = pinfo.getLocation().y;
        int mx = bashMx;
        int my = bashMy;

        //添加 复制
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(jsonObject.getString("sendMsg"));
        clipboard.setContents(transferable, null);

        int n = jsonObject.getInteger("sendCount"); //发送消息次数
        int time = jsonObject.getInteger("sendDelayTime");//两次轰炸之间相隔的时间，单位为毫秒
        int wheelNum = jsonObject.getInteger("wheelNum");//滑动鼠标间隔次数
        int waitTime = jsonObject.getInteger("waitTime");//滑动鼠标间隔次数
        do {
            for (int i = 1; i <= n; i++) {
                robot.mouseMove(mx, my);
                my += jsonObject.getInteger("mouseMove");
                robot.mousePress(InputEvent.BUTTON1_MASK);//按下左键
                robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键

                // 以下两行按下了ctrl+v，完成粘贴功能
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);

                robot.keyRelease(KeyEvent.VK_CONTROL);// 释放ctrl按键
                robot.keyRelease(KeyEvent.VK_V);

                robot.delay(time);
                robot.keyPress(KeyEvent.VK_ENTER);// 回车
                robot.keyRelease(KeyEvent.VK_ENTER);

                if (i % wheelNum == 0) {
                    robot.mouseWheel(jsonObject.getInteger("mouseWheel"));
                    my = bashMy;
                }
            }
            robot.mouseWheel(100);
            robot.delay(waitTime);
        } while (jsonObject.getBoolean("isRun"));

    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
