package com.caomeiprincess;

import java.util.function.Supplier;

public class Car {
    public Car getCar(Supplier<Car> supplier){
       return supplier.get();
    }

    public static void main(String[] args) {
        String costTimes="86400";
        String costTimesStr="";
        int num = 86400*5+60*60*4+60*6+53;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = num % 60;
        num -= second;
        if(num > 0) {
            num /= 60;
            minute = num % 60;
            num -= minute;
            if(num > 0) {
                num /= 60;
                hour = num % 24;
                num -= hour;
                if(num>0){
                    num /= 24;
                    day = num;
                }
            }
        }

        if(day>0){
            costTimesStr+=day+"天";
        }
        if(hour>0){
            costTimesStr+=hour+"小时";
        }
        if(minute>0){
            costTimesStr+=minute+"分";
        }
        if(second>0){
            costTimesStr+=second+"秒";
        }
        System.out.println(costTimesStr);
    }
}
