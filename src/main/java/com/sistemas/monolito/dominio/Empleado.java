package com.sistemas.monolito.dominio;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 60)
    @NotBlank(message = "El Apellido no debe estar en blanco")
    String apellido;

    @Column(length = 60)
    @NotBlank(message = "El Nombre no debe estar en blanco")
    String nombre;

    @Column(length = 8)
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 digitos")
    String dni;

    @Column(length = 9)
    @Size(min = 9, max = 9, message = "El celular debe tener 9 digitos")
    String celular;

    @Column(length = 80)
    @Email(message = "Debe ingresar un eMail valido")
    String email;

    @Temporal(TemporalType.DATE)
    @Past(message = "La fecha de nacimiento debe ser anterior a la actual")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date fechaNacimiento;

    @Temporal(TemporalType.DATE)
    @Past(message = "La fecha de nacimiento debe ser anterior a la actual")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date fechaIngreso;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private List<Asignacion> asignaciones;
}