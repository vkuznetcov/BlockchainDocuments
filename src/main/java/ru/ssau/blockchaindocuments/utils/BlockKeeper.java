package ru.ssau.blockchaindocuments.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.blockchaindocuments.models.block.Block;

import java.io.IOException;
import java.util.ArrayList;

@Component
@ComponentScan(basePackages = "java.util.ArrayList")
public class BlockKeeper {
    public static int difficulty = 2;
    ArrayList<Block> blocks;

    @Autowired()
    public BlockKeeper(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public Block getBlock(int id) {
        return blocks.get(id);
    }

    public void addBlock(MultipartFile file) throws IOException {
        String hash = blocks.size() == 0 ? "000000000000000000000000000000000000000000000000000000000000000000000" : blocks.get(blocks.size() - 1).getHash();
        Block block = new Block(file.getBytes(), hash);
        blocks.add(block);
//        JsonCreator.writeJson(block);
    }

    public Boolean isFileValid(MultipartFile file) throws IOException {
        byte[] checkBytes = file.getBytes();
        if (isChainValid()) {
            for (Block block : blocks) {
                if (HexUtil.applySha256(block.getData(), "").equals(HexUtil.applySha256(checkBytes, "")))
                    return true;
            }
        }
        return false;
    }

    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blocks.size(); i++) {
            currentBlock = blocks.get(i);
            previousBlock = blocks.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
