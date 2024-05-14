package com.suehay.fsastorageservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericPageResponse<T> {
    String error;
    String message;
    String status;
    T data;
    Integer page;
    Integer size;
}