package com.datalink.uaa.test;

import com.datalink.base.auth.utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * JwtUtilTest
 *
 * @author wenmo
 * @since 2021/5/11
 */
@RunWith(SpringRunner.class)
public class JwtUtilTest {
    @Test
    public void test() {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZXN0IjoiYWJjIiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNTYzNjgyMTI4LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJlMDFlNGU0Yi1hZDVkLTRlMTQtODhiMC00OGQ4YzBjN2U5YjkiLCJjbGllbnRfaWQiOiJ3ZWJBcHAifQ.Qrh2aEoN4TL_WIQ9UpxDrW12aqqoVqxeY826sjbea2LB24RBNDYQl1J5vwXzMaQlG9AgjHRL4bTQihwBYYfdL-VuJXx0_l0xONbz9sHPq60a3gAhxOnekNS5-Qet5feTw7j4o2OwNlxo-xty5s8u2lsQY21zCe0tes_T4XeM76JTBpRbQUFGUU3EKxtUFi3Nk9AII4zerW1AbQNvLo4YW2Wvj___0lq5a-xNdCcHlJid8vKgzEF3v3wECOv6OjgL-fUN8VpUsYVt1-_QZp8opPAf-t3OVTtrVIWrJZ_vWV9d6DN5mynKtZ7_mDyMwo_5w3roAZ0ahoBKPKrtYQyEwQ";
        JsonNode claims = JwtUtil.decodeAndVerify(jwtToken);
        //token内容
        System.out.println(claims);
        boolean isValid = JwtUtil.checkExp(claims);
        //是否有效
        System.out.println(isValid);
    }
}
