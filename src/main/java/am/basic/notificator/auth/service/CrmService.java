package am.basic.notificator.auth.service;

import am.basic.notificator.model.User;

public interface CrmService {

    User getByUsername(String username);

}
