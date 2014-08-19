/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

import java.io.IOException;
import java.io.OutputStream;
import net.nexustools.io.DataOutputStream;

/**
 *
 * @author aero
 */
public class Track {
    //4*4 sectors
    Sector[] sectors;
    
    public Track(){
        sectors = new Sector[16];
    }
    
    public void setSector(int x, int z, Sector s){
        sectors[((x<<2)|z)] = s;
    }
    
    public void write(DataOutputStream out) throws IOException{
        for(int i = 0; i < 16; i++)sectors[i].write(out);
        //// there its ready thanks btw, :u teamviewer fighting is annoying with how teamviewer works :\
    }
    
}
