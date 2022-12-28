package ru.ssau.blockchaindocuments.models.block;

import ru.ssau.blockchaindocuments.utils.BlockKeeper;
import ru.ssau.blockchaindocuments.utils.HexUtil;

import java.math.BigInteger;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    private final byte[] data; //our data will be a simple message.
    private final long timeStamp; //as number of milliseconds since 1/1/1970.
    private BigInteger nonce;

    //Block Constructor.
    public Block(byte[] data,String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.nonce = BigInteger.ZERO;
        mineBlock(BlockKeeper.difficulty);
    }

    public String calculateHash() {
        return HexUtil.applySha256(data, previousHash + timeStamp + nonce);
    }

    public void mineBlock(int difficulty) {
        this.hash = calculateHash();
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce = nonce.add(BigInteger.ONE);
            hash = calculateHash();
            System.out.println("current hash: " + hash);
            System.out.println("current nonce: " + nonce);
        }
        System.out.println(this);
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

    @Override
    public String toString() {
        return "Block{" +
                "\n\thash='" + hash + '\'' +
                ", \n\tpreviousHash='" + previousHash + '\'' +
//                ", data=" + Arrays.toString(data) +
                ", \n\ttimeStamp=" + timeStamp +
                ", \n\tnonce=" + nonce +
                "\n\t }";
    }
}
