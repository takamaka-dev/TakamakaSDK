package io.takamaka.demo.utils;

public class WalletFXHelperErrorBean {

    protected String error;
    protected boolean loaded;

    public WalletFXHelperErrorBean() {
        this.error = null;
        this.loaded = false;
    }

    public String getError() {
        return error;
    }

    public boolean isLoaded() {
        return loaded;
    }

}
