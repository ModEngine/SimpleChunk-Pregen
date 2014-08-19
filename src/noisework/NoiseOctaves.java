/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package noisework;

/**
 *
 * @author Luke
 */
public class NoiseOctaves {
    
    static InstancedSimplex is = new InstancedSimplex();
    
    public static final float res1D = 8129f;
    public static final float res2D = 256f;
    static final float lac = 2f;
    static final float persistance = 0.4f;
    
    public static float noise(int x, int y){ // x/y coordinate not float
        float interp = 0f;
        float amp = 1f;
        
        float xla = ((float)(x))/res2D;
        float yla = ((float)(y))/res2D;
        
        for(int oct = 0; oct < 32; oct++){
            interp += is.noise(xla, yla) * amp;
            xla *= lac;
            yla *= lac;
            amp *= persistance;
        }
        
        return interp;
    }
    
    public static final float res3D = 206f;
    
    public static float noise(int x, int y, int z){ // x/y/z coordinate not float
        float interp = 0f;
        float amp = 1f;
        
        float xla = ((float)(x))/res3D;
        float yla = ((float)(y))/res3D;
        float zla = ((float)(z))/res3D;
        
        for(int oct = 0; oct < 32; oct++){
            interp += is.noise(xla, yla, zla) * amp;
            xla *= lac;
            yla *= lac;
            zla *= lac;
            amp *= persistance;
        }
        
        return interp;
    }
    
    public static float getSpriteNoise(int x, int y){
        float ret = is.noise(x, y);
        ret = 1+ret/2;
        if(ret>1f)ret=1f;
        
        
        return ret;
    }
    
    public static float noise(int v){
        
        float interp = 0f;
        float amp = 0.9f;
        
        float la = ((float)(v))/res1D;
        
        for(int oct = 0; oct < 32; oct++){
            interp += is.noise(la, 420.42f) * amp;
            la *= lac;
            amp *= persistance;
        }
        
        return interp;
        
    }
    
}
