package noisework;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Luke
 */// these classes should be moved into a better place
public class BitWriter {

    public byte nthBit = 0;
    public int index = 0;
    public byte[] data;

    public BitWriter( int nBits ) {
        this.data = new byte[(int)Math.ceil(nBits / 8.0)];
    }

    public void writeBit(boolean bit) {
        if( nthBit >= 8) {
            nthBit = 0;

            index++;
            if( index >= data.length) {
                throw new IndexOutOfBoundsException();
            }
        }
        byte b = data[index];

        int mask = (1 << (7 - nthBit));

        if( bit ) {
            b = (byte)(b | mask);
        }
        data[index] = b;
        nthBit++;
    }

    public byte[] toArray() {
        byte[] ret = new byte[data.length];
        System.arraycopy(data, 0, ret, 0, data.length);
        return ret;
    }
//
//    public static void main( String... args ) {
//        BitWriter bw = new BitWriter(6);
//        String strbits = "101010";
//        for( int i = 0; i < strbits.length(); i++) {
//            bw.writeBit( strbits.charAt(i) == '1');
//        }
//
//        byte[] b = bw.toArray();
//        for( byte a : b ) {
//            System.out.format("%02X", a);
//                 //A8 == 10101000
//
//        }
//    }

}