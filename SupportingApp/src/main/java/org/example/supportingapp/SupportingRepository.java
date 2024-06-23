package org.example.supportingapp;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SupportingRepository {
    private Map<UUID, String> supportMap = new ConcurrentHashMap<>();
    SupportingRepository(){
        supportMap.put(UUID.randomUUID(), "Всё будет, но не сразу");
    }
    public String getSupportingWord() throws SupportingError {
        if(supportMap.isEmpty()) throw new SupportingError("База данных пуста");
        Map.Entry<UUID, String> entry = supportMap.entrySet().stream().findAny().orElse(null);
        return entry.getValue();
    }

    public void addSupportingWord(String phrase) {
        var code = UUID.randomUUID();
        while(supportMap.get(code) != null)
            code = UUID.randomUUID();
        supportMap.put(code, phrase);
    }
}
