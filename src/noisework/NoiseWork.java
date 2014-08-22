/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import net.nexustools.concurrent.PropList;
import net.nexustools.data.AdaptorException;
import net.nexustools.data.annote.FieldStream;
import net.nexustools.io.DataOutputStream;
import net.nexustools.io.MemoryStream;
import net.nexustools.io.net.Client;
import net.nexustools.io.net.work.WorkResponsePacket;
import net.nexustools.io.net.work.WorkClient;
import net.nexustools.io.net.work.WorkPacket;
import net.nexustools.runtime.RunQueue;
import net.nexustools.runtime.ThreadedRunQueue;

/**
 *
 * @author aero
 */

class SectorJob implements Runnable {
    
    final AtomicInteger counter;
    final Thread waitingThread;
    final PropList<Sector> sectors;
    int x, z;
    public SectorJob(int x, int z, Thread thread, AtomicInteger counter, PropList<Sector> sectors){ // whats wrong with this?
        this.counter = counter;
        waitingThread = thread;
        this.sectors = sectors;
        this.x=x;
        this.z=z;
    }

    public Sector sect;
    
    @Override
    public void run() {
        try {
            sectors.push(new Sector((short)x,(short)z));
        } finally {
            // Ensure it counts down, but allow exceptions to get thrown for debugging
            if(counter.decrementAndGet() <= 0)
                waitingThread.interrupt(); // Wakeup from sleeping effeciently
        }
    }
    
}

public class NoiseWork extends WorkPacket {
    
    final static RunQueue runQueue = new ThreadedRunQueue("Work", Runtime.getRuntime().availableProcessors());
    
    // These field will automatically be written thanks to the annotation
    @FieldStream(staticField = true)
    protected short clientCount;
    @FieldStream(staticField = true)
    protected short remainingTracksTotal;
    @FieldStream(staticField = true)
    protected short finishedTracks; // entire server
    @FieldStream(staticField = true)
    protected short tracksThisSession; // tracks completed by this client
    @FieldStream(staticField = true)
    protected short trackRecord; // most tracks completed by a single client (count)
    @FieldStream(staticField = true)
    protected int speedRectord; // fastest time to complete a track (ms)
    @FieldStream(staticField = true)
    protected short trackx;
    @FieldStream(staticField = true)
    protected short trackz;

    @Override
    protected WorkResponsePacket processWork(final WorkClient client) {
        // break apart things and send it to threads?
        
        final PropList<Sector> sectors = new PropList();
        final AtomicInteger atomicInteger = new AtomicInteger();
        for(int x = 0; x < 4; x++){
            for(int z = 0; z < 4; z++){
                atomicInteger.incrementAndGet();
                runQueue.push(new SectorJob(x + trackx*4, z + trackz*4, Thread.currentThread(), atomicInteger, sectors));
            }
        }
        
        while(atomicInteger.get() > 0)
            try {
                Thread.sleep(1000*60*30); // Sleep for 30 minutes
            } catch(InterruptedException wokeup) {}
        
        final Track t = new Track();
        for(Sector s : sectors){
            t.setSector(s.x - trackx*4, s.z - trackz*4, s);
        }
        
        return new WorkResponse(t);
    }
    
}
