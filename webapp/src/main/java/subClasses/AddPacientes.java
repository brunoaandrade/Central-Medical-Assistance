/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subClasses;

/**
 *
 * @author Bruno
 */
import entities.HealthCare;
import entities.Patient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import webServices.GetClass;
import webServices.PostClass;

@ManagedBean
public class AddPacientes implements Serializable {

    private List<String> selectedPacientes;
    private List<Patient> pacientes;
    private List<String> pacientesString;
    private final GetClass get = new GetClass();
    private final PostClass post = new PostClass();
    private final int idHealthCare;

    public AddPacientes(int id) {
        idHealthCare = id;
        pacientes = new ArrayList<>();
        pacientesString = new ArrayList<>();
        pacientes = get.listaPacientesSemMedico();
        if (pacientes != null) {

            for (Patient paciente : pacientes) {
                String x = paciente.getFname() + " " + paciente.getLname();
                pacientesString.add(x);
            }
        }
    }

    public List<String> getSelectedPacientes() {
        return selectedPacientes;
    }

    public List<String> getPacientesString() {
        update();
        return pacientesString;
    }

    public void setPacientesString(List<String> pacientesString) {
        this.pacientesString = pacientesString;
    }

    public void setSelectedPacientes(List<String> selectedPacientes) {
        this.selectedPacientes = selectedPacientes;
    }

    public List<Patient> getPacientes() {
        return pacientes;
    }

    public void adicionar() {
        if (pacientes != null) {
            Patient x;
            boolean success = true;
            if (selectedPacientes.size() > 0) {
                for (String selectedPaciente : selectedPacientes) {
                    x = search(selectedPaciente);
                    int id = x.getIdPatient();
                    success = post.addPatientToHealthCare(idHealthCare, id);
                }
            }
            FacesMessage msg;
            if (success) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Adicionar Pacientes");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Ligação", "Não conseguio Adicionar");
            }
            FacesContext.getCurrentInstance().addMessage("adicionarPacientes", msg);
        }
    }

    private Patient search(String x) {
        Patient p = null;
        //String[] s = dadospaciente.split("\\s+");
        if (pacientes != null) {
            for (Patient mylist1 : pacientes) {
                if (x.equals(mylist1.getFname() + " " + mylist1.getLname())) {
                    p = mylist1;
                }
            }
        }
        return p;
    }

    private void update() {
        List<Patient> p = get.listaPacientesSemMedico();
        if (p != null) {
            pacientes = p;
            for (Patient paciente : pacientes) {
                String x = paciente.getFname() + " " + paciente.getLname();
                pacientesString.add(x);
            }
        }
    }
}
