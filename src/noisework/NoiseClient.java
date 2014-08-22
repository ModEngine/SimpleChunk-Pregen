/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.net.Socket;
import net.nexustools.io.net.PacketRegistry;
import net.nexustools.io.net.Server;
import net.nexustools.io.net.work.WorkClient;
import net.nexustools.io.net.work.WorkServer;
import net.nexustools.runtime.RunQueue;

/**
 *
 * @author aero
 */
public class NoiseClient extends WorkClient {
    
    // Client specific statstics held by server
    int fastestTrack;

    public NoiseClient(Socket socket, WorkServer server) throws IOException {
        super(socket, server);
    }
    public NoiseClient(String host, int port, Server.Protocol protocol, RunQueue runQueue, PacketRegistry packetRegistry) throws IOException {
        super(host, port, protocol, runQueue, packetRegistry);
    }
    
}
