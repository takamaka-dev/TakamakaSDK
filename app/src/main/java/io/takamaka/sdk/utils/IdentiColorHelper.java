package io.takamaka.sdk.utils;


import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;

public class IdentiColorHelper {

    public static int cfh(String cc) {
        return Integer.parseInt(cc.substring(0, 2), 16);
    }


    public static int[] getRotation(String hex) {
        int[] rt = new int[4];
        rt[0] = (Integer.parseInt(hex.substring(0, 1), 16)) % 4;
        rt[1] = (Integer.parseInt(hex.substring(0, 1), 16) >> 2) % 4;
        rt[2] = (Integer.parseInt(hex.substring(1, 2), 16)) % 4;
        rt[3] = (Integer.parseInt(hex.substring(1, 2), 16) >> 2) % 4;
        return rt;
    }

    public static int[][] clone(int[][] matrix) {
        int len = matrix.length;
        int ilen = matrix.length - 1;

        int[][] mirroredMatrix = new int[len][len];
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                mirroredMatrix[indexRow][indexCol] = matrix[indexRow][indexCol];
            });
        });
        return mirroredMatrix;
    }

    public static int[][] mirrorVertical(int[][] matrix) {
        int len = matrix.length;
        int ilen = matrix.length - 1;

        int[][] mirroredMatrix = new int[len][len];
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                mirroredMatrix[indexRow][ilen - indexCol] = matrix[indexRow][indexCol];
            });
        });
        return mirroredMatrix;
    }

    public static int[][] mirrorHorizontal(int[][] matrix) {
        int len = matrix.length;
        int ilen = matrix.length - 1;

        int[][] mirroredMatrix = new int[len][len];
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                mirroredMatrix[ilen - indexRow][indexCol] = matrix[indexRow][indexCol];
            });
        });
        return mirroredMatrix;
    }

    public static int[][] mirrorHplusV(int[][] matrix) {
        int len = matrix.length;
        int ilen = matrix.length - 1;

        int[][] mirroredMatrix = new int[len][len];
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                mirroredMatrix[ilen - indexRow][ilen - indexCol] = matrix[indexRow][indexCol];
            });
        });
        return mirroredMatrix;
    }

    /**
     * @param hex 3 char hex string
     * @return 32x32 mirrored matrix
     */
    public static int[][] get32by32SquareBlock(String hex) {
        //select block
        int blockIndex = Integer.parseInt(hex.substring(0, 1), 16);
        int[][][] block = IdenticonManager.i().getBlocks()[blockIndex];
        //rotation index
        int[] rotation = getRotation(hex.substring(1, 3));
        int[][] topLeft = clone(block[rotation[0]]);
        //System.out.println("top left");
        //printMatrix(topLeft);
        int[][] topRight = clone(block[rotation[1]]);
        //System.out.println("top right");
        //printMatrix(topRight);
        int[][] bottomRight = clone(block[rotation[2]]);
        //System.out.println("bottom left");
        //printMatrix(bottomRight);
        int[][] bottomLeft = clone(block[rotation[3]]);
        //System.out.println("bottom right");
        //printMatrix(bottomLeft);

        return merge4Square(topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * @param hex
     * @return
     */
    public static int[][] get64by64SquareBlockSIM(String hex) {
        int[][] squareBlock32 = get32by32SquareBlock(hex);

        int[][] topLeft = clone(squareBlock32);
        int[][] topRight = mirrorVertical(squareBlock32);
        int[][] bottomRight = mirrorHplusV(squareBlock32);
        int[][] bottomLeft = mirrorHorizontal(squareBlock32);

        return merge4Square(topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * @param hex 12 char
     * @return
     */
    public static int[][] get64by64SquareBlockHIRND(String hex) {
        //int[][] squareBlock32 = get32by32SquareBlock(hex);

        int[][] topLeft = clone(get32by32SquareBlock(hex.substring(0, 3)));
        int[][] topRight = clone(get32by32SquareBlock(hex.substring(3, 6)));
        int[][] bottomRight = clone(get32by32SquareBlock(hex.substring(6, 9)));
        int[][] bottomLeft = clone(get32by32SquareBlock(hex.substring(9, 12)));

        return merge4Square(topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * @param hex 12 char
     * @return
     */
    public static int[][] get128by128SquareBlockHIRND(String hex) {
        //int[][] squareBlock32 = get32by32SquareBlock(hex);

        int[][] topLeft = clone(get64by64SquareBlockHIRND(hex.substring(0, 12)));
        int[][] topRight = clone(get64by64SquareBlockHIRND(hex.substring(12, 24)));
        int[][] bottomRight = clone(get64by64SquareBlockHIRND(hex.substring(36, 48)));
        int[][] bottomLeft = clone(get64by64SquareBlockHIRND(hex.substring(48, 60)));

        return merge4Square(topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * @param hex 60 char hex
     * @return
     */
    public static int[][] get256by256SquareBlockHIRND(String hex) {
        int[][] squareBlock128 = get128by128SquareBlockHIRND(hex.substring(0, 60));

        int[][] topLeft = clone(squareBlock128);
        int[][] topRight = mirrorVertical(squareBlock128);
        int[][] bottomRight = mirrorHplusV(squareBlock128);
        int[][] bottomLeft = mirrorHorizontal(squareBlock128);

        return merge4Square(topLeft, topRight, bottomRight, bottomLeft);
    }

    public static int[][] merge4Square(int[][] topLeft, int[][] topRight, int[][] bottomRight, int[][] bottomLeft) {
        int len = topLeft.length * 2;
        int shift = len / 2;
        int[][] res = new int[len][len];
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                if (indexRow < len / 2) {
                    //top
                    if (indexCol < len / 2) {
                        //top left matrix
                        res[indexRow][indexCol] = topLeft[indexRow][indexCol];
                    } else {
                        //top right matrix
                        res[indexRow][indexCol] = topRight[indexRow][indexCol - shift];
                    }
                } else {
                    //bottom
                    if (indexCol < len / 2) {
                        //bottom left matrix
                        res[indexRow][indexCol] = bottomLeft[indexRow - shift][indexCol];
                    } else {
                        //bottom right matrix
                        res[indexRow][indexCol] = bottomRight[indexRow - shift][indexCol - shift];
                    }
                }
            });
        });
        return res;
    }

    public static Color fgColorFromHex(String hex) {
        return Color.valueOf(cfh(hex.substring(0, 2)), cfh(hex.substring(2, 4)), cfh(hex.substring(4, 6)));
    }

//    public static Color bgColorFromHex(String hex) {
//        int r = cfh(hex.substring(0, 2));
//        int g = cfh(hex.substring(2, 4));
//        int b = cfh(hex.substring(4, 6));
//        if (r > 220 & g > 220 & b > 220) {
//            return Color.parseColor("#000000");//Color.valueOf(0, 0, 0);
//        } else {
//            return Color.valueOf(255, 255, 255);
//        }
//    }

    public static int bgColorFromHex(String hex) {
        int r = cfh(hex.substring(0, 2));
        int g = cfh(hex.substring(2, 4));
        int b = cfh(hex.substring(4, 6));
        if (r > 220 & g > 220 & b > 220) {
            return Color.parseColor("#000000");//Color.valueOf(0, 0, 0);
        } else {
            return Color.parseColor("#ffffff");//Color.valueOf(255, 255, 255);
        }
    }

    public static Bitmap identiconMatrixGenerator(String value) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        int len = 256;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(len, len, conf); // this creates a MUTABLE bitmap
        String hex = TkmSignUtils.Hash256ToHex(value);
        int[][] hrandMatrix = get256by256SquareBlockHIRND(hex.substring(0, 60));
        Color fgC = fgColorFromHex(hex.substring(58, 64));
        System.out.println("FGC GENERATO: " + fgC);
        System.out.println("COLOR string input: " + "#" + hex.substring(58, 64));
        int bg = bgColorFromHex(hex.substring(58, 64));
        int fg = Color.parseColor("#" + hex.substring(58, 64));//fgC.toArgb();
        IntStream.range(0, len).forEach((int indexRow) -> {
            IntStream.range(0, len).forEach((int indexCol) -> {
                switch (hrandMatrix[indexRow][indexCol]) {
                    case 0:
                        //bi.setRGB(indexRow, indexCol, bg);
                        bmp.setPixel(indexRow, indexCol, bg);
                        break;
                    case 1:
                        bmp.setPixel(indexRow, indexCol, fg);
                        //bi.setRGB(indexRow, indexCol, fg);
                        break;
                    default:
                }
            });
        });
        return bmp;
    }

    public static void printMatrix(int[][] m) {
        IntStream.range(0, m.length).forEachOrdered((int rIndex) -> {
            IntStream.range(0, m[rIndex].length).forEachOrdered((int cIndex) -> {
                System.out.print(m[rIndex][cIndex]);
            });
            System.out.println("");
        });
        System.out.println("");
    }

}
