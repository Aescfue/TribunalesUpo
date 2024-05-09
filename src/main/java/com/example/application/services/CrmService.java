package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CrmService {
    private final RolRepository rolRepository;
    private final PersonaRepository personaRepository;
    private final DocenteRepository docenteRepository;
    private final TfgRepository tfgRepository;
    private final AlumnoRepository alumnoRepository;
    private final TribunalRepository tribunalRepository;
    private final ConvocatoriaRepository convocatoriaRepository;
    private final DefensaRepository defensaRepository;

    public CrmService(RolRepository rolRepository,
                      PersonaRepository personaRepository,
                      DocenteRepository docenteRepository,
                      TfgRepository tfgRepository,
                      AlumnoRepository alumnoRepository,
                      TribunalRepository tribunalRepository,
                      ConvocatoriaRepository convocatoriaRepository,
                      DefensaRepository defensaRepository) {
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.docenteRepository = docenteRepository;
        this.tfgRepository = tfgRepository;
        this.alumnoRepository = alumnoRepository;
        this.tribunalRepository = tribunalRepository;
        this.convocatoriaRepository = convocatoriaRepository;
        this.defensaRepository = defensaRepository;
    }

    public PersonaRepository getPersonaRepository() {
        return personaRepository;
    }

    public RolRepository getRolRepository() {
        return rolRepository;
    }

    public List<Persona> buscarTodasPersonas(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return personaRepository.findAll();
        } else {
            return personaRepository.buscarPorNombre(stringFilter);
        }
    }

    public List<String> getTodosUsuarios() {
        List<Persona> personas =  personaRepository.findAll();
        Iterator<Persona> it = personas.iterator();
        List<String> lista = new ArrayList<String>();
        while(it.hasNext()){
            Persona p = it.next();
            lista.add(p.getUsuario());
        }
        return lista;
    }

    /*public long countContacts() {
        return contactRepository.count();
    }*/

    public void guardaDocente(Docente docente) {
        if (docente == null) {
            System.err.println("Docente nulo");
            return;
        }

        if(this.existeDocente(docente)==null){
            docenteRepository.guardar(docente.getDni(),docente.getFechaIngreso(),docente.getCategoria());
        }else{
            docenteRepository.save(docente);
        }

    }

    private String existeDocente(Docente docente) {
        String dni = null;
        List<Docente> lista = this.buscarDocenteDni(docente.getDni());
        if (lista != null && !lista.isEmpty() ){
            dni = lista.get(0).getPersona().getDni();
        }
        return dni;
    }

    public void eliminaDocente(Docente docente) {docenteRepository.delete(docente);}

    public List<Docente> buscarTodosDocentes(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return docenteRepository.findAll();
        } else {
            return docenteRepository.search(stringFilter);
        }
    }

    public List<Docente> buscarDocenteDni(String filtro) {
            return docenteRepository.buscarDni(filtro);
    }

    public void guardarPersona(Persona persona) {
        if (persona == null) {
            System.err.println("Persona nula");
            return;
        }
        personaRepository.save(persona);
    }

    public void eliminarPersona(Persona persona) {
        personaRepository.delete(persona);
    }

    public void guardarRol(Rol rol) {
        if (rol == null) {
            System.err.println("Rol nulo");
            return;
        }
        rolRepository.guardar(rol.getUsuario(),rol.getCodigo());
    }

    public void eliminarRol(Rol rol) { rolRepository.delete(rol); }

    public List<Rol> buscarTodosRoles(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return rolRepository.findAll();
        } else {
            return rolRepository.search(stringFilter);
        }
    }

    public void guardaTfg(Tfg tfg) {
        if (tfg == null) {
            System.err.println("Tfg nulo");
            return;
        }
        tfgRepository.save(tfg);
    }

    public void eliminaTfg(Tfg tfg) {tfgRepository.delete(tfg);}

    public List<Tfg> buscarTodosTfgs(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return tfgRepository.findAll();
        } else {
            return tfgRepository.search(codigo);
        }
    }

    public void guardaAlumno(Alumno alumno) {
        if (alumno == null) {
            System.err.println("Alumno nulo");
            return;
        }

        if(this.existeAlumno(alumno)==null){
            alumnoRepository.guardar(alumno.getDni(),alumno.getFechaIngreso());
        }else{
            alumnoRepository.save(alumno);
        }
    }

    private String existeAlumno(Alumno alumno) {
        String dni = null;
        List<Alumno> lista = this.buscarAlumnoDni(alumno.getDni());
        if (lista != null && !lista.isEmpty() ){
            dni = lista.get(0).getDni();
        }
        return dni;
    }

    public void eliminaAlumno(Alumno alumno) {alumnoRepository.delete(alumno);}

    public List<Alumno> buscarTodosAlumno(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return alumnoRepository.findAll();
        } else {
            return alumnoRepository.search(stringFilter);
        }
    }

    public List<Alumno> buscarAlumnoDni(String dni) {
        return alumnoRepository.buscarDni(dni);
    }

    public void guardaTribunal(Tribunal tribunal) {
        if (tribunal == null) {
            System.err.println("Tribunal nulo");
            return;
        }
        tribunalRepository.save(tribunal);
    }

    public void eliminaTribunal(Tribunal tribunal) {tribunalRepository.delete(tribunal);}

    public List<Tribunal> buscarTodosTribunales() {
        return tribunalRepository.findAll();
    }

    public void guardarConvocatoria(Convocatoria convocatoria) {
        convocatoriaRepository.save(convocatoria);
    }

    public void eliminarConvocatoria(Convocatoria convocatoria) {
        convocatoriaRepository.delete(convocatoria);
    }

    public List<Convocatoria> buscarTodasConvocatorias(String value) {
        if (value == null || value.isEmpty()) {
            return convocatoriaRepository.findAll();
        } else {
            return convocatoriaRepository.buscar(value);
        }
    }

    public Convocatoria buscarConvocatoria(String curso, String numero) {
        List<Convocatoria> lista = convocatoriaRepository.buscar(curso, numero);
        if (lista != null && !lista.isEmpty())
            return lista.get(0);
        else
            return null;
        }


    public void guardaDefensa(Defensa defensa) {
        Defensa d = buscarDefensa(defensa);
        if (d == null )
            defensaRepository.save(defensa);
        else{
            defensa.setId(d.getId());
            defensaRepository.save(defensa);
        }
    }

    public Defensa buscarDefensa(Defensa defensa) {
        List<Defensa> lista = defensaRepository.buscarDefensa(defensa.getTribunal().getCodigoTFG().getCodigo(),
                defensa.getTribunal().getConvocatoria().getId().getCurso(),
        defensa.getTribunal().getConvocatoria().getId().getNumero());

        return lista.isEmpty() ? null : lista.get(0);
    }

    public void eliminaDefensa(Defensa defensa) {defensaRepository.delete(defensa);}

    public List<Defensa> buscarTodasDefensas(String value) {
        if (value == null || value.isEmpty()) {
            return defensaRepository.findAll();
        } else {
            return defensaRepository.buscar(value);
        }
    }

    public List<Tribunal> buscarTodosTribunalesCodigo(String codigo, Integer curso, Integer numero) {
        return tribunalRepository.buscarCodigo(codigo, curso, numero);
    }

    public List<Tribunal> buscarTribunalesAnno(String anno) {
        return tribunalRepository.buscarAnno(anno);
    }

    public int docentesValidos(Tribunal tribunal){
        Docente d1 = tribunal.getDocente1();
        Docente d2 = tribunal.getDocente2();
        Docente d3 = tribunal.getDocente3();

        Docente tfg1 = tribunal.getCodigoTFG().getDocente1();
        Docente tfg2 = tribunal.getCodigoTFG().getDocente2();
        //Duplicado
        if(d1.equals(d2) || d1.equals(d3) || d2.equals(d3)){
            return -1;
        }
        //TFG tutor
        if(d1.equals(tfg1) || d2.equals(tfg1) || d1.equals(tfg2) || d2.equals(tfg2) || d3.equals(tfg1) || d3.equals(tfg2) ){
            return -2;
        }
        return 1;
    }

    public void generarTribunalesAleatorios(String curso, String numero) {
        Convocatoria c = buscarConvocatoria(curso, numero);

        List<Tribunal> tribunales = new ArrayList<Tribunal>();
        List<Tfg> listado = tfgRepository.buscarTfgsSinTribunal(curso,numero);
        List<Docente> docentes = docenteRepository.findAll();
        for (Tfg tfg : listado) {
            Set<Docente> seleccionados = new HashSet<>();
            while (seleccionados.size() != 3) {
                Random rand = new Random();
                int indiceAleatorio = rand.nextInt(docentes.size());
                Docente docente = docentes.get(indiceAleatorio);
                seleccionados.add(docente);
            }
            Tribunal t = new Tribunal();
            t.setConvocatoria(c);
            t.setCodigoTFG(tfg);
            t.setFecha(LocalDate.now());
            List<Docente> listaSeleccionados = new ArrayList<>(seleccionados);
            t.setDocente1(listaSeleccionados.get(0));
            t.setDocente2(listaSeleccionados.get(1));
            t.setDocente3(listaSeleccionados.get(2));
            t.ordenarDocentes();
            tribunales.add(t);
        }
        tribunalRepository.saveAll(tribunales);
    }
}