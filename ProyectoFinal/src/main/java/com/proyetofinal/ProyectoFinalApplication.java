package com.proyetofinal;

import com.proyectodinal.servicios.CamionServicio;
import com.proyectodinal.servicios.ViajeServicio;
import com.proyectofinal.entidades.Viaje;
import com.proyectofinal.errores.ErroresServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoFinalApplication {

	public static void main(String[] args) throws ErroresServicio {
		SpringApplication.run(ProyectoFinalApplication.class, args);
                
	}

}
