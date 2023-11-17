package io.github.ugwun.openaispringbootstarter.client.openai;

import java.util.HashMap;
import java.util.Map;

public class AssistantListQueryBuilder {
    private final Map<String, String> queryParams = new HashMap<>();

    public AssistantListQueryBuilder limit(int limit) {
        if (limit < 1 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }
        queryParams.put("limit", String.valueOf(limit));
        return this;
    }

    public AssistantListQueryBuilder order(String order) {
        if (!order.equals("asc") && !order.equals("desc")) {
            throw new IllegalArgumentException("Order must be 'asc' or 'desc'");
        }
        queryParams.put("order", order);
        return this;
    }

    public AssistantListQueryBuilder after(String after) {
        queryParams.put("after", after);
        return this;
    }

    public AssistantListQueryBuilder before(String before) {
        queryParams.put("before", before);
        return this;
    }

    public Map<String, String> build() {
        return queryParams;
    }
}

