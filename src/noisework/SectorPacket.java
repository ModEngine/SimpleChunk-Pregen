/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import net.nexustools.data.annote.FieldStream;
import net.nexustools.io.net.Server;
import net.nexustools.io.net.Client;
import net.nexustools.io.net.Packet;

/**
 *
 * @author kate
 */
public class SectorPacket extends Packet {
    
    @FieldStream(staticField = true)
    protected int x;
    @FieldStream(staticField = true)
    protected int z;

    @Override
    protected void recvFromServer(Client client) {
        // TODO: Implement
    }

    @Override
    protected void recvFromClient(Client client, Server server) {
    }

    
}
