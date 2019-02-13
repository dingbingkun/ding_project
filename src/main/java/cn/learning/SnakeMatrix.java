package cn.learning;

import java.util.Scanner;

/**
 * 蛇型矩阵
 */
public class SnakeMatrix {
    public static void main(String[] args) {
        System.out.print("请输入一个正整数：");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] a = snakMatrix(n);
        for(int i = 0 ; i<n ; i++){
            for(int j = 0 ; j<n ; j++){
                System.out.print(a[j][i]+"\t");
            }
            System.out.println();
        }
    }

    private static int[][] snakMatrix(int n){
        int[][] result = new int[n][n];
        int number = 1;//起始数
        int axisX = -1;//X轴
        int axisY = 0;//Y轴
        int direction = 0;//方向，0右，1下，2左，3上
        while (number<=n*n){
            switch (direction){
                case 0:
                    while (++axisX<n&&result[axisX][axisY]==0){
                        result[axisX][axisY] = number;
                        number++;
                    }
                    axisX--;
                    direction = 1;
                    break;
                case 1:
                    while (++axisY<n&&result[axisX][axisY]==0){
                        result[axisX][axisY] = number;
                        number++;
                    }
                    axisY--;
                    direction = 2;
                    break;
                case 2:
                    while (--axisX>=0&&result[axisX][axisY]==0){
                        result[axisX][axisY] = number;
                        number++;
                    }
                    axisX++;
                    direction = 3;
                    break;
                case 3:
                    while (--axisY>=0&&result[axisX][axisY]==0){
                        result[axisX][axisY] = number;
                        number++;
                    }
                    axisY++;
                    direction = 0;
                    break;
            }
        }

        return result;
    }
}
