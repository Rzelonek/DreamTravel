package pl.edu.wszib.media.store.services;

public interface IAuthenticationService {
    void login(String login, String password);

    void logout();

    String getLoginInfo();
}
