package com.managetruck;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component /*Esta anotación permite que sea considerada como un bean y así poder inyectarla y hacer uso de sus métodos en cualquier otra clase.*/
public class LoginSuccessMessage extends SimpleUrlAuthenticationSuccessHandler{
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
            Authentication authentication) throws IOException, ServletException {
        
            SessionFlashMapManager fManager = new SessionFlashMapManager();
            FlashMap fMap = new FlashMap();
            
            fMap.put("success", "Bienvenid@. Has iniciado sesión con éxito.");
            fManager.saveOutputFlashMap(fMap, request, response);
            
            
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

