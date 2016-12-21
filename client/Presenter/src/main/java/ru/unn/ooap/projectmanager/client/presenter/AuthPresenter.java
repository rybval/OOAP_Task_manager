package ru.unn.ooap.projectmanager.client.presenter;

import ru.unn.ooap.projectmanager.server.model.users.Users;
import ru.unn.ooap.projectmanager.server.model.users.User;
import ru.unn.ooap.projectmanager.server.model.users.executor.Executor;
import ru.unn.ooap.projectmanager.server.model.users.manager.Manager;
import ru.unn.ooap.projectmanager.server.model.users.administrator.Administrator;

import java.io.IOException;

public class AuthPresenter {
    private IAuthView view;
    private String username;
    private String password;
    private String status;
    private boolean isButtonActive;

    private void setStatus(final String statusString) {
        status = statusString;
    }

    public String getStatus() {
        return status;
    }

    public void auth() {
        User user = Users.auth(username, password);
        view.setUser(user);
        try {
            if (user instanceof Executor) {
                view.showExecutorScene();
            } else if (user instanceof Manager) {
                view.showManagerScene();
            } else if (user instanceof Administrator) {
                view.showAdminScene();
            } else if (user == null) {
                setStatus("FAIL: Wrong authentication data");
            } else {
                setStatus("FAIL: Unexpected response");
            }
        } catch (IOException e) {
            setStatus("FAIL: Input/output error");
        }
    }
}
