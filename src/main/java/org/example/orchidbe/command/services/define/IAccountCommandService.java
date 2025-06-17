package org.example.orchidbe.command.services.define;

public interface IAccountCommandService {
    public void createAccount(String username, String password, String email);
    public void updateAccount(Long accountId, String username, String currentPassword, String newPassword, String email);
    public void blockAccount(Long accountId);
    public void unblockAccount(Long accountId);
}
