package io.takamaka.sdk.utils;

import java.util.logging.Level;
import java.util.stream.IntStream;

public class IdenticonManager {

    //
    private static int[][][][] blocks;

    private IdenticonManager() {

        blocks = new int[IdentiBaseBlocks.BLOCKS.length][][][];
        //for each block in array
        IntStream.range(0, blocks.length).forEach((int blockIndex) -> {
            blocks[blockIndex] = new int[4][][];
            //for each rotation in array
            IntStream.range(0, 4).forEach((int rotationIndex) -> {
                switch (rotationIndex) {
                    case 0: //original no rotation
                        blocks[blockIndex][rotationIndex] = IdentiColorHelper.clone(IdentiBaseBlocks.BLOCKS[blockIndex]);
                        break;
                    case 1: //mirror vertical
                        blocks[blockIndex][rotationIndex] = IdentiColorHelper.mirrorVertical(IdentiBaseBlocks.BLOCKS[blockIndex]);
                        break;
                    case 2: //mirror horizontal
                        blocks[blockIndex][rotationIndex] = IdentiColorHelper.mirrorHorizontal(IdentiBaseBlocks.BLOCKS[blockIndex]);
                        break;
                    case 3: //mirror V+H
                        blocks[blockIndex][rotationIndex] = IdentiColorHelper.mirrorHplusV(IdentiBaseBlocks.BLOCKS[blockIndex]);
                        break;
                    default:
                }
            });
        });

    }

    private static class IM {

        public static final IdenticonManager I = new IdenticonManager();
    }

    public static IdenticonManager i() {
        return IM.I;
    }

    public int[][][][] getBlocks() {
        return blocks;
    }

}
