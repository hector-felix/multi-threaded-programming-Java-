/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSC29501_Assignment3;

import java.util.Scanner;

public class MultiThread extends Thread {

    public static int max = 10000;
    private int low;
    private int high;
    private static int currentMax;

    public MultiThread(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public void run() {
        long startTime = System.nanoTime();
        
        int runcurrentMax = 0;

        for (int i = low; i <= high; i++) {
            if (checkIfPrime(i) && lastDigit(i) == 7 && i > runcurrentMax) {
                    runcurrentMax = i;
            }
            newMax(runcurrentMax);
        }

        long endTime = System.nanoTime();
        //long endTime = System.currentTimeMillis();
        long finalTime = endTime - startTime;

        System.out.println("Largest Prime Between [" + low + "," + high + "] is: " + runcurrentMax + "\n  Execution Time: " + finalTime * .000001 + " Milliseconds");
        //System.out.println("Largest Prime Between [" + low + "," + high + "] is: " + largePrime + "\n  Execution Time: " + finalTime + " Nanoseconds");
    }

    synchronized public void newMax(int currentMax) {
        MultiThread.currentMax = currentMax;
    }

    public static int lastDigit(int n) {
        return (n % 10);
    }

    
    public static boolean checkIfPrime(int n) {
        int root = (int) Math.sqrt(n);
        for (int i = 2; i <= root; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public int getCurrentMax()
	{
		return MultiThread.currentMax;
	}

    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        long startTime = System.nanoTime();

        System.out.println("Number of Available Processors: " + Runtime.getRuntime().availableProcessors());

//        Scanner s = new Scanner(System.in);
//
//        System.out.print("Please Specify Number of Threads: ");
//        int threadCount = s.nextInt();
//        System.out.println();
        
      int threadCount = 4;

        MultiThread[] a = new MultiThread[threadCount];

        int numbersInThread = max / threadCount;
        int start = 1; //starting for range in first thread
        int end = start + numbersInThread - 1;

        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                end = max;
            }

            a[i] = new MultiThread(start, end);
            start = end + 1; //Next thread staring point
            end = start + numbersInThread - 1;
        }

        for (int i = 0; i < threadCount; i++) {
            a[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            a[i].join();
        }

        long endTime = System.nanoTime();
        long finalTime = endTime - startTime;

        System.out.println("*****************************************\nLargest Prime in Range [1,10000] : " + a[1].getCurrentMax());
        System.out.println("\nProgram Execution Time: " + finalTime * .000001 + " Milliseconds\n");
    }
}
