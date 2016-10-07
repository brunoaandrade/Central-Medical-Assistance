/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.HealthCare;
import entities.HealthCareToken;
import entities.Patient;
import entities.PrescribedActivity;
import entities.PrescriptedDrugAction;
import entities.PrescriptedMeasureAction;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Bruno
 */
public class PostClass {

    private UrlWebServices urlWebService = new UrlWebServices();

    public boolean postHealthCareRegister(HealthCare healthCare) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;

        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(healthCare);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostHealthCareRegister());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                /* obtem a resposta do pedido */
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public void setUrlWebService(UrlWebServices urlWebService) {
        this.urlWebService = urlWebService;
    }

    public UrlWebServices getUrlWebService() {
        return urlWebService;
    }

    public boolean postHistory(Patient patient) {

        StringBuilder output = new StringBuilder();
        String bodyParameters;

        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(patient);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostHistory());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public HealthCareToken postLogin(String email, String password) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;
        HealthCare p = new HealthCare();
        p.setMail(email);
        p.setPasswordHash(password);
        Gson gson = new GsonBuilder().create();
        try {

            bodyParameters = gson.toJson(p);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostLogin());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return null;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return null;
        }
        return gson.fromJson(output.toString(), HealthCareToken.class);
    }

    public boolean postOneValue(PrescriptedMeasureAction pma) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;

        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(pma);
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostOneValue());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean modifyEmail(HealthCare hc) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;
        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(hc);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostEmail());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean addPatientToHealthCare(int idHealthCare, int Patient) {
        Patient p = new Patient();
        HealthCare hc = new HealthCare();
        hc.setIdHeathCare(idHealthCare);
        p.setIdPatient(idHealthCare);
        p.setIdHealthCare(hc);

        StringBuilder output = new StringBuilder();
        String bodyParameters;
        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(p);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getAddPatientToHealthCare());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;

    }

    public boolean postFavorite(Patient p) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;
        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(p);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostFavorite() + p.getIdPatient());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.writeBytes(bodyParameters);
                wr.flush();
            }

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean postDrugAction(PrescriptedDrugAction pma) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;

        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(pma);
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostDrugAction());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean postActivity(PrescribedActivity pma) {
        StringBuilder output = new StringBuilder();
        String bodyParameters;

        try {
            Gson gson = new GsonBuilder().create();
            bodyParameters = gson.toJson(pma);
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlWebService.getPostActivity());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParameters.getBytes().length));

            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(bodyParameters);
            wr.flush();
            wr.close();

            InputStream is;
            if (urlConnection.getResponseCode() == 200) {
                System.out.println("PASSOU");
                is = urlConnection.getInputStream();

                // obtem a resposta do pedido 
                int n = 1;
                while (n > 0) {

                    byte[] b = new byte[4096];

                    n = is.read(b);

                    if (n > 0) {
                        output.append(new String(b, 0, n));
                    }
                }
                System.out.println(output.toString());
            } else {
                System.out.println(urlConnection.getResponseCode());
                return false;
            }
            urlConnection.disconnect();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean deletePrescription(int idAction) {
        return false;
    }

    public boolean changePass(int idHealthCare, String password1) {
        return false;
    }

}
