/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;


/**
 * 
 * @author Pablo Rojas Martínez
 */
public class Item extends Thread{

    private int buffer = -1; // shared by producer and consumer threads
    private int occupiedBufferCount = 0; // count of occupied buffers

    // place value into buffer
    public synchronized void set(int value) {
        // for output purposes, get name of thread that called this method
        String name = Thread.currentThread().getName();

        // while there are no empty locations, place thread in waiting state
        while (occupiedBufferCount == 1) {

            // output thread information and buffer information, then wait
            try {

                //this thread waits
                wait();

            } // if waiting thread interrupted, print stack trace
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }

        } // end while

        //if arrive to this place, it means that buffer is empty
        buffer = value; // set new buffer value

        // indicate producer cannot store another value
        // until consumer retrieves current buffer value
        ++occupiedBufferCount;

        notify(); // tell waiting thread to enter ready state

    } // end method set; releases lock on SynchronizedBuffer

    // return value from buffer
    public synchronized int get() {
        // for output purposes, get name of thread that called this method
        String name = Thread.currentThread().getName();

        // while no data to read, place thread in waiting state
        while (occupiedBufferCount == 0) {

            // output thread information and buffer information, then wait
            try {

                wait();
            } // if waiting thread interrupted, print stack trace
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }

        } // end while

        // indicate that producer can store another value
        // because consumer just retrieved buffer value
        --occupiedBufferCount;

        notify(); // tell waiting thread to become ready to execute

        return buffer;

    } // end method get; releases lock on SynchronizedBuffer


}
