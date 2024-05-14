package com.suehay.fsastorageservice.model.request;

import com.suehay.fsastorageservice.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private Long id;
    private String name;
    private String image;

    public Category getEntity() {
        var entity = new Category();
        if (!Objects.isNull(id))
            entity.setId(id);
        entity.setName(name);
        entity.setImage(image);
        return entity;
    }
}