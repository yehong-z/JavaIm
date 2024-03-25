package com.zyh.javaim.logic.common.id;

import org.springframework.stereotype.Component;

@Component
public class Id {
    private long sequence = 0L;

    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();

        // 设置起始时间戳，这里以2021-06-01为例
        long twepoch = 1622505600000L;
        if (timestamp < twepoch) {
            throw new RuntimeException("Clock moved backwards.");
        }

        if (timestamp == twepoch) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0) {
                timestamp = tilNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }

        long machineId = 1L;
        long datacenterId = 1L;
        return ((timestamp - twepoch) << 22) | (datacenterId << 17) | (machineId << 12) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}