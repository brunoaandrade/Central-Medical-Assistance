package maps;

import entities.ActionType;
import entities.Activity;
import entities.MeasurePrescript;
import entities.MeasureType;
import entities.Patient;
import entities.PatientAction;
import entities.PrescriptedAction;
import entities.PrescriptedMeasureAction;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.chart.CartesianChartModel;
import webServices.GetClass;
import subClasses.ListaPerigo;
import subClasses.Perfil;
import subClasses.Perigo;
import webServices.PostClass;
import subClasses.Prescricoes;

@ManagedBean(name = "main", eager = true)
@SessionScoped
public class MainBean implements Serializable {

    private ChartBean cb;
    private CartesianChartModel linearModelTemp;
    private CartesianChartModel linearModelTA;
    private CartesianChartModel chartTA;
    private CartesianChartModel chartT;
    private Prescricoes medSelected;
    private LoginBean lb = new LoginBean();
    private Prescricao prescricao = new Prescricao(-1, -1);
    private final PostClass post = new PostClass();
    private final GetClass getClass = new GetClass();
    private Perfil perfil;
    private boolean exits = false;
    private final List<SelectItem> pacientes = new ArrayList<>();
    private String selectedState;
    private String nomedoutor;
    private List<Patient> list = null;
    private int idDoctor;
    DataGrid dataGrid;
    private String searchBox = "";
    private int id = -1;
    private String imagem;
    private String dadospaciente = "";
    private String email = "";
    private int ssn = -1;
    private String gender = "";
    private String history = "";
    private String temphistory = "";
    private Integer rating = 0;
    private ListaPerigo listaPerigo;
    private Date born = null;
    private String city = "";
    private List<Activity> actividades;

    /**
     * Default Constructor.
     */
    public MainBean() {
    }

    public String getSelectedState() {

        return selectedState;
    }

    public LoginBean getLb() {
        return lb;
    }

    public void setLb(LoginBean lb) {
        this.lb = lb;
    }

    public String getNomedoutor() {
        return nomedoutor;
    }

    public ListaPerigo getListaPerigo() {
        return listaPerigo;
    }

    public void setListaPerigo(ListaPerigo listaPerigo) {
        this.listaPerigo = listaPerigo;
    }

    public String getSearchBox() {
        return searchBox;
    }

    public Prescricoes getMedSelected() {
        return medSelected;
    }

    public void setSearchBox(String searchBox) {
        this.searchBox = searchBox;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Patient> getList() {
        return list;
    }

    public void setList(List<Patient> list) {
        this.list = list;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public CartesianChartModel getLinearModelTemp() {
        linearModelTemp = cb.getLinearModelTemp(id);
        return linearModelTemp;
    }

    public CartesianChartModel getLinearModelTA() {

        linearModelTA = cb.getLinearModelTA(id);
        return linearModelTA;
    }

    public CartesianChartModel getChartT() {
        return chartT;
    }

    public CartesianChartModel getChartTA() {
        return chartTA;
    }

    public ChartBean getCb() {
        return cb;
    }

    public void setCb(ChartBean cb) {
        this.cb = cb;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public int getSsn() {
        return ssn;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public int getIddoctor() {
        return idDoctor;
    }

    public String getEmail() {

        return email;
    }

    public String getHistory() {
        temphistory = history;
        return history;
    }

    public String getTemphistory() {
        return temphistory;
    }

    public void setTemphistory(String a) {
        temphistory = a;
    }

    public void setHistory(String hist) {
        history = hist;
    }

    public void setNomedoutor(String nomedoutor) {

        this.nomedoutor = nomedoutor;
    }

    public boolean isExits() {
        return exits;
    }

    public void setExits(boolean exits) {
        this.exits = exits;
    }

    public DataGrid getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(DataGrid dataGrid) {
        this.dataGrid = dataGrid;
    }

    public void setSelectedState(String selectedState) {
        this.selectedState = selectedState;
    }

    public List<SelectItem> getStates() {
        update();
        return pacientes;
    }

    public String getDadospaciente() {
        return dadospaciente;
    }

    public void setDadospaciente(String dadospaciente) {
        this.dadospaciente = dadospaciente;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    //novo valor temperatura
    public void tempvalue() {
        if (idDoctor != -1 && id != -1) {
            boolean success;
            //webService post
            java.util.Date date = new java.util.Date();
            Timestamp timeSend = new Timestamp(date.getTime());
            PrescriptedMeasureAction pma = new PrescriptedMeasureAction(
                    new PatientAction(id),
                    new PrescriptedAction(idDoctor, timeSend, ActionType.Measure, 1, 1),
                    new MeasurePrescript(MeasureType.Temperature)
            );
            success = post.postOneValue(pma);
            FacesMessage msg;
            if (success) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pedido de Temperatura");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não conseguio fazer o pedido Temperatura");
            }
            FacesContext.getCurrentInstance().addMessage("graficos", msg);
        }
    }

    //novo valor de tensão
    public void tavalue() {
        if (idDoctor != -1 && id != -1) {
            //webService post
            java.util.Date date = new java.util.Date();
            Timestamp timeSend = new Timestamp(date.getTime());
            PrescriptedMeasureAction pma = new PrescriptedMeasureAction(
                    new PatientAction(id),
                    new PrescriptedAction(idDoctor, timeSend, ActionType.Measure, 1, 1),
                    new MeasurePrescript(MeasureType.Blood)
            );
            boolean success = post.postOneValue(pma);
            FacesMessage msg;
            if (success) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pedido de Pressão Arterial");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Não conseguio fazer o pedido de PA");
            }
            FacesContext.getCurrentInstance().addMessage("graficos", msg);
        }
    }

    //change pacient 
    public void valueChanged(AjaxBehaviorEvent event) throws IOException {
        if (idDoctor != -1) {
            dadospaciente = (String) ((Object) (new SelectItem(selectedState)).getValue());
            if (list != null) {
                Patient p = search();
                if (p != null) {
                    updateValuesPaciente(p);
                }
            }
            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(origRequest.getRequestURI());
        }
    }

    private void updateValuesPaciente(Patient paciente) {
        if (idDoctor != -1) {
            int number = paciente.getSns();
            if ("M".equals(paciente.getGender())) {
                gender = "Masculino";
                while (number > 20) {
                    number -= 21;
                }
                imagem = "img/Pacientes/men/fotoMen (" + number + ").jpg";
            }
            if ("F".equals(paciente.getGender())) {
                gender = "Feminino";
                while (number > 20) {
                    number -= 21;
                }
                imagem = "img/Pacientes/women/fotoWomen (" + number + ").jpg";
            }
            history = paciente.getHistory();
            id = paciente.getIdPatient();
            ssn = paciente.getSns();
            email = paciente.getMail();
            dadospaciente = paciente.getFname() + " " + paciente.getLname();
            prescricao = new Prescricao(idDoctor, id);
            cb = new ChartBean(id);
            rating = paciente.getFavourite();
            born = paciente.getBorndate();
            city = paciente.getCity();
        }
    }

    //change value of historial of patient
    public void editHistorial() {
        if (idDoctor != -1 && id != -1) {
            history = temphistory;
            try {
                boolean success = false;
                //webService post history
                if (list != null) {
                    Patient p = search();
                    if (p != null) {

                        p.setHistory(history);
                        success = post.postHistory(p);
                    }
                }
                FacesMessage msg;
                if (success) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Edição Completa");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Não conseguiu fazer o upload");
                }
                FacesContext.getCurrentInstance().addMessage("historia", msg);
            } catch (Exception ex) {
                Logger.getLogger(MainBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            temphistory = history;
        }
    }

    public void cancelarHistorial() {
        temphistory = history;
    }

    public void login(ActionEvent actionEvent) {
        boolean success = lb.login(actionEvent);
        if (success) {
            nomedoutor = "Dr. " + lb.getUsername();
            idDoctor = lb.getIdUser();
            perfil = new Perfil(idDoctor);
            cb = new ChartBean(-1);
            dataGrid = new DataGrid(idDoctor);
            linearModelTA = cb.getLinearModelTA(id);
            linearModelTemp = cb.getLinearModelTemp(id);
            prescricao.setIdDoctor(idDoctor);
            list = null;
            pacientes.clear();
            List<Patient> mylist = getClass.patientFavorit(idDoctor);
            list = mylist;
            listaPerigo = new ListaPerigo(idDoctor);
            if (list != null) {
                exits = true;
                for (Patient mylist1 : mylist) {
                    String x = mylist1.getFname() + " " + mylist1.getLname();
                    pacientes.add(new SelectItem(x));
                }
            }
            verPerigo();
        }
        selectedState = "";
    }

    public void logout() throws IOException {
        imagem = ".png";
        id = -1;
        ssn = -1;
        email = "";
        dadospaciente = "";
        gender = "";
        history = "";
        born = null;
        city = "";
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();

        final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        request.getSession(false).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLoggedIn", "false");
        HttpSession session = (HttpSession) ec.getSession(false);
        // remove cookies
        HttpServletResponse httpresponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpresponse.addHeader("Pragma", "no-cache");
        httpresponse.addHeader("Cache-Control", "no-cache");
        httpresponse.addHeader("Cache-Control", "no-store");
        httpresponse.addHeader("Cache-Control", "must-revalidate");
        httpresponse.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");

        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }

    private Patient search() {
        Patient p = null;
        //String[] s = dadospaciente.split("\\s+");
        if (list != null) {
            for (Patient mylist1 : list) {
                if (dadospaciente.equals(mylist1.getFname() + " " + mylist1.getLname())) {
                    p = mylist1;
                }
            }
        }
        return p;
    }

    private void update() {
        verPerigo();
        pacientes.clear();
        List<Patient> mylist = getClass.patientFavorit(idDoctor);
        list = mylist;
        if (list != null) {
            exits = true;
            for (Patient mylist1 : mylist) {
                String x = mylist1.getFname() + " " + mylist1.getLname();
                pacientes.add(new SelectItem(x));
            }
        } else {
            exits = false;
        }
    }

    public void go() {
        dataGrid.go();
        updateValuesPaciente(dataGrid.getSelectedCar().getP());
    }

    public void goPerigo(SelectEvent event) {

        Perigo p = (Perigo) event.getObject();
        dadospaciente = p.getPaciente();
        Patient pa = new Patient();
        List<Patient> mylistPatient = getClass.patientList(idDoctor);
        for (Patient mylist1 : mylistPatient) {
            if (dadospaciente.equals(mylist1.getFname() + " " + mylist1.getLname())) {
                pa = mylist1;
            }
        }
        updateValuesPaciente(pa);
        dataGrid.go();
    }

    public void searchPatient() {
        dataGrid.setSearchBox(searchBox);
        searchBox = "";
        dataGrid.search();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(MainBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onrate() {
        List<Patient> list1 = getClass.patientList(idDoctor);
        Patient p = null;
        if (list1 != null) {
            for (Patient mylist1 : list1) {
                if (dadospaciente.equals(mylist1.getFname() + " " + mylist1.getLname())) {
                    p = mylist1;
                }
            }
            if (p != null) {
                if (p.getFavourite() == 0) {
                    p.setFavourite(1);
                    post.postFavorite(p);
                }
            }
        }
        update();
    }

    public void oncancel() {
        List<Patient> list1 = getClass.patientList(idDoctor);
        Patient p = null;
        if (list1 != null) {
            for (Patient mylist1 : list1) {
                if (dadospaciente.equals(mylist1.getFname() + " " + mylist1.getLname())) {
                    p = mylist1;
                }
            }
            if (p != null) {
                if (p.getFavourite() == 1) {
                    p.setFavourite(0);
                    post.postFavorite(p);
                }
            }
        }
        update();
    }

    public void verPerigo() {
        listaPerigo.update();
    }

    public List<Activity> getActividades() {
        return actividades;
    }

    public void openPresc() {
        openDialogMesure(prescricao.getTb().getSelectedPrescricoes().getIdPrescMeasure());
    }

    public void openCalPresc() {
        List<PrescriptedAction> listPresc = getClass.prescAction(id);
        int idPrescMeasure = -1;
        for (PrescriptedAction pa : listPresc) {
            if (pa.getIdAction() == prescricao.getSchedule().idPrescMeasure) {
                idPrescMeasure = pa.getIdPrescriptact();
            }
        }
        openDialogMesure(idPrescMeasure);
    }

    private void openDialogMesure(int idPrescMeasure) {
        List<Prescricoes> prescricoes = getClass.listaPresc(id);
        Prescricoes c = null;
        for (Prescricoes p : prescricoes) {
            if (p.getIdPrescMeasure() == idPrescMeasure) {
                c = p;
            }
        }
        String destino = "";
        cb.setTaExistsofPrescript(false);
        cb.setTpExistsofPrescript(false);
        if (c.getNome().contains("Arterial")) {
            chartTA = cb.getLinearModelTAofPrescripte(id, idPrescMeasure);
            destino = "pressaoArterial";
        } else if (c.getNome().contains("Temperatura")) {
            chartT = cb.getLinearModelTempofPrescripte(id, idPrescMeasure);
            destino = "temperatura";
        } else if (c.getActionType() == ActionType.Drugtake) {
            medSelected = c;
            List<Activity> a = getClass.activityList(id);
            for (Activity t : a) {
                if (t.getIdAction() == medSelected.getIdPrescMeasure()) {
                    actividades.add(t);
                }
            }
            destino = "medicamento";
        }
        if (cb.getTaExistsofPrescript() || cb.getTpExistsofPrescript() || c.getActionType() == ActionType.Drugtake) {
            Map<String, Object> options = new HashMap<>();
            options.put("modal", true);
            //options.put("draggable", false);
            options.put("resizable", false);
            // options.put("contentHeight", 320);
            options.put("height", "550");
            options.put("width", "700");
            RequestContext.getCurrentInstance().openDialog("dialog/" + destino, options, null);
        }
    }
}
