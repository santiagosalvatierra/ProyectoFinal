
package com.managetruck.repositorios;

import com.managetruck.entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
      @Query("SELECT c FROM Usuario c WHERE c.id = :id")
    public List <Usuario> buscarUsuarioporId(@Param("id")String id);
    
    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
    public Optional <Usuario> buscarPorMail(@Param("mail")String mail);
}