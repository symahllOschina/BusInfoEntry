package com.wdkj.dkhdl;

import org.junit.Before;

import static org.junit.Assert.*;

public class LoginActivityTest {

    LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = new LoginActivity();
        String account = "";
        String passwd = "";
        loginActivity.doLogin(account,passwd);
    }
}