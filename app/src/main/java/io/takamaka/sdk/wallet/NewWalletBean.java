package io.takamaka.sdk.wallet;

import io.takamaka.sdk.globalContext.KeyContexts.WalletCypher;
import java.nio.file.Path;

public class NewWalletBean {

    private String name;
    private String fileName;
    private char[] password;
    private Path filePath;
    private WalletCypher cypher;
    private boolean recover;

    public boolean isRecover() {
        return recover;
    }

    public void setRecover(boolean recover) {
        this.recover = recover;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public WalletCypher getCypher() {
        return cypher;
    }

    public void setCypher(WalletCypher cypher) {
        this.cypher = cypher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

}

