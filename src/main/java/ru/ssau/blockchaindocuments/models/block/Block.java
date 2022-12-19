package ru.ssau.blockchaindocuments.models.block;

import ru.ssau.blockchaindocuments.utils.HexUtil;

import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    private final byte[] data; //our data will be a simple message.
    private final long timeStamp; //as number of milliseconds since 1/1/1970.

    private int nonce;

    //Block Constructor.
    public Block(byte[] data,String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = HexUtil.applySha256(data, previousHash + timeStamp);
    }

    public String calculateHash() {
        return HexUtil.applySha256(data, previousHash + timeStamp + nonce);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
            System.out.println(hash);
        }
//        System.out.println("Block Mined!!! : " + hash);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public byte[] getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
