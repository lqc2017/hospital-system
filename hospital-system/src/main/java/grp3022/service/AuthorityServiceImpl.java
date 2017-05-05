/**
 * 全琛
 * 2017年5月4日
 */
package grp3022.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import grp3022.bean.HospitalAccount;
import grp3022.dao.HospitalAccountDao;

/**
 * @author 全琛
 *
 */
public class AuthorityServiceImpl implements UserDetailsService {
	@Autowired
	private HospitalAccountDao hospitalAccountDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HospitalAccount account = hospitalAccountDao.selectByUserName(username);

		String userName = account.getUsername();
		String passWord = account.getPassword();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		if (account != null) {
			short role = account.getRole();

			switch (role) {
			case 10:
				authorities.add(new SimpleGrantedAuthority("ROLE_CASHIER"));
				break;
			case 20:
				authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
				break;
			case 30:
				authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
				break;
			case 40:
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				break;
			}
		}
		return new User(userName, passWord, authorities);
	}

}
