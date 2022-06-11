package ru.itmo.nfomkin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.repository.OwnerRepository;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final OwnerRepository ownerRepository;

  @Autowired
  public UserDetailsServiceImpl(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Owner owner = ownerRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User doesn't exists"));
    return SecurityUser.fromOwner(owner);
  }
}