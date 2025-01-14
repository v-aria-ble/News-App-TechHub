package dev.mobprog.techhub.models.response;

import java.util.List;

import dev.mobprog.techhub.models.Source;

public class SourcesResponse {
    private String status;
    private List<dev.mobprog.techhub.models.Source> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
