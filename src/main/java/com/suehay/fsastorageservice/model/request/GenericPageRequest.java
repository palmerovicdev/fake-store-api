package com.suehay.fsastorageservice.model.request;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GenericPageRequest <T>{
    private int page;
    private int size;
    private T filter;
}