package com.Rbp.world.Helpers;

public class DataPart {
    private String fileName;
    private byte[] content;
    private String type;

    private String  id;
    private String ss;

    public DataPart() {
    }

    public DataPart(String id, String ss) {
        this.id = id;
        this.ss = ss;
    }

    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }

}
