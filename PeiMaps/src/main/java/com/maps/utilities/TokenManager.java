/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maps.utilities;

import java.util.UUID;

/**
 *
 * @author ubuntu
 */
public class TokenManager {
    
    /**
     * Cria um token
     * @return 
     */
    public static String getPatientToken(){
        return UUID.randomUUID().toString();
    }

}
