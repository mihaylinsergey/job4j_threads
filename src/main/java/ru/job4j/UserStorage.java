package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private static final Map<Integer, User> STORES = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.putIfAbsent(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.replace(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        if (STORES.get(fromId).getAmount() < 0) {
            throw new IllegalArgumentException("Not enough funds");
        }
        User from = STORES.get(fromId);
        from.setAmount(from.getAmount() - amount);
        User to = STORES.get(toId);
        to.setAmount(to.getAmount() + amount);
        update(from);
        update(to);
    }

    public static void main(String[] args) {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        store.add(new User(2, 200));
        STORES.forEach((key, value) -> System.out.println(key + " : " + value.getAmount()));
        store.transfer(1, 2, 50);
        STORES.forEach((key, value) -> System.out.println(key + " : " + value.getAmount()));
    }
}
