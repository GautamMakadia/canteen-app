package com.botmg3002.canteen.schema.category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private Long canteenId;
    private String imageUrl;
}
