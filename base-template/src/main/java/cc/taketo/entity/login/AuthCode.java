package cc.taketo.entity.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCode {

    private String authCode;

    private String base64Image;
}
