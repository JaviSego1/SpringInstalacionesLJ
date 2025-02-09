package com.iesvdc.acceso.pistasdeportivas.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiMisReservas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/mis-reservas")
public class ReservaController {

    @Autowired
    ServiMisReservas serviMisReservas;

    @GetMapping
    public List<Reserva> findAll() {        
        return  serviMisReservas.findReservasByUsuario();
    }
    
    @PostMapping
    public ResponseEntity<Reserva> create(@RequestBody Reserva reserva) {
        try {
            reserva = serviMisReservas.saveReserva(reserva);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }        
    }
    
    /*@DeleteMapping
    public ResponseEntity delete(@ResponseBody Reserva reserva) {
        Optional<Reserva> oReserva;
        try {
            oReserva =  serviMisReservas.deleteReserva(reserva.getId());
        } catch
    }*/

    
}
