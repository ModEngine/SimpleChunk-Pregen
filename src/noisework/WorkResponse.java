/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.File;
import java.io.IOException;
import net.nexustools.data.AdaptorException;
import net.nexustools.io.DataInputStream;
import net.nexustools.io.DataOutputStream;
import net.nexustools.io.FileStream;
import net.nexustools.io.StreamUtils;
import net.nexustools.net.work.ResponsePacket;
import net.nexustools.net.work.WorkClient;
import net.nexustools.net.work.WorkPacket;
import net.nexustools.net.work.WorkServer;
import net.nexustools.utils.log.Logger;

/**
 *
 * @author aero
 */
public class WorkResponse extends ResponsePacket {

    private final byte[] data;
    public WorkResponse() {
        data = null;
    }
    public WorkResponse(byte[] data) {
        this.data = data;
    }
    
    @Override
    protected void handleResponse(WorkClient client, WorkServer server, WorkPacket work) {}

    @Override
    public void read(DataInputStream d, WorkClient client) throws UnsupportedOperationException, IOException {
        super.read(d, client);
        
        Logger.quote("Finished Work", this);
        for(int i = 0; i < 16; i++){
            int x = d.readInt(), z = d.readInt(), length = d.readInt(); // pleas be sane
            // new DB format based on old successful format :u
            String folder = String.valueOf(x);
            if(folder.length()>3) folder = folder.substring(0,3);
            String filename = x + "|"+ z;
            
            // The directory structure is created automatically, ONLY when in writing mode
            FileStream stream = new FileStream("output" + File.separator + folder + File.separator + filename, true);
            
            StreamUtils.copy(d, stream.createOutputStream(), length);
            stream.close();
            
        }
        
    }

    @Override
    public void write(DataOutputStream dataOutput, WorkClient client) throws UnsupportedOperationException, IOException {
        super.write(dataOutput, client);
        dataOutput.write(data);
    }
    
}
