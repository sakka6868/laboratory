package cn.sakka.wechat.transit.cn.sakka.wechat.transit;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.EntryExpiredListener;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class HazelcastTest {

    static HazelcastInstance newHazelcastInstance() {
        Config config = new Config();
        NetworkConfig networkConfig = new NetworkConfig();
        JoinConfig join = new JoinConfig();
        AutoDetectionConfig autoDetectionConfig = new AutoDetectionConfig();
        autoDetectionConfig.setEnabled(true);
        join.setAutoDetectionConfig(autoDetectionConfig);
        networkConfig.setJoin(join);
        InterfacesConfig interfaces = new InterfacesConfig();
        interfaces.setEnabled(true);
        interfaces.setInterfaces(Collections.singleton("192.168.50.*"));
        networkConfig.setInterfaces(interfaces);
        config.setNetworkConfig(networkConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            HazelcastInstance hazelcastInstance = newHazelcastInstance();
            IMap<String, String> data = hazelcastInstance.getMap("data");
            data.addEntryListener((EntryExpiredListener<String, String>) System.out::println, true);
        });
        Thread thread2 = new Thread(() -> {
            HazelcastInstance hazelcastInstance = newHazelcastInstance();
            IMap<String, String> data = hazelcastInstance.getMap("data");
            data.addEntryListener((EntryExpiredListener<String, String>) System.out::println, true);
        });
        thread1.start();
        thread2.start();
        HazelcastInstance hazelcastInstance = newHazelcastInstance();
        IMap<Object, Object> data = hazelcastInstance.getMap("data");
        data.put("key", "value", 30, TimeUnit.SECONDS);
    }

}
