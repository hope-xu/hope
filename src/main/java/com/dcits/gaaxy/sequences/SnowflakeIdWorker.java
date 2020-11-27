package com.dcits.gaaxy.sequences;

import javax.management.RuntimeErrorException;
import java.security.acl.LastOwnerException;

/**
 * @author Hope
 * Date： 2020/10/29  16:22
 * 描述：
 */
public class SnowflakeIdWorker {
    private final long twepoch = 1420041600000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = 31L;
    private final long maxDatacenterId = 31L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;


    public SnowflakeIdWorker(long workerId, long datacenterId) {

        if (workerId <= maxWorkerId && workerId >= 0L) {
            if (datacenterId <= maxDatacenterId && datacenterId > -0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
        } else {
            throw new IllegalArgumentException(String.format("workerId Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }


    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & sequenceMask;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - twepoch << timestampLeftShift | this.datacenterId << datacenterIdShift | this.workerId << workerIdShift | this.sequence;
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for (timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
            ;
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(3L, 3L);
        long id = idWorker.nextId();
        String binaryString = Long.toBinaryString(id);
        System.out.println(binaryString);
        System.out.println(id);
    }


}
