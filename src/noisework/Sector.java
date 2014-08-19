/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import net.nexustools.io.DataOutputStream;
import net.nexustools.io.MemoryStream;

/**
 *
 * @author aero
 */
public class Sector {
    // 256 chunks
    Chunk[] chunks;
    long gentime;
    
    int x, z;
    
    public Sector(int x, int z){
        this.x=x;
        this.z=z;
        long start = System.currentTimeMillis();
        chunks = new Chunk[256];
        for(int i = 0; i < 256; i++){
            chunks[i] = new Chunk(((i&0xF0)>>4)+x*16, ((i&0x0F))+z*16);
        }
        gentime = System.currentTimeMillis() - start;
    }
    
    public void write(DataOutputStream out) throws IOException{
        out.writeInt(x);
        out.writeInt(z);
        MemoryStream ms = new MemoryStream();
        GZIPOutputStream gout = new GZIPOutputStream(ms.createOutputStream());// gzip streams need to be initialized at the right times otherwise they corrupt
        for(int i = 0; i < 256; i++){
            chunks[i].write(gout);
        }
        gout.flush();
        gout.close();
        byte[] data = ms.toByteArray();
        out.writeInt(data.length);
        out.write(data);
    }
    
}
