package cc.taketo.auth;

import cc.taketo.entity.login.AuthCode;
import cc.taketo.utils.ImageCodeUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AuthCodeTest {

    @Test
    public void generate() throws IOException {
        AuthCode authCode = ImageCodeUtil.createAuthCode();
        System.out.println(authCode);
    }
}
