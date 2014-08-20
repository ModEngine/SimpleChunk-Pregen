/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import net.nexustools.concurrent.PropList;
import net.nexustools.io.net.PacketRegistry;
import net.nexustools.io.net.Server.Protocol;
import net.nexustools.net.work.WorkAppDelegate;
import net.nexustools.net.work.WorkClient;
import net.nexustools.net.work.WorkPacket;
import net.nexustools.net.work.WorkServer;
import net.nexustools.runtime.RunQueue;
import net.nexustools.utils.Pair;
import net.nexustools.utils.log.Logger;

/**
 *
 * @author aero
 */
public class NoiseWorkDelegate extends WorkAppDelegate {

    boolean checkIfComplete(int x, int z){
        String folder = String.valueOf(x);
        if(folder.length()>3) folder = folder.substring(0,3);
        return new File("output" + File.separator + folder + File.separator + x + "."+ z).exists();
    }
    
    PropList<Point> work = new PropList<Point>() {
        {
            String range = System.getProperty("range", "16");
            for(int x = -Integer.valueOf(System.getProperty("minx", range)); x <= Integer.valueOf(System.getProperty("maxx", range)); x++){
                for(int y= -Integer.valueOf(System.getProperty("miny", range)); y <= Integer.valueOf(System.getProperty("maxy", range)); y++){
                    if(checkIfComplete(x*4, y*4))
                        Logger.info("Work Already Complete:", x, y);
                    else
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
        return new NoiseClient(name + "Client", host, port, Protocol.TCP, runQueue, packetRegistry);
    }

    @Override
    protected WorkClient createClient(Socket socket, WorkServer server) throws IOException {
        return new NoiseClient(name + "Client", socket, server);
    }

    @Override
    protected void populate(PacketRegistry registry) throws NoSuchMethodException {
        super.populate(registry);
        registry.register(NoiseWork.class);
        registry.register(WorkResponse.class);
        registry.register(SectorPacket.class);
    }

    @Override
    public WorkPacket nextWork(WorkClient workClient) {
        Point wp = work.pop();
        if(wp == null)
            return null;
        
        NoiseWork noisePacket = new NoiseWork();
        noisePacket.finishedTracks = 2;
        noisePacket.trackx = (short) wp.getX();
        noisePacket.trackz = (short) wp.getY();
        
        return noisePacket;
    }
    
}
