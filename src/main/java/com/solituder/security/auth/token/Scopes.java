package com.solituder.security.auth.token;

/**
 * Scopes
 * 
 * @author vladimir.stankovic
 *
 * Aug 18, 2016
 */
public enum Scopes
{
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
