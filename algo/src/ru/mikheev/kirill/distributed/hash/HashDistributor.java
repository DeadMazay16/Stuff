package ru.mikheev.kirill.distributed.hash;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HashDistributor {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Set<String> servers;
    private final int serverPartitioningFactor;

    private final TreeMap<Integer, String> circle = new TreeMap<>(); // Для примера я использовал стандартную реализацию,
    // однако в целях оптимизации лучше написать свое дерево, которое будет работать с int, и при этом будет иметь метод удаления по значению

    public HashDistributor(Collection<String> servers, int serverPartitioningFactor) {
        this.servers = new HashSet<>(servers);
        this.serverPartitioningFactor = serverPartitioningFactor;
        initCircle();
    }

    private void initCircle() {
        long allInts = (long) Integer.MAX_VALUE * 2 + 1;
        int allPartitionsCount = servers.size() * serverPartitioningFactor;
        int step = (int)(allInts / allPartitionsCount);
        int i = 0;
        int intervalStart = Integer.MIN_VALUE;
        while (i < allPartitionsCount - 1) {
            for (String serverName : servers) {
                circle.put(intervalStart, serverName);
                i++;
                intervalStart += step;
            }
        }
    }

    public String getServer(int hash) {
        var tail = circle.tailMap(hash);
        Integer nearestHash = tail.isEmpty() ? circle.firstKey() : tail.firstKey();
        return circle.get(nearestHash);
    }

    public void print() {
        circle.forEach( (a, b) ->
                System.out.println(a + " - " + b)
        );
    }
}
