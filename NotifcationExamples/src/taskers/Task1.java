/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;
import notifcationexamples.ThreadState;

/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    private ThreadState state;
    
    private Notifiable notificationTarget;
    
    public Task1(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
        this.state = ThreadState.STOPPED;
    }
    
    @Override
    public void run() {
        this.state = ThreadState.RUNNING;
        doNotify("Task1 start.");
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task1: " + i);
            }
            
            if (exit) {
                return;
            }
        }
        doNotify("Task1 done.");
        this.state = ThreadState.STOPPED;
    }
    
    public void end() {
        this.state = ThreadState.STOPPED;
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(String message) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> {
                notificationTarget.notify(message);
            });
        }
    }
    
    public ThreadState getThreadState(){
        return state;
    }
}
