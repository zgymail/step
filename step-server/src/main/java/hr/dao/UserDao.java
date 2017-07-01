package hr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hr.entity.User;

@Repository
public interface UserDao extends CrudRepository<User,Integer> {
	User findByIdentity(String identity);
	User findByOpenid(String openid);
}
