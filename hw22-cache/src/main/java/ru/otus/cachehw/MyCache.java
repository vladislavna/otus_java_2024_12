package ru.otus.cachehw;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import lombok.extern.slf4j.Slf4j;
import ru.otus.utils.HwListenerActionEnum;

@Slf4j
public class MyCache<K, V> implements HwCache<K, V> {
    // Надо реализовать эти методы
    private final Map<K, V> cache = new WeakHashMap<>();
    private final Set<HwListener<K, V>> listeners = new HashSet<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, HwListenerActionEnum.PUT);
    }

    @Override
    public void remove(K key) {
        var value = cache.remove(key);
        notifyListeners(key, value, HwListenerActionEnum.REMOVE);
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);
        notifyListeners(key, value, HwListenerActionEnum.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, HwListenerActionEnum currentAction) {
        for (HwListener<K, V> listener : listeners) {
            try {
                listener.notify(key, value, currentAction.name());
            } catch (Exception e) {
                log.error("Something happened in someones listener - {}", e.getMessage());
            }
        }
    }
}
