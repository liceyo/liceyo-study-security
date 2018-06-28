package pers.liceyo.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import pers.liceyo.security.domain.PersistentLogin;
import pers.liceyo.security.domain.PersistentLoginRepository;

import java.util.Date;
import java.util.Optional;

/**
 * 重写记住我数据操作类
 * @author liceyo
 * @version 2018/6/28
 */
@Service
public class LiceyoPersistentLoginRepository implements PersistentTokenRepository{
    @Autowired
    private PersistentLoginRepository persistentLoginRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin=new PersistentLogin();
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persistentLoginRepository.save(persistentLogin);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        persistentLoginRepository.updateToken(series, tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<PersistentLogin> login = persistentLoginRepository.findById(seriesId);
        PersistentRememberMeToken persistentRememberMeToken=null;
        if (login.isPresent()){
            PersistentLogin persistentLogin = login.get();
            persistentRememberMeToken=new PersistentRememberMeToken(persistentLogin.getUsername(),persistentLogin.getSeries(),persistentLogin.getToken(),persistentLogin.getLastUsed());
        }
        return persistentRememberMeToken;
    }

    @Override
    public void removeUserTokens(String username) {
        persistentLoginRepository.removeByUsername(username);
    }
}
