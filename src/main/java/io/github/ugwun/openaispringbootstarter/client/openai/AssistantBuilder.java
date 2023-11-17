package io.github.ugwun.openaispringbootstarter.client.openai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssistantBuilder {
    private final Map<String, Object> data = new HashMap<>();
    private final List<Map<String, String>> tools = new ArrayList<>();

    public AssistantBuilder model(String model) {
        data.put("model", model);
        return this;
    }

    public AssistantBuilder name(String name) {
        if (name != null && name.length() > 256) {
            throw new IllegalArgumentException("Name exceeds maximum length of 256 characters");
        }
        data.put("name", name);
        return this;
    }

    public AssistantBuilder description(String description) {
        if (description != null && description.length() > 512) {
            throw new IllegalArgumentException("Description exceeds maximum length of 512 characters");
        }
        data.put("description", description);
        return this;
    }

    public AssistantBuilder instructions(String instructions) {
        if (instructions != null && instructions.length() > 32768) {
            throw new IllegalArgumentException("Instructions exceed maximum length of 32768 characters");
        }
        data.put("instructions", instructions);
        return this;
    }

    public AssistantBuilder tools(List<Map<String, String>> tools) {
        if (tools.size() > 128) {
            throw new IllegalArgumentException("There can be a maximum of 128 tools per assistant");
        }
        data.put("tools", tools);
        return this;
    }

    public AssistantBuilder addTool(ToolType toolType) {
        Map<String, String> tool = new HashMap<>();
        tool.put("type", toolType.getType());
        tools.add(tool);
        return this;
    }

    public AssistantBuilder fileIds(List<String> fileIds) {
        if (fileIds.size() > 20) {
            throw new IllegalArgumentException("There can be a maximum of 20 files attached to the assistant");
        }
        data.put("file_ids", fileIds);
        return this;
    }

    public AssistantBuilder metadata(Map<String, String> metadata) {
        if (metadata.size() > 16) {
            throw new IllegalArgumentException("Metadata can only contain up to 16 key-value pairs");
        }
        metadata.forEach((key, value) -> {
            if (key.length() > 64 || value.length() > 512) {
                throw new IllegalArgumentException("Metadata key or value exceeds maximum length");
            }
        });
        data.put("metadata", metadata);
        return this;
    }

    public Map<String, Object> build() {
        if (!tools.isEmpty()) {
            data.put("tools", tools);
        }
        // Additional validation checks can be added here
        return data;
    }

    public enum ToolType {
        CODE_INTERPRETER("code_interpreter"),
        RETRIEVAL("retrieval"),
        FUNCTION("function");

        private final String type;

        ToolType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}

