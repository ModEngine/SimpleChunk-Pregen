/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author aero
 */
public class Chunk {
    // 32768 blocks
    boolean[] blocks;
    
    public Chunk(int x, int z){
        blocks = new boolean[32768];
        int realx, realz;
        for(int sx = 0; sx < 16; sx++){
            for(int sz = 0; sz < 16; sz++){
                for(int y = 127; y > 0; y--){
                    realx = sx+x*16;
                    realz = sz+z*16;
                    blocks[sx << 11 | sz << 7 | y] = NoiseOctaves.noise(realx, y, realz)>0.85f; // skyblob value
                }
            }
        }
    }

    void write(OutputStream gout) throws IOException {
        BitWriter bw = new BitWriter(32768);
        for(int i = 0; i < 32768; i++)bw.writeBit(blocks[i]);
        gout.write(bw.data);
        gout.flush();
    }
    
}
