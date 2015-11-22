package com.rothanak.gradeskinda.interactor;

import com.rothanak.gradeskinda.data.auth.AuthFacade;
import com.rothanak.gradeskinda.data.entity.Credentials;
import com.rothanak.gradeskinda.interactor.scheduler.TestAddSchedulesTransformer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginInteractorTest {

    @Mock private AuthFacade authenticator;
    private LoginInteractor interactor;

    @Before
    public void setUp() {
        when(authenticator.login(any(Credentials.class))).thenReturn(Observable.just(true));
        interactor = new LoginInteractor(authenticator, TestAddSchedulesTransformer.get());
    }

    @Test
    public void login_PassesThroughToAuthenticator() {
        String username = "Username";
        String password = "Password";
        Credentials credentials = new Credentials(username, password);

        Observable<Boolean> login = interactor.login(credentials);

        assertThat(login.toBlocking().first()).isTrue();
        verify(authenticator).login(credentials);
    }

}