package org.upo.tribunalesupo.views.list;

import org.upo.tribunalesupo.data.Persona;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonaForm extends FormLayout {
    TextField nombre = new TextField("Nombre");
    TextField apellidos = new TextField("Apellidos");
    TextField dni = new TextField("DNI");
    DatePicker fecha_nacimiento = new DatePicker("Fecha de nacimiento");
    EmailField correo_electronico = new EmailField("Correo electrónico");
    IntegerField telefono = new IntegerField("Número de teléfono");
    TextField usuario = new TextField("Usuario");
    TextField contrasena = new TextField("Contraseña");



    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button close = new Button("Cancelar");
    BeanValidationBinder<Persona> binder = new BeanValidationBinder<>(Persona.class);

    public PersonaForm() {
        addClassName("Persona-form");
        binder.bindInstanceFields(this);

        add(nombre,
                apellidos,
                dni,
                fecha_nacimiento,
                correo_electronico,
                telefono,
                usuario,
                contrasena,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new PersonaForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new PersonaForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            Persona p = binder.getBean();
            p.setNombre(primeraLetraMayuscula(p.getNombre().toLowerCase()));
            p.setApellidos(primeraLetraMayuscula(p.getApellidos().toLowerCase()));

            if(p.getUsuario().isEmpty()) {
                Pattern pattern = Pattern.compile("\\s");
                Matcher matcher = pattern.matcher(p.getApellidos());
                boolean found = matcher.find();
                p.setUsuario(p.getNombre().charAt(0) + p.getApellidos().substring(0, 3) + p.getApellidos().substring(p.getApellidos().indexOf(' ')+1, p.getApellidos().indexOf(' ') + 4));
                p.setUsuario(p.getUsuario().toLowerCase());
                p.setUsuario(StringUtils.stripAccents(p.getUsuario()));
            }

            p.setUsuario(StringUtils.stripAccents(p.getUsuario()));
            String contrasena = p.getContrasena();
            PasswordEncoder codificador = new BCryptPasswordEncoder();
            if(!p.getContrasena().isEmpty()){
                p.setContrasena(codificador.encode(contrasena));
            }
            fireEvent(new PersonaForm.SaveEvent(this, p));
        }
    }

    private static String primeraLetraMayuscula(String texto) {
        return Arrays.stream(texto.split("\\s"))
                .map(palabra -> Character.toTitleCase(palabra.charAt(0)) + palabra.substring(1))
                .collect(Collectors.joining(" "));
    }

    public void setPersona(Persona Persona) {
        binder.setBean(Persona);
    }

    // Eventos
    public static abstract class PersonaFormEvent extends ComponentEvent<PersonaForm> {
        private Persona Persona;

        protected PersonaFormEvent(PersonaForm source, Persona Persona) {
            super(source, false);
            this.Persona = Persona;
        }

        public Persona getPersona() {
            return Persona;
        }
    }

    public static class SaveEvent extends PersonaForm.PersonaFormEvent {
        SaveEvent(PersonaForm source, Persona Persona) {
            super(source, Persona);
        }
    }

    public static class DeleteEvent extends PersonaForm.PersonaFormEvent {
        DeleteEvent(PersonaForm source, Persona Persona) {
            super(source, Persona);
        }

    }

    public static class CloseEvent extends PersonaForm.PersonaFormEvent {
        CloseEvent(PersonaForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<PersonaForm.DeleteEvent> listener) {
        return addListener(PersonaForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<PersonaForm.SaveEvent> listener) {
        return addListener(PersonaForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<PersonaForm.CloseEvent> listener) {
        return addListener(PersonaForm.CloseEvent.class, listener);
    }
}
