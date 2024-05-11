package org.upo.tribunalesupo.security;

import org.upo.tribunalesupo.data.Persona;
import org.upo.tribunalesupo.services.PersonaRepository;
import org.upo.tribunalesupo.data.Rol;
import org.upo.tribunalesupo.services.RolRepository;
import org.upo.tribunalesupo.services.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TribunalesUserDetailsService implements UserDetailsService {

    @Autowired
    private CrmService crm;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonaRepository personaRepository = crm.getPersonaRepository();
        RolRepository rolRepository = crm.getRolRepository();

        Set<GrantedAuthority> authorityList = new HashSet<>();
        List<Persona> p = personaRepository.search(username);
        Iterator<Persona> it = p.iterator();
        if(it.hasNext()){
            Persona persona = it.next();
            List<Rol> roles = rolRepository.search(persona.getUsuario());
            for (Rol rol : roles) {
                authorityList.add(new SimpleGrantedAuthority(rol.getCodigo()));
            }
            return new org.springframework.security.core.userdetails.User(persona.getUsuario(), persona.getContrasena(), authorityList);

        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
