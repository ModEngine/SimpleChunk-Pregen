/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.io.InputStream;

public class BitReader {   
       
    private InputStream bis;   
    private int readCount;   
    private byte cacheByte;   
    public BitReader(InputStream bis){   
        this.bis = bis;   
        readCount = 8;   
    }
    
    public void close() throws IOException{
        bis.close();
    }
    /**  
     *   
     * @return 0 or 1,-1 means EOF  
     */   
    public byte readBit(){   
        if(readCount == 8){   
            int value = -1;   
            try {   
                value = bis.read();   
            } catch (IOException e) {   
                // TODO Auto-generated catch block   
                e.printStackTrace();   
            }   
            if(value == -1){   
                return -1;   
            }   
            cacheByte = (byte)value;    
            readCount = 0;   
        }   
        int mask =  0x80 >>> readCount;   
        readCount ++;   
        return (byte)((cacheByte & mask) >>> ( 8 - readCount));   
           
    }   
    
}
