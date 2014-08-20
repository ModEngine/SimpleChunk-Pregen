/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import net.nexustools.concurrent.PropList;
import net.nexustools.io.net.PacketRegistry;
import net.nexustools.io.net.Server.Protocol;
import net.nexustools.net.work.WorkAppDelegate;
import net.nexustools.net.work.WorkClient;
import net.nexustools.net.work.WorkPacket;
import net.nexustools.net.work.WorkServer;
import net.nexustools.utils.Pair;

/**
 *
 * @author aero
 */
public class NoiseWorkDelegate extends WorkAppDelegate {

    boolean checkIfComplete(int x, int z){
        boolean complete = false;
        
        String folder = String.valueOf(x);
        if(folder.length()>3) folder = folder.substring(0,3);
        String filename = x + "."+ z;
        
        complete = new File("output" + File.separator + folder + File.separator + filename).exists();
        
        return complete;
    }
    
    PropList<Point> work = new PropList<Point>() {
        {
            for(int x = -64; x < 64; x++){
                for(int y=  -64; y < 64; y++){
                    if(!checkIfComplete(x*4, y*4))
                        push(new Point(x,y));
                }
            }
        }
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new NoiseWorkDelegate(args).mainLoop();
    }

    public NoiseWorkDelegate(String[] args) {
        super(args, "NoiseWork", "NexusTools");
    }

    @Override
    protected WorkClient createClient(String host, int port) throws IOException {
        return new NoiseClient(name + "-NoiseClient", host, port, Protocol.TCP, runQueue, packetRegistry);
    }

    @Override
    protected WorkClient createClient(Pair socket, WorkServer server) throws IOException {
        return new NoiseClient(name + "-NoiseClient", socket, server);
    }

    @Override
    protected void populate(PacketRegistry registry) throws NoSuchMethodException {
        super.populate(registry);
        registry.register(NoiseWork.class);
        registry.register(WorkResponse.class);
    }

    @Override
    public WorkPacket nextWork(WorkClient workClient) {
        NoiseClient client = (NoiseClient)workClient;
        NoiseWork noisePacket = new NoiseWork();
        noisePacket.finishedTracks = 2;
        Point wp = work.pop();
        noisePacket.trackx = (short) wp.getX();
        noisePacket.trackz = (short) wp.getY();
        
        return noisePacket;
    }
    
}
