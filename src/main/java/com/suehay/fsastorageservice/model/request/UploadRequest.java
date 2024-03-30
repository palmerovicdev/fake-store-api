package com.suehay.fsastorageservice.model.request;

public record UploadRequest(String name, String type, byte[] data) {
}