package com.stackroute.keepnote.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	public User registerUser(User user) throws UserAlreadyExistsException {
		User userById = null;
		
		
		if(userRepository.existsById(user.getUserId())) {
			throw new UserAlreadyExistsException("UserAlreadyExistsException");
		}else {
			user.setUserAddedDate(new Date());
			userById = userRepository.insert(user);
			if(userById == null) {
				throw new UserAlreadyExistsException("UserAlreadyExistsException");
			}
		}
		return userById;
		
		
	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId, User user) throws UserNotFoundException {

		User userById = getUserById(userId);
		if (userById == null) {
			throw new UserNotFoundException("UserNotFoundException");
		} else {
			userById.setUserName(user.getUserName());
			userById.setUserPassword(user.getUserPassword());
			userById.setUserMobile(user.getUserMobile());
			userById.setUserId(user.getUserId());
			userRepository.save(userById);
			return userById;
		}
	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {

		User userById = getUserById(userId);
		if (userById == null) {
			throw new UserNotFoundException("UserNotFoundException");
		} else {
			userRepository.deleteById(userId);
			return true;
		}
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {

		User existsById = userRepository.findById(userId).get();

		if (existsById ==null) {
			throw new UserNotFoundException("UserNotFoundException");
			
		} else {
			return existsById;
		}
	}

}
