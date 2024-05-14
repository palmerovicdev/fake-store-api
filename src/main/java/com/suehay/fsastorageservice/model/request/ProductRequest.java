package com.suehay.fsastorageservice.model.request;

import com.suehay.fsastorageservice.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private List<String> images = new ArrayList<>();
    private Long category;

    public Product getEntity() {
        var entity = new Product();
        if (!Objects.isNull(id))
            entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setImages(images);
        return entity;
    }
}