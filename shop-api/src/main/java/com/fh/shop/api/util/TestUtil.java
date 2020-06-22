package com.fh.shop.api.util;

import java.util.Scanner;

public class TestUtil {
    /*
        第一题 用递归实现字符串倒转，这道题应该是最简单的了
    */
    public static String reverse(String originStr) {
        if (originStr == null || originStr.length() == 1) {
            return originStr;
        }
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    /*第二题 一个有n级的台阶，一次可以走1级、2级或3级，问走完n级台阶有多少种走法。*/
    public static int countWays(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else {
            return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
    }

    public static String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static int i = 0;
    public static int[] monthday1 = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int[] monthday2 = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //查看距离当前天数的差值
    public static String distance(int year, int month, int day, int newMonth, int newDay) {
        int sum = 0; //设定初始距离天数
        if (month + newMonth >= 12) {
            if (((year + 1) % 4 == 0 && (year + 1) % 100 != 0) || (year + 1) % 400 == 0) {
                sum += 366 + newDay;
                for (int i = 0; i < newMonth - 12; i++) {
                    sum += monthday1[month + i];
                }
            } else {
                sum += 365 + newDay;
                for (int i = 0; i < newMonth - 12; i++) {
                    sum += monthday1[month + i];
                }
            }
        } else {
            for (int i = 0; i < newMonth; i++) {
                sum += monthday1[month + i];
            }
            sum += newDay;
        }
        return week[sum % 7];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入当前年份");
        int year = scanner.nextInt();
        System.out.println("请输入当前月份");
        int month = scanner.nextInt();
        System.out.println("请输入当前天数");
        int day = scanner.nextInt();
        System.out.println("请输入当前是星期几：以数字表示，如：星期天 为 0");
        int index = scanner.nextInt();
        System.out.println("今天是：" + year + "-" + month + "-" + day + "  " + week[index]);

        System.err.println("请输入相隔月份");
        int newMonth = scanner.nextInt();
        System.out.println("请输入剩余天数");
        int newDay = scanner.nextInt();

        System.out.println("经过" + newMonth + "月" + newDay + "天后，是" + distance(year, month, day, newMonth, newDay));

        System.out.println(reverse("hello"));
        System.out.println(countWays(5)); // 13

        int[][] data = {
                {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
                {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
        };

        Scanner sc = new Scanner(System.in);

        System.out.print("请输入年月日(1980 11 28): ");
        int y = sc.nextInt();
        int m = sc.nextInt();
        int date = sc.nextInt();
        int[] daysOfMonth = data[(y % 4 == 0 && y % 100 != 0 || y % 400 == 0) ? 1 : 0];
        int sum = 0;
        for (int i = 0; i < m - 1; i++) {
            sum += daysOfMonth[i];
        }
        sum += date;
        System.out.println(sum);
        sc.close();

    }

}
