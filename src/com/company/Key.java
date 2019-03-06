package com.company;

import java.util.Objects;

public class Key {

    private final long recordTime;
    private int count;

    public Key(long recordTime, Object key) {
        this.recordTime = recordTime;
        this.count = count;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public Object getKey() {
        return key;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return recordTime == key.recordTime &&
                Objects.equals(key, key.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordTime, key);
    }
}
