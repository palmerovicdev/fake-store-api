package com.suehay.fsastorageservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "image_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
}