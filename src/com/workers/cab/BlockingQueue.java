package com.workers.cab;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> { 
	  
    private List<T> queue = new LinkedList<T>(); 
    private int limit = 10; 
  
    public BlockingQueue(int limit) 
    { 
        this.limit = limit; 
    } 
  
    public synchronized void add(T item) 
        throws InterruptedException 
    { 
        while (this.queue.size() == this.limit) { 
            wait(); 
        } 
        if (this.queue.size() == 0) { 
            notifyAll(); 
        } 
        this.queue.add(item); 
    } 
  
    public synchronized T remove() 
        throws InterruptedException 
    { 
        while (this.queue.size() == 0) { 
            wait(); 
        } 
        if (this.queue.size() == this.limit) { 
            notifyAll(); 
        } 
  
        return this.queue.remove(0); 
    } 
    
    public synchronized T peek() 
            throws InterruptedException 
        { 
    	while (this.queue.size() == 0) { 
            wait(); 
        } 
        if (this.queue.size() == this.limit) { 
            notifyAll(); 
        } 
            return this.queue.get(0); 
        } 
} 
