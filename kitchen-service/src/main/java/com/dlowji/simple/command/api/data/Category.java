package com.dlowji.simple.command.api.data;

import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends TimeStamp {
    @Id
    private String categoryId;
    private String categoryName;
}
