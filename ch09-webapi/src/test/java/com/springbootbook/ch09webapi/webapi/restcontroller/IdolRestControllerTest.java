package com.springbootbook.ch09webapi.webapi.restcontroller;

import com.springbootbook.ch09webapi.persistence.entity.BloodType;
import com.springbootbook.ch09webapi.persistence.entity.Idol;
import com.springbootbook.ch09webapi.service.IdolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class IdolRestControllerTest {

}
