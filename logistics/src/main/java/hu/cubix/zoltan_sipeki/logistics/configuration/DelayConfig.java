package hu.cubix.zoltan_sipeki.logistics.configuration;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "delay")
public class DelayConfig {

    private Map<Integer, Integer> incomeCutPercents = new TreeMap<>((a, b) -> Integer.compare(a, b) * -1);

    public DelayConfig(Map<Integer, Integer> incomeCutPercents) {
        this.incomeCutPercents.putAll(incomeCutPercents);
    }

    public Map<Integer, Integer> getIncomeCutPercents() {
        return incomeCutPercents;
    }
}
