package org.upo.tribunalesupo.services;

import org.springframework.stereotype.Service;
import org.upo.tribunalesupo.data.*;

import java.io.File;
import java.io.IOException;
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
    private final GeneradorActa generadorActa;

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
        this.generadorActa = new GeneradorActa() ;
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


        ComparadorListaParticipaciones comparator = new ComparadorListaParticipaciones();
        List<Object[]> docentesOrdenados = this.obtenerDocentesMenosParticipacion();


        for (Tfg tfg : listado) {
            Set<Docente> seleccionados = new HashSet<>();
            int elementos = 0;
            while (seleccionados.size() != 3) {
                Random rand = new Random();
                int indiceAleatorio = rand.nextInt(docentesOrdenados.size()/2);

                String dni = (String) docentesOrdenados.get(indiceAleatorio)[0];
                Docente docente = this.docenteRepository.findByDni(dni).get(0);
                seleccionados.add(docente);

                if (elementos != seleccionados.size()){
                    elementos = seleccionados.size();
                    docentesOrdenados.get(indiceAleatorio)[1]=((long)docentesOrdenados.get(indiceAleatorio)[1] + 1);
                    docentesOrdenados.sort(comparator);
                }
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

    public File generarActa(Defensa defensa) throws IOException {
        return this.generadorActa.generarActa(defensa);
    }
    
    public List<Object[]> obtenerDocentesTribunalesEstadistica(){
        return tribunalRepository.estadisticaDocentesTribunal();
    }
    
    public List<Object[]> obtenerDocentesMenosParticipacion(){
        return tribunalRepository.docentesMenosParticipaciones();
    }

    public List<Persona> buscarPersona(String usuario) {
        return personaRepository.findByUsuario(usuario);
    }

    public List<Tfg> buscarTfgPersona(Persona p) {
        Set<Tfg> tfgs = new HashSet<Tfg>();
        //se mira si es alumno
        List<Alumno> a = alumnoRepository.findByPersona(p);
        if(!a.isEmpty()){
            tfgs.addAll(tfgRepository.findByAlumno(a.get(0)));
        }
        //Se mira si es docente
        List<Docente> d = docenteRepository.findByPersona(p);
        if(!d.isEmpty()){
            tfgs.addAll(tfgRepository.buscarTfgsDocente(d.get(0)));
        }
        return new ArrayList<Tfg>(tfgs);
    }

    public List<Tribunal> buscarTribunalPersona(Persona p) {
        Set<Tribunal> tribunales = new HashSet<Tribunal>();
        //Se mira si es alumno
        List<Alumno> a = this.alumnoRepository.findByPersona(p);
        if(!a.isEmpty()){
            List<Tfg> lista = tfgRepository.findByAlumno(a.get(0));
                for (Tfg tfg : lista) {
                    List<Tribunal> trib = this.tribunalRepository.findByCodigoTFG(tfg);
                    tribunales.addAll(trib);
                }
        }
        //se mira si es docente
        List<Docente> d = docenteRepository.findByPersona(p);
        if(!d.isEmpty()){
            tribunales.addAll(tribunalRepository.findByDocente1(d.get(0)));
            tribunales.addAll(tribunalRepository.findByDocente2(d.get(0)));
            tribunales.addAll(tribunalRepository.findByDocente3(d.get(0)));
        }
        return new ArrayList<Tribunal>(tribunales);
    }
}