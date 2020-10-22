package io.takamaka.sdk.transactions.fee;


import java.io.Serializable;
import java.math.BigInteger;

public class FeeBean implements Serializable {

    transient private final Object MEM_LOCK = new Object();
    transient private final Object DISK_LOCK = new Object();
    transient private final Object CPU_LOCK = new Object();

    private String addr;
    private String hexAddr;
    private String sith;
    private BigInteger disk;
    private BigInteger memory;
    private BigInteger cpu;

    public String getSith() {
        return sith;
    }

    public void setSith(String sith) {
        this.sith = sith;
    }

    /**
     * non threadsafe
     *
     * @return
     */
    public String getAddr() {
        return addr;
    }

    /**
     * non threadsafe
     *
     * @param addr
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * non threadsafe
     *
     * @return
     */
    public String getHexAddr() {
        return hexAddr;
    }

    /**
     * non threadsafe
     *
     * @param hexAddr
     */
    public void setHexAddr(String hexAddr) {
        this.hexAddr = hexAddr;
    }

    public BigInteger getDisk() {
        synchronized (DISK_LOCK) {
            return disk;
        }
    }

    public void setDisk(BigInteger disk) {
        synchronized (DISK_LOCK) {
            this.disk = disk;
        }
    }

    public BigInteger getMemory() {
        synchronized (MEM_LOCK) {
            return memory;
        }
    }

    public void setMemory(BigInteger memory) {
        synchronized (MEM_LOCK) {
            this.memory = memory;
        }
    }

    public BigInteger getCpu() {
        synchronized (CPU_LOCK) {
            return cpu;
        }
    }

    public void setCpu(BigInteger cpu) {
        synchronized (CPU_LOCK) {
            this.cpu = cpu;
        }
    }

    public void addCpu(BigInteger valueToAdd) {
        synchronized (CPU_LOCK) {
            this.cpu = this.cpu.add(valueToAdd);
        }
    }

    public void addMemory(BigInteger valueToAdd) {
        synchronized (MEM_LOCK) {
            this.memory = this.memory.add(valueToAdd);
        }
    }

    public void addDisk(BigInteger valueToAdd) {
        synchronized (DISK_LOCK) {
            this.disk = this.disk.add(valueToAdd);
        }
    }

    /**
     * sum the FeeBean passed to the current FeeBean
     *
     * @param fb
     */
    public void sum(FeeBean fb) {
        addCpu(fb.getCpu());
        addMemory(fb.getMemory());
        addDisk(fb.getDisk());
    }

}
