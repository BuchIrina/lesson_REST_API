package buchneva.models.lombok;

import lombok.Data;

@Data
public class AddToCartResponseModel {
    Boolean success;
    String message, updatetopcartsectionhtml, updateflyoutcartsectionhtml;
}
