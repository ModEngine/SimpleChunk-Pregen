/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import net.nexustools.io.DataInputStream;
import net.nexustools.io.DataOutputStream;
import net.nexustools.io.net.PacketRegistry;
import net.nexustools.io.net.Server;
import net.nexustools.net.work.WorkClient;
import net.nexustools.net.work.WorkServer;
import net.nexustools.utils.Pair;

/**
 *
 * @author aero
 */
public class NoiseClient extends WorkClient {
    
    // Client specific statstics held by server
    int fastestTrack;

    public NoiseClient(String name, Pair<DataInputStream, DataOutputStream> socket, WorkServer server) {
        super(name, socket, server);
    }
    public NoiseClient(String name, final Pair<DataInputStream,DataOutputStream> socket, PacketRegistry packetRegistry) {
        super(name, socket, packetRegistry);
    }
    public NoiseClient(String name, String host, int port, Server.Protocol protocol, PacketRegistry packetRegistry) throws IOException {
        super(name, host, port, protocol, packetRegistry);
    }
    
}
