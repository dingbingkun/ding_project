package cn.learning;

import java.util.Arrays;

/**
 * 排序
 */
public class Sort {
    public static void main(String[] args) {
        int[] arr = {12,85,25,16,34,23,49,95,17,61};
        long startTime = System.currentTimeMillis();
        quickSort(arr,0,arr.length-1);
        long endTime = System.currentTimeMillis();
        System.out.println("排序结果："+ Arrays.toString(arr) +"\n排序耗时："+(endTime-startTime)+"ms");
    }

    /**
     * 快速排序-三数取中法
     * @param arr 需要排序数组
     * @param left 左指针
     * @param right 右指针
     */
    private static void quickSort(int[] arr,int left,int right){
        if(left<right){
            //第一步：获取左端数、中间数、右端数，并排序,将中间数放到右端数左边作为枢纽值
            dealPivot(arr, left, right);
            //第二步：比较左边所有大于中间数数挪到枢纽值右边
            int i = left;//左
            int j = right -1;//右
            int pivot =right -1;//枢纽值
            while (i<j){
                //从左边开始找大于枢纽值的数
                while(true){
                    if (arr[++i] >= arr[pivot]) break;
                }
                //从右边开始找小于枢纽值的数
                while(true){
                    if (j <= left || arr[--j] <= arr[pivot]) break;
                }
                if(i<j){
                    swap(arr,i,j);
                }
            }
            if (i < right) {
                swap(arr, i, right - 1);
            }
            quickSort(arr,left,i-1);
            quickSort(arr,i+1,right);
        }
    }

    private static void dealPivot(int[] arr,int left,int right){
        int mid = (left+right)/2;
        //排序
        if(arr[left]>arr[mid]){
            swap(arr,left,mid);
        }
        if(arr[left]>arr[right]){
            swap(arr,left,right);
        }
        if(arr[mid]>arr[right]){
            swap(arr,mid,right);
        }
        swap(arr,mid,right-1);
    }

    private static void swap(int[] arr,int a ,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
