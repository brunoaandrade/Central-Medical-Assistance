/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maps;

import entities.ActionType;
import entities.Activity;
import entities.DrugTakePrescripted;
import entities.MeasurePrescript;
import entities.MeasureType;
import entities.PatientAction;
import entities.PrescribedActivity;
import entities.PrescriptedAction;
import entities.PrescriptedDrugAction;
import entities.PrescriptedMeasureAction;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import webServices.GetClass;
import webServices.PostClass;

/**
 *
 * @author Bruno
 */
@SessionScoped
public class Prescricao {

    private String tipo = "1";
    private String tipoMedicao = "1";
    private String medicamento = "";
    private Date dateFim = new Date();
    private Date dateNow = new Date();
    private Date dateInicio = new Date();
    private int repeticoes = 1;
    private String tipoMedicaoString;
    private int idDoctor;
    private int id;
    private String detalhes;
    private String actividade;
    private String minDate;
    private String minDateFim;
    private boolean antesRefeicoes = false;

    private TableAction ta;
    private TablePresc tb;
    private CalendarioBean schedule;
    private final PostClass post = new PostClass();
    private final GetClass get = new GetClass();

    public Prescricao(int idDoctor, int id) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        minDate = df.format(new Date());
        minDateFim = minDate;
        this.idDoctor = idDoctor;
        this.id = id;
        tb = new TablePresc(id);
        ta = new TableAction(id);
        schedule = new CalendarioBean(idDoctor, id);
        resetValues();
    }

    public String getDetalhes() {
        return detalhes;
    }

    public TablePresc getTb() {
        return tb;
    }

    public void setTb(TablePresc tb) {
        this.tb = tb;
    }

    public TableAction getTa() {
        return ta;
    }

    public void setTa(TableAction ta) {
        this.ta = ta;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public boolean isAntesRefeicoes() {
        return antesRefeicoes;
    }

    public void setAntesRefeicoes(boolean antesRefeicoes) {
        this.antesRefeicoes = antesRefeicoes;
    }

    public String getActividade() {
        return actividade;
    }

    public void setActividade(String actividade) {
        this.actividade = actividade;
    }

    public CalendarioBean getSchedule() {
        return schedule;
    }

    public void setSchedule(CalendarioBean scheduleController) {
        this.schedule = scheduleController;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFim() {
        dateNow = new Date();
        if (dateFim.before(dateNow)) {
            dateFim = dateNow;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh");
        minDate = df.format(dateInicio);
        return dateFim;

    }

    public void setDateFim(Date dateFim) {
        this.dateFim = dateFim;
    }

    public Date getDateInicio() {
        dateNow = new Date();
        if (dateInicio.before(dateNow)) {
            dateInicio = dateNow;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        minDate = df.format(dateInicio);
        return dateInicio;
    }

    public void setDateInicio(Date dateInicio) {
        this.dateInicio = dateInicio;
    }

    public int getRepeticoes() {
        if (antesRefeicoes == true) {
            return 0;
        }
        return repeticoes;
    }

    public String getMinDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        minDate = df.format(new Date());

        return minDate;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoMedicao() {
        return tipoMedicao;
    }

    public void setTipoMedicao(String tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getMinDateFim() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        minDateFim = df.format(new Date());
        return minDateFim;
    }

    public void editPrescM(ActionEvent actionEvent) {
        boolean success = false;
        if (idDoctor != -1) {
            if (antesRefeicoes) {
                repeticoes = 0;
            }
            int period = Days.daysBetween(new DateTime(dateInicio), new DateTime(dateFim)).getDays() + 2;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(dateInicio);
            cal2.setTime(dateFim);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                period = 1;
            }
            DrugTakePrescripted t = new DrugTakePrescripted();
            t.setDrugName(medicamento);
            t.setDescription(detalhes);
            t.setTakeTime(new Timestamp(new Date().getTime()));
            Timestamp timeSend = new Timestamp(dateInicio.getTime());
            PrescriptedDrugAction pma = new PrescriptedDrugAction(
                    new PatientAction(id),
                    new PrescriptedAction(idDoctor, timeSend, ActionType.Drugtake, period, repeticoes),
                    t
            );
            success = post.postDrugAction(pma);
            tb.update();
            schedule.update();

        }
        FacesMessage msg;
        if (success) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Prescrever Medicamento : " + medicamento);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não conseguio Fazer Prescrição");
        }
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        resetValues();
        refresh();

    }

    public void cancelarPrescM(ActionEvent actionEvent) {
        resetValues();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cancelado", "Pedido de nova prescrição Cancelado");
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        refresh();
    }

    public String onFlowProcessM(FlowEvent event) {
        return event.getNewStep();
    }

    public void editPrescActivity(ActionEvent actionEvent) {
        boolean success = false;
        if (idDoctor != -1) {

            if (antesRefeicoes) {
                repeticoes = 0;
            }
            int period = Days.daysBetween(new DateTime(dateInicio), new DateTime(dateFim)).getDays() + 2;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(dateInicio);
            cal2.setTime(dateFim);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                period = 1;
            }
            Timestamp timeSend = new Timestamp(dateInicio.getTime());
            Activity t = new Activity();
            if (!"".equals(detalhes)) {
                t.setDescription(actividade + " - " + detalhes);
            } else {
                t.setDescription(actividade + " - " + detalhes);
            }
            PrescribedActivity pma = new PrescribedActivity(
                    new PatientAction(id),
                    new PrescriptedAction(idDoctor, timeSend, ActionType.Drugtake, period, repeticoes),
                    t
            );
            success = post.postActivity(pma);

            tb.update();
            schedule.update();

        }
        FacesMessage msg;
        if (success) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Prescrever a Actividade : " + actividade);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não conseguio Fazer Prescrição");
        }

        resetValues();
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        refresh();
    }

    public void cancelarPrescActivity(ActionEvent actionEvent) {
        resetValues();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cancelado", "Pedido de nova Actividade Cancelado");
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        refresh();
    }

    public String onFlowProcessActivity(FlowEvent event) {
        return event.getNewStep();
    }

    public void editPrescMedicao(ActionEvent actionEvent) {
        boolean success = false;
        if (idDoctor != -1) {
            if (antesRefeicoes) {
                repeticoes = 0;
            }
            int period = Days.daysBetween(new DateTime(dateInicio), new DateTime(dateFim)).getDays() + 2;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(dateInicio);
            cal2.setTime(dateFim);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                period = 1;
            }
            MeasureType t;
            switch (tipoMedicao) {
                case "1":
                    t = MeasureType.Temperature;
                    break;
                case "3":
                    t = MeasureType.HeartRate;
                    break;
                default:
                    t = MeasureType.Blood;
                    break;
            }
            Timestamp timeSend = new Timestamp(dateInicio.getTime());
            PrescriptedMeasureAction pma = new PrescriptedMeasureAction(
                    new PatientAction(id),
                    new PrescriptedAction(idDoctor, timeSend, ActionType.Measure, period, repeticoes),
                    new MeasurePrescript(t)
            );
            success = post.postOneValue(pma);
            tb.update();
            schedule.update();
        }
        FacesMessage msg;
        if (success) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Prescrever :" + getTipoMedicaoString());
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não conseguio Fazer Prescrição");
        }
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        resetValues();
        refresh();
    }

    public void cancelarPrescMedicao(ActionEvent actionEvent) {

        resetValues();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cancelado", "Pedido de nova prescrição Cancelado");
        FacesContext.getCurrentInstance().addMessage("prescricoes", msg);
        refresh();
    }

    public String onFlowProcessMedicao(FlowEvent event) {
        return event.getNewStep();
    }

    public String getTipoMedicaoString() {
        switch (tipoMedicao) {
            case "1":
                tipoMedicaoString = "Temperatura";
                break;
            case "2":
                tipoMedicaoString = "Pressão Arterial";
                break;
            default:
                tipoMedicaoString = "Vital Jacket";
                break;
        }
        return tipoMedicaoString;
    }

    public void valueChanged() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = false;
        boolean atividade = false;

        if (null != tipo) {
            switch (tipo) {
                case "1":
                    //medicao
                    medicao = true;
                    context.addCallbackParam("medicao", medicao);
                    context.addCallbackParam("actividade", atividade);
                    break;
                case "2":
                    //medicamento
                    context.addCallbackParam("medicao", medicao);
                    context.addCallbackParam("actividade", atividade);
                    break;
                case "3":
                    //actividade
                    atividade = true;
                    context.addCallbackParam("medicao", medicao);
                    context.addCallbackParam("actividade", atividade);
                    break;
            }
        }
    }

    private void resetValues() {
        actividade = "";
        detalhes = "";
        antesRefeicoes = false;
        tipo = "1";
        tipoMedicao = "1";
        medicamento = "";
        dateFim = new Date();
        dateInicio = new Date();
        repeticoes = 1;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        minDate = df.format(dateInicio);
        minDateFim = minDate;
    }

    private void refresh() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshCalendario() {
        schedule.update();
        tb.renderC();
    }

    /**
     * *********************************
     */
    public void valueChangedMedicaoT() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = true;
        boolean atividade = false;
        tipo = "1";
        tipoMedicao = "1";
        tipoMedicaoString = "Temperatura";
        context.addCallbackParam("medicao", medicao);
        context.addCallbackParam("actividade", atividade);
    }

    public void valueChangedMedicaoP() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = true;
        boolean atividade = false;
        tipo = "1";
        tipoMedicao = "2";
        tipoMedicaoString = "Pressão Arterial";
        context.addCallbackParam("medicao", medicao);
        context.addCallbackParam("actividade", atividade);
    }

    public void valueChangedMedicaoF() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = true;
        boolean atividade = false;
        tipo = "1";
        tipoMedicao = "3";
        tipoMedicaoString
                = tipoMedicaoString = "Vital Jacket";
        context.addCallbackParam("medicao", medicao);
        context.addCallbackParam("actividade", atividade);
    }

    public void valueChangedActividade() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = false;
        boolean atividade = false;
        tipo = "2";

        //medicamento
        context.addCallbackParam("medicao", medicao);
        context.addCallbackParam("actividade", atividade);

    }

    public void valueChangedMedicamento() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean medicao = false;
        boolean atividade;
        tipo = "3";

        //actividade
        atividade = true;
        context.addCallbackParam("medicao", medicao);
        context.addCallbackParam("actividade", atividade);

    }

    public String onFlowProcessM1(FlowEvent event) {
        return event.getNewStep();
    }

    public String onFlowProcessMed(FlowEvent event) {
        return event.getNewStep();
    }

    public String onFlowProcessA(FlowEvent event) {
        return event.getNewStep();
    }

}
