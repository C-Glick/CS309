package com.example.carbon_counter_front_end;

import android.content.Context;
import android.widget.EditText;

import com.android.volley.toolbox.HttpResponse;
import com.example.carbon_counter_front_end.data.logic.LoginLogic;
import com.example.carbon_counter_front_end.data.logic.UpdateStatsLogic;
import com.example.carbon_counter_front_end.data.model.RequestServerForService;
import com.example.carbon_counter_front_end.data.model.UserInformation;
import com.example.carbon_counter_front_end.data.view.LoginActivity;
import com.example.carbon_counter_front_end.data.view.UpdateActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAndViewTest {
    @Mock
    private Context context;

    @Mock
    private UpdateActivity UpdateActivity;

    @Mock
    private RequestServerForService model;

    JSONObject mockJSON = mock(JSONObject.class);

    @Mock private EditText miles;
    @Mock private EditText water;
    @Mock private EditText power;
    @Mock private EditText waste;
    @Mock private EditText meat;


    @Test
    public void UpdateUser() throws JSONException {

        UpdateStatsLogic mock = mock(UpdateStatsLogic.class);
        mock.setModel(model);
        mock.authenticate(mockJSON);
        verify(mock).authenticate(mockJSON);


    }
}