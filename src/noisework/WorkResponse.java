/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.File;
import java.io.IOException;
import net.nexustools.io.DataInputStream;
import net.nexustools.io.DataOutputStream;
import net.nexustools.io.FileStream;
import net.nexustools.io.StreamUtils;
import net.nexustools.io.net.Client;
import net.nexustools.io.net.work.WorkResponsePacket;
import net.nexustools.io.net.work.WorkClient;
import net.nexustools.io.net.work.WorkPacket;
import net.nexustools.io.net.work.WorkServer;
import net.nexustools.utils.log.Logger;

/**
 *
 * @author aero
 */
public class WorkResponse extends WorkResponsePacket {

    private final Track data;
    public WorkResponse() {
        data = null;
    }
    public WorkResponse(Track data) {
        this.data = data;
    }
    
    @Override
    protected void handleResponse(WorkClient client, WorkServer server, WorkPacket work) {}

    @Override
    public void read(DataInputStream d, Client client) throws UnsupportedOperationException, IOException {
        super.read(d, client);
        
        Logger.quote("Finished Work", this);
        for(int i = 0; i < 16; i++){
            int x = d.readInt();
            int z = d.readInt();
            int length = d.readInt(); // pleas be sane
            System.out.println(x+"|"+z+"|"+length);
            // new DB format based on old successful format :u
            String folder = String.valueOf(x);
            if(folder.length()>3) folder = folder.substring(0,3);
            String filename = x + "."+ z;
            
            // The directory structure is created automatically, ONLY when in writing mode
            FileStream stream = new FileStream("output" + File.separator + folder + File.separator + filename, true);
            StreamUtils.copy(d, stream.createOutputStream(), length);
            stream.close();
        }
        
    }

    @Override
    public void write(DataOutputStream dataOutput, Client client) throws UnsupportedOperationException, IOException {
        super.write(dataOutput, client);
        data.write(dataOutput);
    }
    
}
