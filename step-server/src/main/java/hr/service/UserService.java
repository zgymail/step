package hr.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import hr.entity.User;


@Service
@Order(1)
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public String getCurrentIdentity() {
    	return null;
     }

	public User findUserByIdentity(String username) {
		// TODO Auto-generated method stub
		return null;
	}
    
  


}
