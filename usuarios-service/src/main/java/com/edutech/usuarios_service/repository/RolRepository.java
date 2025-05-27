package com.edutech.usuarios_service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.edutech.usuarios_service.model.Rol;

@Repository
public class RolRepository {

    private List<Rol> listaRoles = new ArrayList<>();
    public List<Rol> obtenerRoles() {
        return listaRoles;
    }
    public Rol buscarPorId(UUID id) {
        for (Rol rol : listaRoles) {
            if (rol.getId().equals(id)) {
                return rol;
            }
        }
        return null;
    }
    public Rol buscarPorNombre(String nombre) {
        for (Rol rol : listaRoles) {
            if (rol.getNombre().equals(nombre)) {
                return rol;
            }
        }
        return null;
    }
    public Rol guardar(Rol rol) {
        listaRoles.add(rol);
        return rol;
    }
    public Rol actualizar(Rol rol) {
        UUID id = null;
        int idPosicion = 0;

        for (int i = 0; i < listaRoles.size(); i++) {
            if (listaRoles.get(i).getId().equals(rol.getId())) {
                id = rol.getId();
                idPosicion = i;
            }
        }

        Rol rol1 = new Rol();
        rol1.setId(id);
        rol1.setNombre(rol.getNombre());
        rol1.setDescripcion(rol.getDescripcion());

        listaRoles.set(idPosicion, rol1);
        return rol1;
    }
    public void eliminar(UUID id) {
        for (int i = 0; i < listaRoles.size(); i++) {
            if (listaRoles.get(i).getId().equals(id)) {
                listaRoles.remove(i);
                break;
            }
        }
    }
}
