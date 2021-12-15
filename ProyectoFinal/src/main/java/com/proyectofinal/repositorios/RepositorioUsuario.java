
package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Transportista;
import com.proyectofinal.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
      @Query("SELECT c FROM Usuario c WHERE c.id = :id")
    public List <Usuario> buscarUsuarioporId(@Param("id")String id);
    
}
