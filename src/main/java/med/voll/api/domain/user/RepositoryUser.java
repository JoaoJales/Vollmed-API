package med.voll.api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositoryUser extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);
}
