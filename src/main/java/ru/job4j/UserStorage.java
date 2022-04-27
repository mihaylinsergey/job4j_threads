package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> stores = new HashMap<>();

    public synchronized boolean add(User user) {
        return stores.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return stores.replace(user.getId(), user) == null;
    }

    public synchronized boolean delete(User user) {
        return stores.remove(user.getId()) == null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = stores.get(fromId);
        User to = stores.get(toId);
        if (from == null || to == null || from.getAmount() < 0) {
            throw new IllegalArgumentException("Not enough funds or wrong users");
        }
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
    }

    public static void main(String[] args) {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        store.add(new User(2, 200));
        store.stores.forEach((key, value) -> System.out.println(key + " : " + value.getAmount()));
        store.transfer(1, 2, 50);
        store.stores.forEach((key, value) -> System.out.println(key + " : " + value.getAmount()));
    }
}
