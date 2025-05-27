package com.edutech.usuarios_service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.edutech.usuarios_service.model.Permiso;

@Repository
public class PermisoRepository {

    private List<Permiso> listaPermisos = new ArrayList<>();
    public List<Permiso> obtenerPermisos() {
        return listaPermisos;
    }
    public Permiso buscarPorId(String id) {
        for (Permiso permiso : listaPermisos) {
            if (permiso.getId().equals(id)) {
                return permiso;
            }
        }
        return null;
    }
    public Permiso guardar(Permiso permiso) {
        listaPermisos.add(permiso);
        return permiso;
    }
    public Permiso actualizar(Permiso permiso) {
        UUID id = null;
        int idPosicion = 0;

        for (int i = 0; i < listaPermisos.size(); i++) {
            if (listaPermisos.get(i).getId().equals(permiso.getId())) {
                id = permiso.getId();
                idPosicion = i;
            }
        }

        Permiso permiso1 = new Permiso();
        permiso1.setId(id);
        permiso1.setNombre(permiso.getNombre());
        permiso1.setDescripcion(permiso.getDescripcion());

        listaPermisos.set(idPosicion, permiso1);
        return permiso1;
    }
    public void eliminar(String id) {
        listaPermisos.removeIf(permiso -> permiso.getId().equals(id));
    }
    

}
