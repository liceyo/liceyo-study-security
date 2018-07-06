package pers.liceyo.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liceyo
 * @version 2018/7/5
 */
@Component
@ConfigurationProperties("security")
public class WebSecurityProperties {
    private List<String> permitUrls;

    public List<String> getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(List<String> permitUrls) {
        this.permitUrls = permitUrls;
    }

    public String[] permitAll(){
        String[] ignoringUrls=new String[permitUrls.size()];
        permitUrls.toArray(ignoringUrls);
        return ignoringUrls;
    }
}
